package com.rmcevoy.palindrome.controller;

import com.rmcevoy.palindrome.Responses;
import com.rmcevoy.palindrome.model.PalindromeCheck;
import com.rmcevoy.palindrome.repository.PalindromeRepository;
import com.rmcevoy.palindrome.service.PalindromeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/palindrome")
public class PalindromeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PalindromeController.class);
    @Autowired
    private PalindromeService palindromeService; //injection
    @Autowired
    private PalindromeRepository palindromeRepository; //injection

    /**
     * Endpoint to check if a text is a palindrome.
     * @param username The username of the user who made the request.
     * @param text The text to check.
     * @return Json response containing an ID, the username, the text, and whether the text is a palindrome.
     */
    @PostMapping("/check")
    public ResponseEntity<?> checkPalindrome(
            @RequestParam String username,
            @RequestParam String text) {

        //check if the text is valid
        if (!PalindromeService.isValidText(text)) {
            return Responses.error(HttpStatus.BAD_REQUEST, "Invalid text");
        }

        //check if the text is a parameter
        boolean isPalindrome = PalindromeService.isPalindrome(text);

        PalindromeCheck response = new PalindromeCheck(username, text, isPalindrome);

        //save to database
        try {
            palindromeRepository.save(response);
            LOGGER.info("Record saved to database with id: " + response.getId());
        } catch (Exception e) {
            LOGGER.error("Error saving record to database", e);
            return Responses.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving record to the database");
        }

        // Return the response as JSON with an OK status
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to fetch a previously analysed piece of text.
     * @param username The name of the user who made the request.
     * @param text The text that was analysed.
     * @return The record from the database in Json format containing the username, text and the palindrome result.
     */
    @GetMapping("/check/{username}/{text}")
    public ResponseEntity<?> getPalindromeByText(@PathVariable String username,
                                                 @PathVariable String text) {

        try {

            PalindromeCheck record;

            List<PalindromeCheck> results = palindromeRepository.findByUsernameAndText(username, text);

            if (!results.isEmpty()) {
                // If there are results, select the first one
                record = results.get(0);

                PalindromeCheck palindromeCheck = new PalindromeCheck(record.getUsername(), record.getText(), record.isPalindrome());

                // Return the record as JSON with an OK status
                return ResponseEntity.ok(palindromeCheck);
            } else {
                // No result found for the given text and username
                LOGGER.info("No record found for username: " + username + " and text: " + text);

                // Return a not found response
                return Responses.error(HttpStatus.NOT_FOUND, "No record found for username: "
                        + username + " and text: " + text);
            }

        } catch (Exception e) {
            // Log the exception for debugging purposes
            LOGGER.error("Error retrieving palindrome record", e);
            return Responses.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving palindrome record");
        }
    }
}
