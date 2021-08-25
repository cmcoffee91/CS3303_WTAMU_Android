package com.loancalculator.cc916647.loancalculator;

/**
 * Created by cmcoffee91 on 3/7/18.
 */

public class AmortRow {

    private int paymentNum;
    private double paymentAmt;
    private double towardsPrinciple;
    private double towardsInterest;
    private double remainingBalance;

    public AmortRow(int paymentNum, double paymentAmt, double towardsPrinciple, double towardsInterest, double remainingBalance)
    {
        this.paymentAmt = paymentAmt;
        this.paymentNum = paymentNum;
        this.towardsPrinciple = towardsPrinciple;
        this.towardsInterest = towardsInterest;
        this.remainingBalance = remainingBalance;
    }


    public int getPaymentNum() {
        return paymentNum;
    }

    public double getPaymentAmt() {
        return paymentAmt;
    }

    public double getTowardsPrinciple() {
        return towardsPrinciple;
    }

    public double getTowardsInterest() {
        return towardsInterest;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }
}
