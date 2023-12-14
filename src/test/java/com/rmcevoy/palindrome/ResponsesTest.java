package com.rmcevoy.palindrome;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for Response objects.
 */
public class ResponsesTest {

    @Test
    @DisplayName("Test 500 Response")
    public void testErrorResponse() {
        //create expected response object
        ResponseEntity<?> expectedResponse = Responses.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

        assert(expectedResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Internal server error");  // Use the provided message

        assertEquals(expectedResponse.getBody(), errorResponse);

    }

    @Test
    @DisplayName("Test 200 response")
    public void testSuccessResponse() {
        //create expected response object
        ResponseEntity<?> expectedResponse = Responses.success("Success");

        assert(expectedResponse.getStatusCode() == HttpStatus.OK);

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Success");

        assertEquals(expectedResponse.getBody(), successResponse);
    }
}
