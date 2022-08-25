package ai.mypulse.hmacauth.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilsTest {
    @Test
    public void isNullOrEmptyWhenNullValueReturnsTrue() {
        assertTrue(StringUtils.isNullOrEmpty(null));
    }

    @Test
    public void isNullOrEmptyWhenNotNullValueReturnsFalse() {
        assertFalse(StringUtils.isNullOrEmpty("foo"));
    }

    @Test
    public void upperCaseWhenNullDoesNothing() {
        var result = StringUtils.upperCase(null);

        assertNull(result);
    }

    @Test
    public void upperCaseWhenNonEmptyStringTransformsToUppercase() {
        var input = "aBcD9e_)(";
        var expected = "ABCD9E_)(";
        var result = StringUtils.upperCase(input);

        assertEquals(expected, result);
    }
}
