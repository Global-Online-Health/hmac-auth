package ai.mypulse.hmacauth.core;

import lombok.Data;

/**
 * {@link Signer} implementation that sets and gets the secret access key and HttpRequest
 * to calculate an authentication signature.
 */
@Data
public class AuthSignerRequest implements Signer {

    private String secretAccessKey;
    private HttpRequest httpRequest;

}
