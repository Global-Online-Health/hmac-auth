package com.hmacspringboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashMap;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
class IntegrationTest {

    private static final WireMockServer wireMockServer = new WireMockServer();
    private static final String ACCESS_KEY_ID = "test-access-key";
    private static final long REQUEST_TIMESTAMP = Instant
            .now(Clock.fixed(Instant.parse("2022-01-01T14:00:00Z"), ZoneOffset.UTC))
            .getEpochSecond();
    private static final String AUTHORIZATION_HEADER_TEMPLATE =
            "HMAC-SHA256, SignedHeaders=X-Mp-Timestamp;X-Mp-Access-Key, Signature=%s";
    private static final String QUERY_STRING = "paramC=valueC&paramB=valueB&paramA=valueA";

    @BeforeAll
    static void beforeAll() {
        wireMockServer.start();
    }

    @BeforeEach
    void setUp() {
        wireMockServer.resetAll();
        configureFor("localhost", 8080);
        stubFor(get(urlEqualTo("/foo")).willReturn(aResponse()));
        stubFor(get(urlEqualTo("/foo?" + QUERY_STRING)).willReturn(aResponse()));
        stubFor(post(urlEqualTo("/foo")).willReturn(aResponse()));
    }

    @Test
    void shouldAddAuthorizationHeaderToGetRequest() {
        var expectedSignature = "947bd0fa6994f6ddf7ccbddeff6968a8f011187b88864f1f8ba4f3fb48a2d9f0";
        var expectedAuthorizationHeader = String.format(AUTHORIZATION_HEADER_TEMPLATE, expectedSignature);

        var httpClient = new HttpClient(ACCESS_KEY_ID, REQUEST_TIMESTAMP);
        var response = httpClient.get();

        Assertions.assertEquals(response.getStatusCode(), OK);
        verify(getRequestedFor(urlEqualTo("/foo"))
                .withHeader("Authorization", equalTo(expectedAuthorizationHeader))
                .withHeader("X-Mp-Timestamp", equalTo(String.valueOf(REQUEST_TIMESTAMP)))
                .withHeader("X-Mp-Access-Key", equalTo(ACCESS_KEY_ID)));
    }

    @Test
    void shouldAddAuthorizationHeaderToPostRequest() throws JsonProcessingException {
        byte[] requestBody = new ObjectMapper().writeValueAsBytes(new HashMap<String, String>() {{
            put("fieldA", "valueA");
            put("fieldB", "valueB");
        }});
        var expectedSignature = "d67b4f69c9373d224f9415dce5cdbeba484435b40b523d2b2d6f192b0bef333a";
        var expectedAuthorizationHeader = String.format(AUTHORIZATION_HEADER_TEMPLATE, expectedSignature);

        var httpClient = new HttpClient(ACCESS_KEY_ID, REQUEST_TIMESTAMP);
        var response = httpClient.post(requestBody);

        Assertions.assertEquals(response.getStatusCode(), OK);
        verify(postRequestedFor(urlEqualTo("/foo"))
                .withHeader("Authorization", equalTo(expectedAuthorizationHeader))
                .withHeader("X-Mp-Timestamp", equalTo(String.valueOf(REQUEST_TIMESTAMP)))
                .withHeader("X-Mp-Access-Key", equalTo(ACCESS_KEY_ID)));
    }

    @Test
    void shouldAddAuthorizationHeaderToGetRequestWithQueryString() {
        var expectedSignature = "10ed21705b857eb52ab73296367e06db3f547308f8760ea498641cc25c425b3e";
        var expectedAuthorizationHeader = String.format(AUTHORIZATION_HEADER_TEMPLATE, expectedSignature);

        var httpClient = new HttpClient(ACCESS_KEY_ID, REQUEST_TIMESTAMP);
        var response = httpClient.get(QUERY_STRING);

        Assertions.assertEquals(response.getStatusCode(), OK);
        verify(getRequestedFor(urlEqualTo("/foo?" + QUERY_STRING))
                .withHeader("Authorization", equalTo(expectedAuthorizationHeader))
                .withHeader("X-Mp-Timestamp", equalTo(String.valueOf(REQUEST_TIMESTAMP)))
                .withHeader("X-Mp-Access-Key", equalTo(ACCESS_KEY_ID)));
    }

}
