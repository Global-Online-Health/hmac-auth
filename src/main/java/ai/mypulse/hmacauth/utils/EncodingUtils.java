package ai.mypulse.hmacauth.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class EncodingUtils {
    public static byte[] hash(final InputStream inputStream) throws IOException, IllegalArgumentException {
        Objects.requireNonNull(inputStream);

        try {
            final byte[] buffer = new byte[Constants.BUFFER_SIZE];
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 hashing algorithm unknown.", e);
        }
    }

    public static byte[] hashString(String text) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(text.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 hashing algorithm unknown.", e);
        }
    }

    public static String hex(byte[] bytes) {
        char[] result = Hex.encodeHex(bytes);
        return new String(result);
    }
}
