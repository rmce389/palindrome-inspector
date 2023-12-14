package com.rmcevoy.palindrome.service;

import org.springframework.stereotype.Service;

/**
 * Class to hold methods related to validation and checking if the text is a palindrome.
 */
@Service
public class PalindromeService {

    /**
     * Checks if the string is a palindrome by comparing it to its reversed self.
     * @param pStr The string to check.
     * @return Returns true if the string is a palindrome.
     */
    public static boolean isPalindrome(String pStr) {
        return pStr.contentEquals(new StringBuilder(pStr).reverse());
    }

    /**
     * Checks whether a string contains numbers, spaces and punctuation.
     * Using loop for performance
     * @param pStr The string to validate.
     * @return Returns true if the text is only text.
     */
    public static boolean isValidText(String pStr) {
        if (pStr.isEmpty()) {
            return false;
        }
        char[] chars = pStr.toLowerCase().toCharArray(); // Convert to lowercase char array
        // Loop through and check if each char is a letter. Return false if not
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
}
