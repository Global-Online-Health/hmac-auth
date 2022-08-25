package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.utils.HttpUtils;

import java.net.URI;

public abstract class AbstractAuthSigner {
    protected String getResourcePathToCanonical(String resourcePath) {
        String value;

        value = HttpUtils.urlEncode(resourcePath);

        URI normalize = URI.create(value).normalize();
        value = normalize.getRawPath();

        if (!resourcePath.endsWith("/") && value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        if (!value.startsWith("/")) {
            value = "/" + value;
        }
        return value;
    }
}
