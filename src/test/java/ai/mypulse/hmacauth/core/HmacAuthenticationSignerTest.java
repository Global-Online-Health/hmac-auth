package ai.mypulse.hmacauth.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HmacAuthenticationSignerTest {

    @Test
    public void shouldCreateSignatureAsHexadecimalForAGetRequest() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var signatureRequest = createSignatureRequest("GET", null, null);
        var expectedResult = "7a66c633fd8b137b892598856fe6d55eb1d590162de3a02b9aa6a11f6e7e8835";

        var result = auth.calculateSignatureAsHexadecimal(signatureRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldCreateSignatureAsHexadecimalForPostRequest() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var values = new HashMap<String, String>() {{
            put("fieldA", "valueA");
            put("fieldB", "valueB");
        }};
        byte[] requestBody = new ObjectMapper().writeValueAsBytes(values);
        var signatureRequest = createSignatureRequest("POST", requestBody, null);
        var expectedResult = "5cb1c4dbabee077799dd80a3bd3b38084685395edd540567d0ec7c744d555a5d";

        var result = auth.calculateSignatureAsHexadecimal(signatureRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldCreateSignatureAsHexadecimalForGetRequestWithQueryStrings() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var queryString = "paramC=valueC&paramB=valueB&paramA=valueA";
        var signatureRequest = createSignatureRequest("GET", null, queryString);
        var expectedResult = "b5717fcf2e9aa39ae3812472c46d2b16d19a641593a01d3538ba78aca3eb4c2d";

        var result = auth.calculateSignatureAsHexadecimal(signatureRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldCreateSignatureAsBase64ForGetRequest() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var signatureRequest = createSignatureRequest("GET", null, null);
        var expectedResult = "embGM/2LE3uJJZiFb+bVXrHVkBYt46ArmqahH25+iDU=";

        var result = auth.calculateSignatureAsBase64(signatureRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldCreateSignatureAsBase64ForPostRequest() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var values = new HashMap<String, String>() {{
            put("fieldA", "valueA");
            put("fieldB", "valueB");
        }};
        byte[] requestBody = new ObjectMapper().writeValueAsBytes(values);
        var signatureRequest = createSignatureRequest("POST", requestBody, null);
        var expectedResult = "XLHE26vuB3eZ3YCjvTs4CEaFOV7dVAVn0Ox8dE1VWl0=";

        var result = auth.calculateSignatureAsBase64(signatureRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldCreateSignatureAsBase64ForGetRequestWithQueryStrings() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var queryString = "paramC=valueC&paramB=valueB&paramA=valueA";
        var signatureRequest = createSignatureRequest("GET", null, queryString);
        var expectedResult = "tXF/zy6ao5rjgSRyxG0rFtGaZBWToB01OLp4rKPrTC0=";

        var result = auth.calculateSignatureAsBase64(signatureRequest);

        assertEquals(expectedResult, result);
    }

    /*
    @Test
    public void generateSignatureFromGivenInputs() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var method = ""; //add in the request method, ie: GET, POST etc
        var path = ""; //add in the request path, ie: events, appointments/{appointmentId}
        var queryString = ""; //add in the string representation of the query parameters, ie: field=[\"valueA\", \"valueB\"]

        var signatureTimestamp = Instant.now().getEpochSecond();

        var accessKey = ""; //add in the hmac access key id
        var secretAccessKey = ""; //add in the hmac secret access key

        var signatureRequest = new AuthSignerRequest();

        HttpRequest request = HttpRequest.builder()
                .method(method)
                .path(path)
                .timestamp(signatureTimestamp)
                .accessKeyId(accessKey)
                .queryString(queryString)
                .body(null)
                .build();

        signatureRequest.setHttpRequest(request);
        signatureRequest.setSecretAccessKey(secretAccessKey);

        var result = auth.calculateSignatureAsBase64(signatureRequest);

        //print the signature timestamp and the generated signature
        System.out.print("Signature Timestamp: " + signatureTimestamp);
        System.out.print("\n");
        System.out.print("Generated Hmac Signature: " + result);

        assertNotNull(result);
    }
*/

    private AuthSignerRequest createSignatureRequest(String method, byte[] requestBody, String queryString) {
        var signatureTimestamp = Instant.now(Clock.fixed(Instant.parse("2022-01-01T14:00:00Z"),
                ZoneOffset.UTC)).getEpochSecond();
        var accessKey = "test_key";
        var signatureRequest = new AuthSignerRequest();
        HttpRequest request = HttpRequest.builder()
                .method(method)
                .path("/events")
                .timestamp(signatureTimestamp)
                .accessKeyId(accessKey)
                .queryString(queryString)
                .body(requestBody)
                .build();

        String secretAccessKey = Base64.getEncoder().encodeToString("test-secret-access-key".getBytes());
        signatureRequest.setHttpRequest(request);
        signatureRequest.setSecretAccessKey(secretAccessKey);

        return signatureRequest;
    }
}
