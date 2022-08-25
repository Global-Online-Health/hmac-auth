package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.http.HttpMethod;

import java.io.InputStream;
import java.net.URI;
import java.util.*;

public class AuthSignRequest implements SignRequest {
    private String resourcePath;
    private Map<String, List<String>> parameters = new LinkedHashMap<String, List<String>>();
    private Map<String, String> headers = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
    private URI endpoint;
    private HttpMethod httpMethod = HttpMethod.POST;
    private InputStream content;
    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setParameters(Map<String, List<String>> parameters) {
        this.parameters.clear();
        this.parameters.putAll(parameters);
    }
    public void setHeaders(Map<String, String> headers) {
        this.headers.clear();
        this.headers.putAll(headers);
    }
    public void setContent(InputStream content) {
        this.content = content;
    }
    public InputStream getContent() {
        return content;
    }

    @Override
    public InputStream getContentUnwrapped() {
        return null;
    }

    @Override
    public Object getOriginalRequestObject() {
        return null;
    }

    public URI getEndpoint() {
        return endpoint;
    }
    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }
    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
    public AuthSignRequest withParameter(String name, String value) {
        addParameter(name, value);
        return this;
    }
    public Map<String, List<String>> getParameters() {
        return parameters;
    }
    public void addParameters(String name, List<String> values) {
        if (values == null) return;
        for (String value : values) {
            addParameter(name, value);
        }
    }
    public void addParameter(String name, String value) {
        List<String> paramList = parameters.get(name);
        if (paramList == null) {
            paramList = new ArrayList<String>();
            parameters.put(name, paramList);
        }
        paramList.add(value);
    }
    public String getResourcePath() {
        return resourcePath;
    }
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }


}
