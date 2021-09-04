package com.lotteria.api.component.lotto;

import com.lotteria.api.dto.lotto.LottoHistoryRequestDto;
import com.lotteria.api.dto.lotto.LottoResultResponseDto;
import com.lotteria.api.entity.lotto.LottoStatistics;
import com.lotteria.api.exception.ServerErrorException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Component
public class LottoCrawlingComponent {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value(value = "${lotto.crawling.default.uri}")
    private String dhLotteryUri;

    @Value(value = "${lotto.crawling.read.timeout}")
    private int lottoReadTimeout;

    @Value(value = "${lotto.crawling.request.timeout}")
    private int connectionRequestTimeout;

    @Value(value = "${lotto.crawling.httpclient.max.connection}")
    private int httpClientMaxConnection;

    @Value(value = "${lotto.crawling.httpclient.max.connection.per.route}")
    private int httpClientMaxConnectionPerRoute;

    private RestTemplate restTemplate;

    private HttpEntity httpEntity;

    public LottoCrawlingComponent() {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpComponentsClientHttpRequestFactory.setReadTimeout(this.lottoReadTimeout);
        httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(this.connectionRequestTimeout);

        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(this.httpClientMaxConnection)
                .setMaxConnPerRoute(this.httpClientMaxConnectionPerRoute)
                .build();
        httpComponentsClientHttpRequestFactory.setHttpClient(httpClient);

        this.restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
        this.httpEntity = new HttpEntity(new HttpHeaders());
    }

    public LottoHistoryRequestDto getNumberBydHLottoInHtml(long roundNum) {
        String requestUri = UriComponentsBuilder
                .fromHttpUrl(this.dhLotteryUri)
                .queryParam("method", "byWin")
                .queryParam("drwNo", roundNum).toUriString();

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                requestUri,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            logger.info("Dh lottery site is error : " + responseEntity.getStatusCode().toString());
            throw new ServerErrorException("lotto site not available");
        }

        if (responseEntity.getBody() == null) throw new ServerErrorException("lotto body is null");
        String cleanResponseString = responseEntity.getBody()
                .replaceAll("[\\r|\\t]", "")
                .replace(" ", "").replace("  ", "");
        List<String> responseList = Arrays.asList(cleanResponseString.split("\n"));
        List<Integer> lottoHistoryList = new ArrayList<>();

        LottoHistoryRequestDto lottoHistoryRequestDto = new LottoHistoryRequestDto();

        for (String checkString : responseList) {
            if (checkString.contains("ball_645lrg")) {
                if (checkString.contains("<p>")) {
                    lottoHistoryRequestDto.setBonus(
                            Integer.valueOf(checkString.replaceAll("<[^>]*>", ""))
                    );
                }
                else {
                    lottoHistoryList.add(
                            Integer.valueOf(checkString.replaceAll("<[^>]*>", ""))
                    );
                }
            }
            if (checkString.contains("<h4><strong>") && checkString.contains("당첨결과")) {
                long roundNumInHtml = Long.parseLong(
                        checkString.replaceAll("(<[^>]*>)|([ㄱ-ㅎㅏ-ㅣ가-힣])|(\\s)", "")
                );
                if (roundNumInHtml != roundNum) throw new ServerErrorException("roundNum and html is not match");
                lottoHistoryRequestDto.setRoundNum(roundNumInHtml);
            }
        }
        if (lottoHistoryList.size() != 6) throw new ServerErrorException("lotto number list size 0");
        lottoHistoryRequestDto.setNumberList(lottoHistoryList);
        return lottoHistoryRequestDto;
    }

    public LottoHistoryRequestDto getNumberBydHLottoInHtmLatest() {
        String requestUri = UriComponentsBuilder
                .fromHttpUrl(this.dhLotteryUri)
                .queryParam("method", "byWin").toUriString();

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                requestUri,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            logger.info("Dh lottery site is error : " + responseEntity.getStatusCode());
            throw new ServerErrorException("lotto site not available");
        }

        if (responseEntity.getBody() == null) throw new ServerErrorException("lotto body is null");
        String cleanResponseString = responseEntity.getBody()
                .replaceAll("[\\r|\\t]", "")
                .replace(" ", "").replace("  ", "");
        List<String> responseList = Arrays.asList(cleanResponseString.split("\n"));
        List<Integer> lottoHistoryList = new ArrayList<>();

        LottoHistoryRequestDto lottoHistoryRequestDto = new LottoHistoryRequestDto();

        for (String checkString : responseList) {
            if (checkString.contains("ball_645lrg")) {
                if (checkString.contains("<p>")) {
                    lottoHistoryRequestDto.setBonus(
                            Integer.parseInt(checkString.replaceAll("<[^>]*>", ""))
                    );
                }
                else {
                    lottoHistoryList.add(
                            Integer.parseInt(checkString.replaceAll("<[^>]*>", ""))
                    );
                }
            }
            if (checkString.contains("<h4><strong>") && checkString.contains("당첨결과")) {
                lottoHistoryRequestDto.setRoundNum(Long.parseLong(
                        checkString.replaceAll("(<[^>]*>)|([ㄱ-ㅎㅏ-ㅣ가-힣])|(\\s)", "")
                ));
            }
        }
        if (lottoHistoryList.size() != 6) throw new ServerErrorException("lotto number list size 0");
        lottoHistoryRequestDto.setNumberList(lottoHistoryList);
        return lottoHistoryRequestDto;
    }

    public List<LottoStatistics> getStatistics() {
        String requestUri = UriComponentsBuilder
                .fromHttpUrl(this.dhLotteryUri)
                .queryParam("method", "statByNumber").toUriString();

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                requestUri,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            logger.info("Dh lottery site is error : " + responseEntity.getStatusCode());
            throw new ServerErrorException("lotto site not available");
        }

        if (responseEntity.getBody() == null) throw new ServerErrorException("lotto body is null");

        String cleanResponseString = responseEntity.getBody()
                .replaceAll("[\\r|\\t]", "")
                .replace(" ", "").replace("  ", "");
        List<String> responseList = Arrays.asList(cleanResponseString.split("\n"));
        List<LottoStatistics> lottoStatisticsList = new ArrayList<>();

        for (String checkString : responseList) {
            if (checkString.contains("drwtNoPop[")) {
                String numberString = checkString.split("=")[0].replaceAll("[^0-9]", "");
                String countString = checkString.split("=")[1].replaceAll("[^0-9]", "");
                lottoStatisticsList.add(LottoStatistics.builder()
                        .num(Integer.parseInt(numberString) + 1)
                        .count(Integer.parseInt(countString))
                        .build()
                );
            }
        }

        return lottoStatisticsList;
    }
}
