package ai.mypulse.hmacauth.core;

import lombok.Builder;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Builder
@Getter
public class HttpRequest {
    private String method;
    private String path;
    private String queryString;
    private long timestamp;
    private String accessKeyId;
    private byte[] body;

    public InputStream getBody() {
        if (body == null) {
            body = new byte[0];
        }
        return new ByteArrayInputStream(body);
    }
}
