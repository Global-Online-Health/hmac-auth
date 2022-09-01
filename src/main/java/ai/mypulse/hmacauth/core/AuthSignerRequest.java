package ai.mypulse.hmacauth.core;

import lombok.Data;

@Data
public class AuthSignerRequest implements Signer {

    private String secretAccessKey;
    private HttpRequest httpRequest;

}
