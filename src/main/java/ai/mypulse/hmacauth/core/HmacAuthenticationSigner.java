package ai.mypulse.hmacauth.core;

import org.apache.commons.codec.digest.HmacUtils;

import java.io.IOException;

import static ai.mypulse.hmacauth.utils.EncodingUtils.hex;
import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_256;

public class HmacAuthenticationSigner implements AuthenticationSigner{

    public String calculateSignature(Signer request) throws IOException {
        String stringToSign = new HmacStringToSign().createStringToSign(request.getHttpRequest());
        return calculateHmac(request.getSecretAccessKey(), stringToSign);
    }

    private String calculateHmac(String secretAccessKey, String stringToSign) {
        final HmacUtils hmacHelper = new HmacUtils(HMAC_SHA_256, secretAccessKey);

        final byte[] raw = hmacHelper.hmac(stringToSign);
        return hex(raw);
    }
}
