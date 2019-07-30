package api_utils;

import com.google.gson.Gson;

/***
 * Class to represent the request payload/body for calculator.
 */
public class CalculatorReqPayload {
    private String loanAmount;
    private String nominalRate;
    private Integer duration;
    private String startDate;

    public CalculatorReqPayload(String loanAmount, String nominalRate, Integer duration, String startDate) {
        this.loanAmount = loanAmount;
        this.nominalRate = nominalRate;
        this.duration = duration;
        this.startDate = startDate;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getNominalRate() {
        return nominalRate;
    }

    public void setNominalRate(String nominalRate) {
        this.nominalRate = nominalRate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
