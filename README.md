### The Test automation framework(TAF) for API automation.

#### Pre-requisite:
 - **Java** as Programming language.
 - **TestNG** as Base Testing framework
 - **Rest-assured** for API automation
 - **Log4j** for Logging feature with spring class dependency injection
 - **Maven** as Build tool
 - **Data Driven** test strategy
 
 **NOTE : Please follow the "Solution.pdf" for details.**
 
 #### Steps to execute:
1. Clone the code.
2. Import the code into IDE for better visibility. (preferably, Intellij IDEA with maven auto-import enabled)
3. Run the provided binary on localhost: borrower-plan (So, It will be available at localhost:8080)
4. Run with “test_suite.xml” file using following command, It will generate the log file named “test-run.log” file in “result” folder. 

        mvn clean test -Dsurefire.suiteXmlFiles=test_suite.xml
       
       
        
