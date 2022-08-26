package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.utils.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthSigner extends AbstractAuthSigner {
    public static final String SEPARATOR = "\n";

    protected String createCanonicalRequest(HttpServletRequest request) throws IOException {
        final String path = HttpUtils.appendUri(request.getPathInfo());

        var inputStream = request.getInputStream();

        String canonicalRequestBuilder = request.getMethod() +
                SEPARATOR +
                getResourcePathToCanonical(path) +
                SEPARATOR +
                getQueryParametersToCanonical(request.getQueryString()) +
                SEPARATOR +
                getContentHash(inputStream);

        return canonicalRequestBuilder;
    }
}
