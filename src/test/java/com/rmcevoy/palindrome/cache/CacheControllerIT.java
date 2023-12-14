package com.rmcevoy.palindrome.cache;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Integration tests for the CacheController.
 */
@WebMvcTest(CacheController.class)
class CacheControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CacheManager cacheManager;

    @Test
    @DisplayName("Test that endpoint clears cache")
    void testCacheClears() throws Exception {
        // Mock the Cache instance
        Cache cache = mock(Cache.class);

        // Stub the cacheManager.getCache() method
        when(cacheManager.getCache("palindrome")).thenReturn(cache);

        // Mock the cache clearing
        doNothing().when(cache).clear();

        // Perform the request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/palindrome/cache/clear")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.message").value("Cache cleared."));

    }
}