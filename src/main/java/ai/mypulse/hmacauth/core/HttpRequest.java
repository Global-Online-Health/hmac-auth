package ai.mypulse.hmacauth.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HttpRequest {
    private String method;
    private String path;
    private String queryString;
    private byte[] body;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public InputStream getBody() {
        return new ByteArrayInputStream(body);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
