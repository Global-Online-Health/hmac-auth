package ai.mypulse.hmacauth.core;

public class AuthSignerRequest implements Signer {

    private String secretAccessKey;
    private HttpRequest httpRequest2;

    @Override
    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    @Override
    public HttpRequest getHttpRequest2() {
        return httpRequest2;
    }

    public void setHttpRequest2(HttpRequest httpRequest2) {
        this.httpRequest2 = httpRequest2;
    }
}
