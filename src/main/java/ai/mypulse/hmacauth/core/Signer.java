package ai.mypulse.hmacauth.core;

import javax.servlet.http.HttpServletRequest;

public interface Signer {
    String getSecretAccessKey();

    HttpServletRequest getHttpRequest();

    HttpRequest getHttpRequest2();
}
