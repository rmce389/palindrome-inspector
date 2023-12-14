package com.rmcevoy.palindrome.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the PalindromeService class.
 */
public class PalindromeServiceTest {

    @Test
    @DisplayName("Test true palindrome detected")
    public void testTruePalindromeDetected() {
        String input = "racecar";
        boolean result = PalindromeService.isPalindrome(input);
        assert result; //result should be true
    }

    @Test
    @DisplayName("Test false palindrome rejected")
    public void testFalsePalindromeRejected() {
        String input = "car";
        boolean result = PalindromeService.isPalindrome(input);
        assert !result; //result should be false
    }


    @Test
    @DisplayName("Test valid text accepted")
    public void testValidTextAccepted() {
        String input = "madam";
        boolean result = PalindromeService.isValidText(input);
        assert result; //result should be true
    }

    @Test
    @DisplayName("Test empty string rejected")
    public void testEmptyStringRejected() {
        String input = "";
        boolean result = PalindromeService.isValidText(input);
        assert !result; //result should be false
    }

    @Test
    @DisplayName("Test string with numbers rejected")
    public void testStringWithNumbersRejected() {
        String input = "m4dam";
        boolean result = PalindromeService.isValidText(input);
        assert !result; //result should be false
    }

    @Test
    @DisplayName("Test string with spaces rejected")
    public void testStringWithSpacesRejected() {
        String input = "m dam";
        boolean result = PalindromeService.isValidText(input);
        assert !result; //result should be false
    }
}
