package com.rmcevoy.palindrome.repository;

import com.rmcevoy.palindrome.model.PalindromeCheck;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface to define methods for interacting with the database.
 */
@Repository
public interface PalindromeRepository extends MongoRepository<PalindromeCheck, String> {

    /**
     * Searches the database using a username and text.
     * Used for finding if a piece of text has previously been inspected.
     * @param username The user who submitted the request.
     * @param text The text to search for.
     * @return The record.
     */
    @Cacheable("palindrome")
    List<PalindromeCheck> findByUsernameAndText(String username, String text);
}
