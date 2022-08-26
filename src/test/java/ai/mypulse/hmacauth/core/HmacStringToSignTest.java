package ai.mypulse.hmacauth.core;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static ai.mypulse.hmacauth.utils.Constants.HMAC_ALGORITHM;
import static org.junit.Assert.assertEquals;

public class HmacStringToSignTest {
    @Test
    public void createStringToSignReturnsExpectedString() throws IOException {
        var stringToSign = new HmacStringToSign();
        var signatureTimestamp = Instant.now(Clock.fixed(Instant.parse("2022-01-01T14:00:00Z"),
                ZoneOffset.UTC)).getEpochSecond();
        var accessKey = "test-access-key";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("example");
        request.setRequestURI("example.ai");
        request.setMethod("GET");
        request.setPathInfo("/foo");
        request.addHeader("x-mp-timestamp", signatureTimestamp);
        request.addHeader("X-mp-access-key", accessKey);
        var expectedResult = HMAC_ALGORITHM +
                "\n" + signatureTimestamp +
                "\n" + accessKey +
                "\ne1688f15ba88ed1fdf3279a044ad4d99a301fd14257f0b4dd2b986de4f2edfc8";

        var result = stringToSign.createStringToSign(request);

        assertEquals(expectedResult, result);
    }

    @Test
    public void createStringToSignHandlesUppercaseSignatureTimestampHeader() throws IOException {
        var stringToSign = new HmacStringToSign();
        var signatureTimestamp = Instant.now(Clock.fixed(Instant.parse("2022-01-01T14:00:00Z"),
                ZoneOffset.UTC)).getEpochSecond();
        var accessKey = "test-access-key";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("example");
        request.setRequestURI("example.ai");
        request.setMethod("GET");
        request.setPathInfo("/foo");
        request.addHeader("X-MP-TIMESTAMP", signatureTimestamp);
        request.addHeader("X-MP-ACCESS-KEY", accessKey);
        var expectedResult = HMAC_ALGORITHM +
                "\n" + signatureTimestamp +
                "\n" + accessKey +
                "\ne1688f15ba88ed1fdf3279a044ad4d99a301fd14257f0b4dd2b986de4f2edfc8";

        var result = stringToSign.createStringToSign(request);

        assertEquals(expectedResult, result);
    }
}
