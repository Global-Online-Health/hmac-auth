package ai.mypulse.hmacauth.core;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface StringToSign {
    String createStringToSign(HttpServletRequest request) throws IOException;
}
