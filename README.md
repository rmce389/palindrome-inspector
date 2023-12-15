# Palindrome Inspector

Required to run:
-  Java 17
-  Maven
- The database is MongoDB Atlas. Access has been allowed for 1 week. Please contact me if there are any issues.

Assume that a palindrome record is tied to a user. jbloggs with the text 'civic' is different to a jdoe with the text 'civic', and so retrieval of a record requires a username and text.
 - It is acknowledged that the most desirable output is that the username and text not be linked however time constraints mean that this could be changed in a later version improving the memory usage of the cache/database. 

To run, execute `build.sh` script found in the project directory.
Tests can be executed with ```mvn test```. 

### Postman Collection
A postman collection is available in the repository to test the endpoints.

Endpoints exist for:
- Requesting an inspection
- Retrieving a previous inspection
- Clearing the cache

<img width="145" alt="Palindrome Inspector Image" src="https://github.com/rmce389/palindrome-inspector/assets/37864814/3c1782ee-224a-4dc3-993e-9701f581b158">

### Coverage Report

<img width="364" alt="Coverage Report" src="https://github.com/rmce389/palindrome-inspector/assets/37864814/1e5996bf-0b1f-46d2-b7b9-27cdee01e5d9">
