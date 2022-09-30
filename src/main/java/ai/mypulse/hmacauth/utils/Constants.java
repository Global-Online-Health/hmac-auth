package ai.mypulse.hmacauth.utils;

/**
 * Constants for calculating the signature components.
 */
public class Constants {
    /**
     * New line separator.
     */
    public static final String SEPARATOR = "\n";

    /**
     * SHA256 hashing algorithm used for creating the HMAC signature.
     */
    public static final String HMAC_ALGORITHM = "HMAC-SHA256";

    /**
     * SHA-256 hashing algorithm used for hash computing.
     */
    public static final String SHA256_ALGORITHM = "SHA-256";

    /**
     * Buffer size.
     */
    public static final int BUFFER_SIZE = 1024 * 1024;

    /**
     * UTF-8 encoding as default.
     */
    public static final String DEFAULT_ENCODING = "UTF-8";
}
