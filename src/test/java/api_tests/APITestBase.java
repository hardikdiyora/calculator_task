package api_tests;

import api_utils.CalculatorReqPayload;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import logger.TALogger;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

/***
 * Base class for all API test-classes.
 */
public class APITestBase {
    protected Logger Log;
    String baseURI;

    @BeforeClass
    public void initialization() {
        //Load the Logger class configuration using spring framework.
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(TALogger.class);
        TALogger taLogger = context.getBean(TALogger.class);
        // Get Logger object which is used by sub classes.
        Log = taLogger.getLogger(getClass());
        Log.info("Setting up the Base URI for API testing");
        baseURI = "http://localhost:8080";
        RestAssured.baseURI = baseURI;
    }

    /***
     * To get annuity amount
     * @param reqPayload the request body
     * @param status the response status
     * @return {String} the annuity amount
     */
    public String getAnnuity(CalculatorReqPayload reqPayload, int status) {
        return given()
                .contentType(ContentType.JSON)
                .body(reqPayload.toJsonString())
                .when().post("/calc-annuity")
                .then()
                .statusCode(status)
                .extract().path("annuity");
    }

    /***
     * To get plan for required loan
     * @param reqPayload the request body
     * @param status the response status
     * @return {Response} the plan details with all installments
     */
    public Response getLoanPlan(CalculatorReqPayload reqPayload, int status) {
        return given()
                .contentType(ContentType.JSON)
                .body(reqPayload.toJsonString())
                .when().post("/generate-plan")
                .then()
                .statusCode(status)
                .extract().response();
    }
}
