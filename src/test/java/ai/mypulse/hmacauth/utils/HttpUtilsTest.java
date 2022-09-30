package ai.mypulse.hmacauth.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpUtilsTest {
    @Test
    public void shouldReturnEmptyStringForNullUrl() {
        var expected = "";

        var result = HttpUtils.urlEncode(null, true);

        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnEmptyStringForEmptyUrl() {
        var expected = "";

        var result = HttpUtils.urlEncode("", true);

        assertEquals(expected, result);
    }

    @Test
    public void shouldDoNothingForUrlContainingUnreservedCharacters() {
        var unreservedCharacters = "0123456789"
                + "abcdefghijklmnopqrstuvwxyz"
                + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "-_.~";

        var result = HttpUtils.urlEncode(unreservedCharacters, true);

        assertEquals(unreservedCharacters, result);
    }

    @Test
    public void shouldEncodeReservedCharacters() {
        var expected = "%09%0A%0D%20%21%22%23%24%25%26%27%28%29%2A%2B%2C%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E%60%7B%7C%7D";
        var reservedChars = "\t\n\r !\"#$%&'()*+,:;<=>?@[\\]^`{|}";

        var result = HttpUtils.urlEncode(reservedChars, true);

        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnTrailingSlashForEmptyPath() {
       var path = "";

        var result = HttpUtils.appendUri(path);

        assertEquals("/", result);
    }

    @Test
    public void shouldPrependSlashForPathWithoutSlash() {
        var path = "foo/bar";
        var expected = "/foo/bar";

        var result = HttpUtils.appendUri(path);

        assertEquals(expected, result);
    }
}
