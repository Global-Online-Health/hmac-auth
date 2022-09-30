package ai.mypulse.hmacauth.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilsTest {
    @Test
    public void shouldReturnTrueForNullString() {
        assertTrue(StringUtils.isNullOrEmpty(null));
    }

    @Test
    public void shouldReturnTrueForEmptyString() {
        assertTrue(StringUtils.isNullOrEmpty(""));
    }

    @Test
    public void shouldReturnFalseForStringWithValue() {
        assertFalse(StringUtils.isNullOrEmpty("foo"));
    }

    @Test
    public void shouldDoNothingForEmptyString() {
        var result = StringUtils.upperCase(null);

        assertNull(result);
    }

    @Test
    public void shouldTransformToUppercaseForStringWithValue() {
        var input = "aBcD9e_)(";
        var expected = "ABCD9E_)(";
        var result = StringUtils.upperCase(input);

        assertEquals(expected, result);
    }
}
