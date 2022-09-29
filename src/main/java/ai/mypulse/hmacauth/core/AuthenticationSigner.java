package ai.mypulse.hmacauth.core;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * An interface for calculating authentication signatures for a given {@link Signer} request.
 */
public interface AuthenticationSigner {
    /**
     * Calculates the hexadecimal representation of an authentication signature from a given {@link Signer}.
     *
     * @param request The {@link Signer} that contains the HttpRequest and the SecretAccessKey.
     * @return A hexadecimal value of the calculated signature.
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    String calculateSignatureAsHexadecimal(Signer request) throws IOException, NoSuchAlgorithmException;

    /**
     * Calculates the Base64 representation of an authentication signature from a given {@link Signer}.
     *
     * @param request The {@link Signer} that contains the HttpRequest and the SecretAccessKey.
     * @return A Base64 value of the calculated signature.
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    String calculateSignatureAsBase64(Signer request) throws IOException, NoSuchAlgorithmException;
}
