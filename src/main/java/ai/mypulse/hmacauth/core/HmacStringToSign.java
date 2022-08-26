package ai.mypulse.hmacauth.core;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static ai.mypulse.hmacauth.utils.Constants.HMAC_ALGORITHM;
import static ai.mypulse.hmacauth.utils.Constants.SEPARATOR;

public class HmacStringToSign implements StringToSign{

    public String createStringToSign(HttpServletRequest request) throws IOException {
        String hashCanonicalRequest = new HmacCanonicalRequest().hashCanonicalRequest(request);
        String requestTimestamp = request.getHeader("x-mp-timestamp");
        String requestAccessKey = request.getHeader("x-mp-access-key");

        return HMAC_ALGORITHM +
                SEPARATOR +
                requestTimestamp +
                SEPARATOR +
                requestAccessKey +
                SEPARATOR +
                hashCanonicalRequest;
    }
}
