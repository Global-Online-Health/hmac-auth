package ai.mypulse.hmacauth.core;

import javax.servlet.http.HttpServletRequest;

public interface Signer {
    public String getSecretAccessKey();

    public HttpServletRequest getHttpRequest();
}
