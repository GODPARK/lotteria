package com.lotteria.batch.component;

import com.lotteria.batch.dto.lotteria.LotteriaLottoResponseDto;
import com.lotteria.batch.exception.LotteriaApiException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LotteriaApiComponent {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value(value = "${lotteria.api.uri}")
    private String lotteriaUri;
    @Value(value = "${lotteria.api.read.timeout}")
    private int readTimeout;
    @Value(value = "${lotteria.api.request.timeout}")
    private int requestTimeout;
    @Value(value = "${lotteria.api.max.connection}")
    private int maxConnection;
    @Value(value = "${lotteria.api.max.connection.per.route}")
    private int maxConnectionPerRoute;

    private RestTemplate restTemplate;
    private HttpEntity httpEntity;

    private <T> T responseEntityCheck(ResponseEntity<T> responseEntity, String uri) {
        if (responseEntity.getStatusCode() != HttpStatus.OK) throw new LotteriaApiException(
               uri + " response state error (" + responseEntity.getStatusCode().toString() + ")"
        );
        if (responseEntity.getBody() == null) throw new LotteriaApiException(
                uri + " response body null"
        );
        return responseEntity.getBody();
    }

    public LotteriaApiComponent() {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpComponentsClientHttpRequestFactory.setReadTimeout(this.readTimeout);
        httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(this.requestTimeout);

        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(this.maxConnection)
                .setMaxConnPerRoute(this.maxConnectionPerRoute)
                .build();

        httpComponentsClientHttpRequestFactory.setHttpClient(httpClient);
        this.restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
        this.httpEntity = new HttpEntity(new HttpHeaders());
    }

    public LotteriaLottoResponseDto lotteriaLottoDataSyncLatestApi() {
        String requestUri = this.lotteriaUri + "/lotto_data/sync/latest";
        ResponseEntity<LotteriaLottoResponseDto> responseEntity = this.restTemplate.exchange(
                requestUri,
                HttpMethod.POST,
                this.httpEntity,
                LotteriaLottoResponseDto.class
        );
        return this.responseEntityCheck(responseEntity, requestUri);
    }

    public LotteriaLottoResponseDto lotteriaLottoLatestNumberApi() {
        String requestUri = this.lotteriaUri + "/lotto_history/latest";
        ResponseEntity<LotteriaLottoResponseDto> responseEntity = this.restTemplate.exchange(
                requestUri,
                HttpMethod.GET,
                this.httpEntity,
                LotteriaLottoResponseDto.class
        );
        return this.responseEntityCheck(responseEntity, requestUri);
    }

}
