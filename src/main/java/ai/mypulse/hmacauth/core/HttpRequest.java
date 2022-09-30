package ai.mypulse.hmacauth.core;

import lombok.Builder;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * A class for providing access to the components required
 * to generate a canonical request.
 */
@Builder
@Getter
public class HttpRequest {
    private String method;
    private String path;
    private String queryString;
    private long timestamp;
    private String accessKeyId;
    private byte[] body;

    /**
     * Returns the input stream of bytes for the request payload.
     *
     * @return An InputStream.
     */
    public InputStream getBody() {
        if (body == null) {
            body = new byte[0];
        }
        return new ByteArrayInputStream(body);
    }
}
