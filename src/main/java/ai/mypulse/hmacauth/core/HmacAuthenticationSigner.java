package ai.mypulse.hmacauth.core;

import org.apache.commons.codec.digest.HmacUtils;

import java.io.IOException;
import java.util.Base64;

import static ai.mypulse.hmacauth.utils.EncodingUtils.hex;
import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_256;

public class HmacAuthenticationSigner implements AuthenticationSigner{

    public String calculateSignatureAsHexadecimal(Signer request) throws IOException {
        final byte[] hmac = calculateHmac(request);
        return hex(hmac);
    }

    public String calculateSignatureAsBase64(Signer request) throws IOException {
        final byte[] hmac = calculateHmac(request);
        return Base64.getEncoder().encodeToString(hmac);
    }

    private static byte[] calculateHmac(Signer request) throws IOException {
        String stringToSign = new HmacStringToSign().createStringToSign(request.getHttpRequest());
        String secretAccessKey = request.getSecretAccessKey();
        byte[] secretAccessKeyBase64 = Base64.getDecoder().decode(secretAccessKey);
        final HmacUtils hmacHelper = new HmacUtils(HMAC_SHA_256, secretAccessKeyBase64);

        return hmacHelper.hmac(stringToSign);
    }
}
