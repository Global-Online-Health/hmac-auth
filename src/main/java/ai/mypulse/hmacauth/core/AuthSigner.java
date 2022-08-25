package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.utils.HttpUtils;

public class AuthSigner extends AbstractAuthSigner {
    protected String createCanonicalRequest(SignRequest request) {
        final String path = HttpUtils.appendUri(
                request.getEndpoint().getPath(), request.getResourcePath());

        String canonicalRequestBuilder = request
                .getHttpMethod().toString() + "\n" +
                getResourcePathToCanonicalResource(path);

        return canonicalRequestBuilder;
    }

    protected String getResourcePathToCanonicalResource(String requestPath){
        return getResourcePathToCanonical(requestPath);
    }
}
