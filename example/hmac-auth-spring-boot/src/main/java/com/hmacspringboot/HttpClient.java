package com.hmacspringboot;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class HttpClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders headers = new HttpHeaders();

    public HttpClient(String accessKeyId, long requestTimestamp) {
        this.restTemplate.getInterceptors().add(new InjectAuthorizationHeaderWithHmacSignature());
        this.headers.add("X-Mp-Timestamp", String.valueOf(requestTimestamp));
        this.headers.add("X-Mp-Access-Key", accessKeyId);
    }

    public ResponseEntity<String> get() {
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        return restTemplate.exchange("http://localhost:8080/foo", HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> get(String queryString) {
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        return restTemplate.exchange("http://localhost:8080/foo?" + queryString, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> post(byte[] requestBody) {
        HttpEntity<byte[]> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange("http://localhost:8080/foo", HttpMethod.POST, entity, String.class);
    }
}
