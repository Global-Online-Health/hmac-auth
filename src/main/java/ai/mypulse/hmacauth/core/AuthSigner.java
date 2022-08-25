package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.utils.HttpUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class AuthSigner extends AbstractAuthSigner {
    public static final String SEPARATOR = "\n";

    protected String createCanonicalRequest(SignRequest request) {
        final String path = HttpUtils.appendUri(
                request.getEndpoint().getPath(), request.getResourcePath());

        String canonicalRequestBuilder = request.getHttpMethod().toString() +
                SEPARATOR +
                getResourcePathToCanonicalResource(path) +
                SEPARATOR +
                getQueryParametersToCanonicalQueryString(request.getParameters()) +
                SEPARATOR +
                getContentHash(request.getContent());

        return canonicalRequestBuilder;
    }

    protected String getResourcePathToCanonicalResource(String requestPath) {
        return getResourcePathToCanonical(requestPath);
    }

    protected String getQueryParametersToCanonicalQueryString(Map<String, List<String>> parameters){
        return getQueryParametersToCanonical(parameters);
    }

    protected String getContentHash(InputStream content) {
        var bytes = hash(content);
        return new String(bytes);
    }
}
