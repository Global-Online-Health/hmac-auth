package ai.mypulse.hmacauth.core;

public class AuthSignerRequest implements Signer {

    private String secretAccessKey;
    private HttpRequest httpRequest;

    @Override
    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    @Override
    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }
}
