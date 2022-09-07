package ai.mypulse.hmacauth.core;

import org.apache.commons.codec.digest.HmacUtils;

import java.io.IOException;
import java.util.Base64;

import static ai.mypulse.hmacauth.utils.EncodingUtils.hex;
import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_256;

public class HmacAuthenticationSigner implements AuthenticationSigner{

    public String calculateSignature(Signer request) throws IOException {
        String stringToSign = new HmacStringToSign().createStringToSign(request.getHttpRequest());
        return calculateHmac(request.getSecretAccessKey(), stringToSign);
    }

    private static String calculateHmac(String secretAccessKey, String stringToSign) {
        byte[] secretAccessKeyBase64 = Base64.getDecoder().decode(secretAccessKey);
        final HmacUtils hmacHelper = new HmacUtils(HMAC_SHA_256, secretAccessKeyBase64);

        final byte[] raw = hmacHelper.hmac(stringToSign);
        return hex(raw);
    }
}
