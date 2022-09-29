package ai.mypulse.hmacauth.core;

/**
 * An interface for providing access to the elements required
 * to calculate an authentication signature.
 */
public interface Signer {
    /**
     * Returns the secret access key to be used on signing the request.
     * @return A Base64 string representation of the secret access key.
     */
    String getSecretAccessKey();

    /**
     * Returns the HttpRequest to be used on generating the string to sign.
     * @return A HttpRequest.
     */
    HttpRequest getHttpRequest();
}
