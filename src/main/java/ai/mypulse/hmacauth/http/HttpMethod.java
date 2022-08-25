package ai.mypulse.hmacauth.http;

import ai.mypulse.hmacauth.utils.StringUtils;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    PATCH;


    public static HttpMethod fromValue(String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            return null;
        }

        final String upperCaseValue = StringUtils.upperCase(value);
        for (HttpMethod httpMethodName : values()) {
            if (httpMethodName.name().equals(upperCaseValue)) {
                return httpMethodName;
            }
        }
        throw new IllegalArgumentException("Unsupported HTTP method name " + value);
    }
}
