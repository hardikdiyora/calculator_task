package api_tests.plan_api;

import api_tests.APITestBase;
import api_utils.CalculatorReqPayload;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FailedToGenPlanTest extends APITestBase {
    private static Response response;

    @Test(dataProvider = "getFailedRequestData")
    void verifyFailedResponse(CalculatorReqPayload payload, String failureMsg) {
        Log.info("Verify /generate-plan api");
        response = this.getLoanPlan(payload, 400);
        Log.info("Verify invalid request data is not acceptable");
        Assert.assertEquals(failureMsg, response.path("error"));
    }

    @DataProvider
    public Object[][] getFailedRequestData() {
        return new Object[][] {
                {new CalculatorReqPayload("5000", "5", 12, "2018-01-1")
                        , "Invalid date format."},
                {new CalculatorReqPayload("5000", "5", 12, "2018-0-01")
                        , "Invalid date format."},
                {new CalculatorReqPayload("5000", "5", 12, "28-01-01")
                        , "Invalid date format."},
                {new CalculatorReqPayload("5000", "5", 12, "2018-")
                        , "Invalid date format."},
                {new CalculatorReqPayload("5000", "5", 12, null)
                        , "Invalid date format."},
                {new CalculatorReqPayload("5000", null, 12, "2018-01-01")
                        , "Invalid nominal rate."},
                {new CalculatorReqPayload("5000", "5 %", 12, "2018-01-01")
                        , "Invalid nominal rate."},
                {new CalculatorReqPayload(null, "5", 12, "2018-01-01")
                        , "Invalid loan amount."},
                {new CalculatorReqPayload("5000$", "5", 12, "2018-01-01")
                        , "Invalid loan amount."}
        };
    }
}
