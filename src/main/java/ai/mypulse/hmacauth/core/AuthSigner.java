package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.utils.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthSigner extends AbstractAuthSigner {
    public static final String SEPARATOR = "\n";

    protected String hashCanonicalRequest(HttpServletRequest request) throws IOException {
        String canonicalRequest = createCanonicalRequest(request);
        return  getContentStringHash(canonicalRequest);
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
