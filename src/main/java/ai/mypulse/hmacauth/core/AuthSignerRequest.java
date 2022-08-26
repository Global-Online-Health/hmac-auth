package ai.mypulse.hmacauth.core;

import javax.servlet.http.HttpServletRequest;

public class AuthSignerRequest implements Signer {

    private String secretAccessKey;
    private HttpServletRequest httpRequest;

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
}
