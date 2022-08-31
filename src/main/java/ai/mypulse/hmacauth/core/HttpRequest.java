package ai.mypulse.hmacauth.core;

import lombok.Data;
import lombok.Singular;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Data
public class HttpRequest {
    private String method;
    private String path;
    private String queryString;
    private long timestamp;
    private String accessKey;
    private byte[] body = new byte[0];

    public InputStream getBody() {
        return new ByteArrayInputStream(body);
    }
}
