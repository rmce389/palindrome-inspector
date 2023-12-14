package com.rmcevoy.palindrome.cache;

import com.rmcevoy.palindrome.model.PalindromeCheck;
import com.rmcevoy.palindrome.repository.PalindromeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class to initialize the cache with records from the database.
 */
@Component
public class CacheInitialiser implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheInitialiser.class);
    private final PalindromeRepository palindromeRepository;
    private final CacheManager cacheManager;

    @Autowired
    public CacheInitialiser(PalindromeRepository pPalindromeRepository, CacheManager pCacheManager) {
        this.palindromeRepository = pPalindromeRepository;
        this.cacheManager = pCacheManager;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Retrieve all records from the repository
        List<PalindromeCheck> allRecords = palindromeRepository.findAll();

        // Get the cache by name
        Cache cache = cacheManager.getCache("palindrome");

        int cacheCount = 0;

        // Populate the cache while avoiding duplicates
        for (PalindromeCheck record : allRecords) {

            // Generate a unique key based on username and text
            String cacheKey = record.getUsername() + record.getText();

            // Check if the key is not already in the cache
            if (cache != null && cache.get(cacheKey) == null) {
                // Cache the record using the generated key
                cache.put(cacheKey, record);
                cacheCount++;
            }
        }

        //log the number of unique records in the cache
        LOGGER.info("Cache initialised with " + cacheCount + " records");

    }
}

