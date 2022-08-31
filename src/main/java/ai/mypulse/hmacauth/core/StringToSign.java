package ai.mypulse.hmacauth.core;

import java.io.IOException;

public interface StringToSign {
    String createStringToSign(HttpRequest request) throws IOException;
}
