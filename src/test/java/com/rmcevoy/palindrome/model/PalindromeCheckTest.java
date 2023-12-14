package com.rmcevoy.palindrome.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for PalindromeCheck class.
 */
public class PalindromeCheckTest {

    @Test
    @DisplayName("Test that the PalindromeCheck class can be instantiated")
    public void testParameterisedConstructor() {
        PalindromeCheck palindromeCheck = new PalindromeCheck("jbloggs", "madam", true);

        assert palindromeCheck.getUsername().equals("jbloggs");
        assert palindromeCheck.getText().equals("madam");
        assert palindromeCheck.isPalindrome();
    }
}
