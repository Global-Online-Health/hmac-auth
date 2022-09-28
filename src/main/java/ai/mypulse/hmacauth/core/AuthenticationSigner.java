package ai.mypulse.hmacauth.core;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface AuthenticationSigner {
    String calculateSignatureAsHexadecimal(Signer request) throws IOException, NoSuchAlgorithmException;
    String calculateSignatureAsBase64(Signer request) throws IOException, NoSuchAlgorithmException;
}
