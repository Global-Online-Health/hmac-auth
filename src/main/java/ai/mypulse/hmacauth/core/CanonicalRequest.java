package ai.mypulse.hmacauth.core;

import java.io.IOException;

public interface CanonicalRequest {
    String hashCanonicalRequest(HttpRequest request) throws IOException;
}
