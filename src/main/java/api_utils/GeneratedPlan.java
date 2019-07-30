package api_utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/***
 * Class to represent the generated plan
 */
public class GeneratedPlan {
    private Double borrowerPaymentAmount;
    private Date date;
    private Double initialOutstandingPrincipal;
    private Double interest;
    private Double principal;
    private Double remainingOutstandingPrincipal;

    public Double getBorrowerPaymentAmount() {
        return borrowerPaymentAmount;
    }

    public void setBorrowerPaymentAmount(Double borrowerPaymentAmount) {
        this.borrowerPaymentAmount = borrowerPaymentAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getInitialOutstandingPrincipal() {
        return initialOutstandingPrincipal;
    }

    public void setInitialOutstandingPrincipal(Double initialOutstandingPrincipal) {
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getRemainingOutstandingPrincipal() {
        return remainingOutstandingPrincipal;
    }

    public void setRemainingOutstandingPrincipal(Double remainingOutstandingPrincipal) {
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }

    public static Boolean verifyCalculatedInterestAmount(GeneratedPlan[] actualResponse, Double rate) {
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        for (GeneratedPlan plan : actualResponse){
            Double interestAmount = Math.round(((plan.getInitialOutstandingPrincipal()*rate)/1200) * 100D ) / 100D;
            result.add(plan.getInterest().compareTo(interestAmount) == 0);
        }
        return !result.contains(false);
    }

    public static Boolean verifyTotalMonthlyPayment(GeneratedPlan[] actualResponse, Double annuity) {
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        int len = actualResponse.length;
        for (int i = 0; i < len-1; i++) {
            result.add(String.format("%.2f", actualResponse[i].getInterest() + actualResponse[i].getPrincipal())
                    .equals(annuity.toString()));
        }
        result.add(String.format("%.2f", actualResponse[len-1].getBorrowerPaymentAmount()).equals(String.format("%.2f",
                actualResponse[len-1].getInterest() + actualResponse[len-1].getPrincipal())));
        return !result.contains(false);
    }

    public static Boolean verifyDueDate(GeneratedPlan[] actualResponse, String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int expectedDate = format.parse(date).getDate();
        //The new date calculated when the expectedDate is on of (29, 30, 31).
        int newDate = expectedDate > 30 ? expectedDate % 30 : (expectedDate + 2) % 30;
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        for (GeneratedPlan plan : actualResponse){
            result.add((plan.getDate().getDate() == expectedDate) || (plan.getDate().getDate() == newDate));
        }
        System.out.println(result);
        return !result.contains(false);
    }

    public static Boolean verifyOutstandingPrincipal(GeneratedPlan[] actualResponse, String loanAmount) {
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        int len = actualResponse.length;
        for (int i = 0; i < len-1; i++) {
            result.add(actualResponse[i].getRemainingOutstandingPrincipal().compareTo(
                    actualResponse[i+1].getInitialOutstandingPrincipal()
            ) == 0);
        }
        result.add(actualResponse[0].getInitialOutstandingPrincipal().compareTo(Double.parseDouble(loanAmount)) == 0);
        return !result.contains(false);
    }

    public static Boolean verifyLastPrincipalAmount(GeneratedPlan[] actualResponse){
        return actualResponse[actualResponse.length-1].getRemainingOutstandingPrincipal().
                compareTo(Double.parseDouble("0.00")) == 0;
    }

    public static Boolean verifySumOfAllPrincipalAmounts(GeneratedPlan[] actualResponse, String amount) {
        Double loanAmount = 0.00;
        for (GeneratedPlan plan : actualResponse) {
            loanAmount += plan.getPrincipal();
        }
        return String.format("%.2f", loanAmount).equals(String.format("%.2f", Double.parseDouble(amount))) ;
    }
}
