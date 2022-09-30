package ai.mypulse.hmacauth.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static ai.mypulse.hmacauth.utils.Constants.SHA256_ALGORITHM;
import static ai.mypulse.hmacauth.utils.Constants.BUFFER_SIZE;

/**
 * Utilities for hash computations.
 */
public class EncodingUtils {
    /**
     * Computes the hash from a given input stream using the SHA-256 algorithm.
     * @param inputStream The stream (request body) to hash.
     * @return The array of bytes for the resulting hash value.
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static byte[] hash(final InputStream inputStream) throws IOException, IllegalArgumentException {
        Objects.requireNonNull(inputStream);
        try {
            final byte[] buffer = new byte[BUFFER_SIZE];
            final MessageDigest digest = MessageDigest.getInstance(SHA256_ALGORITHM);
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 hashing algorithm unknown.", e);
        }
    }

    /**
     * Computes the hash from a given string using the SHA-256 algorithm.
     * @param input The string input to be hashed.
     * @return The array of bytes for the resulting hash value.
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static byte[] hashString(String input) {
        try {
            final MessageDigest digest = MessageDigest.getInstance(SHA256_ALGORITHM);
            return digest.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 hashing algorithm unknown.", e);
        }
    }

    /**
     * Converts a given array of bytes into an array of characters representing the hexadecimal
     * values of each byte in order.
     * @param bytes The array of bytes to be converted.
     * @return The string value of the converted bytes into hexadecimals.
     */
    public static String hex(byte[] bytes) {
        char[] result = Hex.encodeHex(bytes);
        return new String(result);
    }
}
