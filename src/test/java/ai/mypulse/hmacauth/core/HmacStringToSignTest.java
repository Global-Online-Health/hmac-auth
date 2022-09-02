package ai.mypulse.hmacauth.core;

import org.junit.Test;

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
        HttpRequest request = HttpRequest.builder()
                .method("GET")
                .path("/foo")
                .timestamp(signatureTimestamp)
                .accessKeyId(accessKey)
                .build();
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
        HttpRequest request = HttpRequest.builder()
                .method("GET")
                .path("/foo")
                .timestamp(signatureTimestamp)
                .accessKeyId(accessKey)
                .build();
        var expectedResult = HMAC_ALGORITHM +
                "\n" + signatureTimestamp +
                "\n" + accessKey +
                "\ne1688f15ba88ed1fdf3279a044ad4d99a301fd14257f0b4dd2b986de4f2edfc8";

        var result = stringToSign.createStringToSign(request);

        assertEquals(expectedResult, result);
    }
}
