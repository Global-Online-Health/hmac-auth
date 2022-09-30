package ai.mypulse.hmacauth.core;

import java.io.IOException;

/**
 * An interface for generating a canonical request for a given {@link HttpRequest}
 */
public interface CanonicalRequest {
    /**
     * Combines elements from {@link HttpRequest} such as verb, path, query parameters and payload,
     * hashes the response and returns the hexadecimal representation of it.
     * @param request The {@link HttpRequest} that contains the required elements to generate the canonical request.
     * @return A dexadecimal value of the generated canonical request.
     * @throws IOException
     */
    String hashCanonicalRequest(HttpRequest request) throws IOException;
}
