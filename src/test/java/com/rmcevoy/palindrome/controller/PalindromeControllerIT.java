package com.rmcevoy.palindrome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmcevoy.palindrome.model.PalindromeCheck;
import com.rmcevoy.palindrome.repository.PalindromeRepository;
import com.rmcevoy.palindrome.service.PalindromeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the PalindromeController class.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(PalindromeController.class)
public class PalindromeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PalindromeService palindromeService;

    @MockBean
    private PalindromeRepository palindromeRepository;

    @Test
    @DisplayName("Test POST Json response is correct when username and text are valid")
    void testPostSuccessResponseWithValidParameters() throws Exception {

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/palindrome/check?username=rmce&text=civic");

        // Performing the request using mockMvc and storing the result actions
        ResultActions resultActions = mockMvc.perform(request)
                // Expecting a successful response with status code 200 (OK)
                .andExpect(status().isOk())
                // Expecting a JSON response with a property "username" and its value is "rmce"
                .andExpect(jsonPath("$.username").value("rmce"))
                .andExpect(jsonPath("$.text").value("civic"))
                .andExpect(jsonPath("$.palindrome").value(true));
    }

    @Test
    @DisplayName("Test POST bad request response and json response")
    void testPostResponseIsBadRequestWhenTextIsInvalid() throws Exception {
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/palindrome/check?username=rmce&text=c1vic");

        // Performing the request using mockMvc and storing the result actions
        ResultActions resultActions = mockMvc.perform(request)
                // Expecting a successful response with status code 400 (Bad request)
                .andExpect(status().isBadRequest())
                // Expecting a JSON response with an error message
                .andExpect(jsonPath("$.error").value("Invalid text"));
    }

    @Test
    @DisplayName("Test POST 500 response when exception is thrown")
    public void testPostCheckPalindromeServerError() throws Exception {
        // Mock the save method in the repository to throw an exception
        Mockito.when(palindromeRepository.save(Mockito.any(PalindromeCheck.class)))
                .thenThrow(new RuntimeException("Simulated error"));

        // Perform the request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.post("/palindrome/check")
                        .param("username", "rmce")
                        .param("text", "level"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Error saving record to the database"));
    }

    @Test
    @DisplayName("Test GET successful record retrieval")
    public void testGetPalindromeByUsernameAndTextSuccess() throws Exception {
        // Mock data
        String username = "rmce";
        String text = "civic";
        boolean isPalindrome = true;

        PalindromeCheck mockRecord = new PalindromeCheck(username, text, isPalindrome);

        // Mock the repository response
        Mockito.when(palindromeRepository.findByUsernameAndText(username, text)).thenReturn(Collections.singletonList(mockRecord));

        // Perform the request and validate the response
        mockMvc.perform(get("/palindrome/check/{username}/{text}", username, text))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.text").value(text))
                .andExpect(jsonPath("$.palindrome").value(isPalindrome));
    }

    @Test
    @DisplayName("Test GET not found response when record doesn't exist")
    public void testGetPalindromeNotFound() throws Exception {
        // Mock data
        String username = "rmce";
        String text = "civic";
        boolean isPalindrome = true;

        PalindromeCheck mockRecord = new PalindromeCheck(username, text, isPalindrome);

        // Mock the repository response
        Mockito.when(palindromeRepository.findByUsernameAndText(username, text)).thenReturn(Collections.singletonList(mockRecord));

        // Perform the request and validate the response
        mockMvc.perform(get("/palindrome/check/{username}/{text}", "jbloggs", text))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("No record found for username: jbloggs and text: civic"));
    }

    @Test
    @DisplayName("Test GET 500 response")
    public void testGetPalindromeByTextServerError() throws Exception {
        // Mock data
        String username = "testUser";
        String text = "level";

        // Mock an exception in the repository
        Mockito.when(palindromeRepository.findByUsernameAndText(username, text))
                .thenThrow(new RuntimeException("Simulated error"));

        // Perform the request and validate the response
        mockMvc.perform(get("/palindrome/check/{username}/{text}", username, text))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Error retrieving palindrome record"));
    }

    @Test
    @DisplayName("Test GET not found response when username empty")
    public void testGetNotFoundWhenUsernameIsEmpty() throws Exception {
        // Mock data
        String username = "";
        String text = "level";

        // Perform the request and validate the response
        mockMvc.perform(get("/palindrome/check/{username}/{text}", username, text))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test GET not found response when text empty")
    public void testGetNotFoundWhenTextIsEmpty() throws Exception {
        // Mock data
        String username = "rmce";
        String text = "";

        // Perform the request and validate the response
        mockMvc.perform(get("/palindrome/check/{username}/{text}", username, text))
                .andExpect(status().isNotFound());
    }
}
