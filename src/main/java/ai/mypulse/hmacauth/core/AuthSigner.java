package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.utils.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class AuthSigner extends AbstractAuthSigner {
    public static final String SEPARATOR = "\n";
    public static final String HMAC_ALGORITHM = "HMAC-SHA256";

    protected String calculateSignature(Signer request) throws IOException, NoSuchAlgorithmException {
        String stringToSign = createStringToSign(request.getHttpRequest());
        return calculateHmac(request.getSecretAccessKey(), stringToSign);
    }

    protected String createStringToSign(HttpServletRequest request) throws IOException {
        String hashCanonicalRequest = hashCanonicalRequest(request);
        return HMAC_ALGORITHM +
                SEPARATOR +
                request.getHeader("x-mp-timestamp") +
                SEPARATOR +
                request.getHeader("x-mp-access-key") +
                SEPARATOR +
                hashCanonicalRequest;
    }

    protected String hashCanonicalRequest(HttpServletRequest request) throws IOException {
        String canonicalRequest = createCanonicalRequest(request);
        return getContentStringHash(canonicalRequest);
    }

    protected String createCanonicalRequest(HttpServletRequest request) throws IOException {
        final String path = HttpUtils.appendUri(request.getPathInfo());

        // ensure that the inputStream is retrieved before getting the query parameters
        var inputStream = request.getInputStream();

        String canonicalRequestBuilder = request.getMethod() +
                SEPARATOR +
                getResourcePathToCanonical(path) +
                SEPARATOR +
                getQueryParametersToCanonical(request.getQueryString()) +
                SEPARATOR +
                getContentStreamHash(inputStream);

        return canonicalRequestBuilder;
    }
}
