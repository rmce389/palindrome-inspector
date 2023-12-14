package com.rmcevoy.palindrome;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Class containing responses that can be constructed with HTTP status codes and messages.
 */
public class Responses {

    /**
     * Constructs an error response with a JSON body containing the provided message.
     * @param pStatus The HTTP status code to return.
     * @param pMessage The message to return.
     * @return The JSON response to return.
     */
    public static ResponseEntity<?> error(HttpStatus pStatus, String pMessage) {
        // Return a bad request response with a JSON body
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", pMessage);  // Use the provided message

        // Build a response with the status parameter and error response map
        return ResponseEntity.status(pStatus).body(errorResponse);
    }

    public static ResponseEntity<?> success(String pMessage) {
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", pMessage);
        return ResponseEntity.ok(successResponse);  // Return a 200 OK response with a JSON body
    }
}
