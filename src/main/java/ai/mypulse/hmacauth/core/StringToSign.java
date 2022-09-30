package ai.mypulse.hmacauth.core;

import java.io.IOException;

/**
 * An interface for generating the string to sign for a given {@link HttpRequest}.
 */
public interface StringToSign {
    /**
     * Generates a string from combining the hashing algorithm used with request elements
     * from a given {@link HttpRequest}.
     *
     * @param request The {@link HttpRequest}.
     * @return A string value of the generated string to sign.
     * @throws IOException
     */
    String createStringToSign(HttpRequest request) throws IOException;
}
