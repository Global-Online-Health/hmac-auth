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
                "\ne91ef84669163268d05f8415f1f6afe789a361d4fb64b8534a71c35526e4809f";

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
                "\ne91ef84669163268d05f8415f1f6afe789a361d4fb64b8534a71c35526e4809f";

        var result = stringToSign.createStringToSign(request);

        assertEquals(expectedResult, result);
    }
}
