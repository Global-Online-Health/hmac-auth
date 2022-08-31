package ai.mypulse.hmacauth.core;

import javax.servlet.http.HttpServletRequest;

public class AuthSignerRequest implements Signer {

    private String secretAccessKey;
    private HttpServletRequest httpRequest;
    private HttpRequest httpRequest2;

    @Override
    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    @Override
    public HttpServletRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public HttpRequest getHttpRequest2() {
        return httpRequest2;
    }

    public void setHttpRequest2(HttpRequest httpRequest2) {
        this.httpRequest2 = httpRequest2;
    }
}
