package ai.mypulse.hmacauth.core;

public interface Signer {
    String getSecretAccessKey();

    HttpRequest getHttpRequest2();
}
