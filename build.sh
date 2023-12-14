echo Starting Build Process...
mvn clean package -DskipTests

# Check if the Maven build was successful
if [ $? -eq 0 ]; then
    echo Build successful. Launching the application...

    # JAR file name
    JAR_FILE=target/palindrome-inspector-0.0.1-SNAPSHOT.jar

    # Run the JAR file
    java -jar $JAR_FILE
else
    echo Build failed. Please check the build logs for errors.
fi
