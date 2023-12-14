package com.rmcevoy.palindrome.cache;

import com.rmcevoy.palindrome.model.PalindromeCheck;
import com.rmcevoy.palindrome.repository.PalindromeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Integration tests for cache initialisation.
 */
@ExtendWith(OutputCaptureExtension.class)
@SpringJUnitConfig
class CacheInitialiserIT {

    @Mock
    private PalindromeRepository palindromeRepository;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private CacheInitialiser cacheInitialiser;

    @Test
    @DisplayName("Test cache initialisation when database is empty")
    void testEmptyCacheWhenDatabaseIsEmpty(CapturedOutput pOutput) {
        // Mock the repository to return an empty list
        when(palindromeRepository.findAll()).thenReturn(Collections.emptyList());

        // Mock the cache manager to return the cache
        when(cacheManager.getCache("palindrome")).thenReturn(cache);

        // Mock the application arguments
        ApplicationArguments applicationArguments = Mockito.mock(ApplicationArguments.class);

        // Run the cache initialiser
        cacheInitialiser.run(applicationArguments);

        // Verify that the cache was checked
        Mockito.verify(cacheManager).getCache("palindrome");

        // Verify that the cache was not populated
        Mockito.verify(cache, Mockito.never()).put(any(), any());

        // Verify that the log message indicates an empty cache
        Assertions.assertTrue(pOutput.getOut().contains("Cache initialised with 0 records"));
    }

    /**
     * Test cache initialisation when database is not empty.
     * @param pOutput Used to verify the log output.
     */
    @Test
    @DisplayName("Test cache when database isn't empty")
    void testCacheWhenDatabaseIsNotEmpty(CapturedOutput pOutput) {

        // Create PalindromeCheck object
        PalindromeCheck palindromeCheck = new PalindromeCheck();
        palindromeCheck.setId("1");
        palindromeCheck.setUsername("rmce");
        palindromeCheck.setText("civic");
        palindromeCheck.setPalindrome(true);

        // Mock the repository to return a non-empty list with created object
        when(palindromeRepository.findAll()).thenReturn((List<PalindromeCheck>) Collections.singletonList(palindromeCheck));

        // Mock the cache manager to return the cache
        when(cacheManager.getCache("palindrome")).thenReturn(cache);

        // Mock the application arguments
        ApplicationArguments applicationArguments = Mockito.mock(ApplicationArguments.class);

        // Run the cache initialiser
        cacheInitialiser.run(applicationArguments);

        // Verify that the cache was checked
        Mockito.verify(cacheManager).getCache("palindrome");

        // Verify that the cache was populated
        Mockito.verify(cache).put(any(), any());

        // Verify that the log message indicates a non-empty cache using OutputCapture
        Assertions.assertTrue(pOutput.getOut().contains("Cache initialised with 1 records"));
    }
}
