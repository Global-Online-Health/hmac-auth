package ai.mypulse.hmacauth.core;

import java.io.IOException;

import static ai.mypulse.hmacauth.utils.Constants.HMAC_ALGORITHM;
import static ai.mypulse.hmacauth.utils.Constants.SEPARATOR;

public class HmacStringToSign implements StringToSign {

    public String createStringToSign(HttpRequest request) throws IOException {
        String hashCanonicalRequest = new HmacCanonicalRequest().hashCanonicalRequest(request);
        return HMAC_ALGORITHM +
                SEPARATOR +
                request.getTimestamp() +
                SEPARATOR +
                request.getAccessKeyId() +
                SEPARATOR +
                hashCanonicalRequest;
    }
}
