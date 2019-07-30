package api_tests.plan_api;

import api_tests.APITestBase;
import api_utils.CalculatorReqPayload;
import api_utils.GeneratedPlan;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;

public class SuccessGenPlanTest extends APITestBase {
    private static Response response;

    @Test(dataProvider = "getSuccessRequestData")
    void verifySuccessResponse(CalculatorReqPayload payload) throws ParseException {

        Log.info("Verify /calc-annuity api");
        String annuity = this.getAnnuity(payload, 200);
        Double calcAnnuity = Double.parseDouble(annuity);
        Log.info("Verify /generate-plan api");
        response = this.getLoanPlan(payload, 200);
        GeneratedPlan[] actualResponse = response.as(GeneratedPlan[].class);

        Log.info("Verify calculated borrower schedule must comply with the given duration in months.");
        Assert.assertEquals((int) payload.getDuration(), actualResponse.length);
        Log.info("Verify total monthly payable amount with annuity");
        Assert.assertTrue(GeneratedPlan.verifyTotalMonthlyPayment(actualResponse, calcAnnuity));
        Log.info("Verify calculated interest amount with the given nominal rate.");
        Assert.assertTrue(GeneratedPlan.verifyCalculatedInterestAmount(actualResponse
                , Double.parseDouble(payload.getNominalRate())));
        Log.info("Verify due date for all installments");
        Assert.assertTrue(GeneratedPlan.verifyDueDate(actualResponse, payload.getStartDate()));
        Log.info("Verify outstanding principle amount for all installments except last");
        Assert.assertTrue(GeneratedPlan.verifyOutstandingPrincipal(actualResponse, payload.getLoanAmount()));
        Log.info("Verify outstanding principle amount for last installment");
        Assert.assertTrue(GeneratedPlan.verifyLastPrincipalAmount(actualResponse));
        Log.info("Verify sum of all installments principal amount is equals to loan amount");
        Assert.assertTrue(GeneratedPlan.verifySumOfAllPrincipalAmounts(actualResponse, payload.getLoanAmount()));
    }

    @DataProvider
    public Object[][] getSuccessRequestData() {
        return new Object[][] {
                {
                    new CalculatorReqPayload("5000", "5", 12, "2018-01-01")
                },
                {
                    new CalculatorReqPayload("15000", "4.5", 14, "2018-01-01")
                },
                {
                    new CalculatorReqPayload("13000", "12.10", 24, "2018-01-01")
                },
                {
                    new CalculatorReqPayload("10000", "4.5", 12, "2018-01-01")
                },
                {
                    new CalculatorReqPayload("5000", "5", 12, "2018-05-30")
                },
                {
                    new CalculatorReqPayload("5000", "5", 12, "2018-03-31")
                }
        };
    }
}
