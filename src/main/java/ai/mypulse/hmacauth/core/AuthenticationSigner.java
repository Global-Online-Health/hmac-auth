package ai.mypulse.hmacauth.core;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface AuthenticationSigner {
    String calculateSignature(Signer request) throws IOException, NoSuchAlgorithmException;
}
