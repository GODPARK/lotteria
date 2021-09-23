package com.lotteria.batch.component;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

}
