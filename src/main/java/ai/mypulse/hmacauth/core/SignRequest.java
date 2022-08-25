package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.http.HttpMethod;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface SignRequest {

        Map<String, String> getHeaders();

        String getResourcePath();


        Map<String, List<String>> getParameters();

        URI getEndpoint();

        HttpMethod getHttpMethod();

        InputStream getContent();

        InputStream getContentUnwrapped();

        Object getOriginalRequestObject();
}
