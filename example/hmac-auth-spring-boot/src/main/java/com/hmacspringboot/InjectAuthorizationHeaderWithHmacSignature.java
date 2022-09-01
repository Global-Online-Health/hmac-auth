package com.hmacspringboot;

import ai.mypulse.hmacauth.core.AuthSignerRequest;
import ai.mypulse.hmacauth.core.HmacAuthenticationSigner;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Objects;

public class InjectAuthorizationHeaderWithHmacSignature implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        var requestWithAuthorization = new ai.mypulse.hmacauth.core.HttpRequest();
        var requestTimestamp = Long.parseLong(Objects.requireNonNull(request.getHeaders().get("X-Mp-Timestamp")).get(0));
        var accessKeyId = Objects.requireNonNull(request.getHeaders().get("X-Mp-Access-Key")).get(0);
        requestWithAuthorization.setTimestamp(requestTimestamp);
        requestWithAuthorization.setAccessKeyId(accessKeyId);
        requestWithAuthorization.setQueryString(request.getURI().getQuery());
        requestWithAuthorization.setMethod(request.getMethodValue());
        requestWithAuthorization.setPath(request.getURI().getPath());
        requestWithAuthorization.setBody(body);

        var authSignerRequest = new AuthSignerRequest();
        authSignerRequest.setHttpRequest(requestWithAuthorization);
        authSignerRequest.setSecretAccessKey("test-secret-access-key"); // Store secret key in your secrets manager
        var hmacAuthenticationSigner = new HmacAuthenticationSigner();
        var signature = hmacAuthenticationSigner.calculateSignature(authSignerRequest);

        String authorizationHeaderValue = "HMAC-SHA256, SignedHeaders=X-Mp-Timestamp;X-Mp-Access-Key, Signature=";
        request.getHeaders().add("Authorization", authorizationHeaderValue + signature);
        return execution.execute(request, body);
    }
}
