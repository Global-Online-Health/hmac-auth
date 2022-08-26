package ai.mypulse.hmacauth.core;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface CanonicalRequest {
    String hashCanonicalRequest(HttpServletRequest request) throws IOException;
}
