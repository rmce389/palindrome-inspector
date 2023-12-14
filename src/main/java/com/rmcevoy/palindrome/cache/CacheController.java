package com.rmcevoy.palindrome.cache;

import com.rmcevoy.palindrome.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class to manage cache through endpoints
 */
@RestController
@RequestMapping("/palindrome/cache")
public class CacheController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private CacheManager cacheManager;

    /**
     * Endpoint to clear the cache.
     * @return Json response containing a message indicating whether the cache has been cleared.
     */
    @PostMapping("/clear")
    @CacheEvict(allEntries = true, value = "palindrome")
    public ResponseEntity<?> clearCache() {

        try {
            LOGGER.info("Clearing cache...");

            cacheManager.getCache("palindrome").clear();

            LOGGER.info("Cache cleared.");
            return Responses.success("Cache cleared.");
        } catch (Exception e) {
            LOGGER.error("Error clearing cache.", e);
            return Responses.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error clearing cache.");
        }
    }
}
