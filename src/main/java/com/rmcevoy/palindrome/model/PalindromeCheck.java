package com.rmcevoy.palindrome.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class to model a palindrome request.
 * Generates ID for database along with storing username and text parameters.
 * Stores result of whether the text is a palindrome.
 */
@Document("PalindromeResult")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PalindromeCheck {

    @Id
    private String id;
    private String username;
    private String text;
    private boolean isPalindrome;

    public PalindromeCheck() {
    }

    public PalindromeCheck(String pUsername, String pText, boolean pIsPalindrome) {
        username = pUsername;
        text = pText;
        isPalindrome = pIsPalindrome;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String pId) {
        id = pId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String pUsername) {
        username = pUsername;
    }

    public String getText() {
        return text;
    }

    public void setText(String pText) {
        text = pText;
    }

    public boolean isPalindrome() {
        return isPalindrome;
    }

    public void setPalindrome(boolean pPalindrome) {
        isPalindrome = pPalindrome;
    }
}
