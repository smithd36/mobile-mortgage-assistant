/**
 * The Calculation class handles mortgage calculations.
 *
 * This class encapsulates the logic for performing mortgage calculations,
 * including the calculation of the monthly mortgage payment based on the
 * principal amount, interest rate, and mortgage period.
 *
 * @author Drey Smith
 * @since 09/30/2023
 */
package com.example.mortgagecalculator;

public class Calculation {
    private double principle;
    private double interestRate;
    private double mortgagePeriod;

    /**
     * Default constructor for the Calculation class.
     * Initializes the calculation parameters to default values.
     */
    public Calculation() {
        // set all values to 0 initially
        this.principle = 0.0;
        this.interestRate = 0.0;
        this.mortgagePeriod = 0.0;
    }

    /**
     * Calculate the monthly mortgage payment.
     *
     * Uses formula provided in assignment description:
     *      M = P [ i(1 + i)^n ] / [ (1 + i)^n – 1]
     *
     * @param principle The principal amount of the mortgage ($).
     * @param interestRate  The annual interest rate (%).
     * @param mortgagePeriod The mortgage period in years.
     * @return The calculated monthly mortgage payment ($).
     */
    public double calculateMonthlyPayment(double principle, double interestRate, double mortgagePeriod) {
        // Calculate the monthly interest rate by dividing the annual interest rate (in percentage) by 100
        // to convert it to a decimal, and then dividing by 12 to get the monthly rate.
        double monthlyInterestRate = interestRate / 100 / 12;

        // Calculate the total number of monthly payments over the entire mortgage period
        // by multiplying the number of years (mortgagePeriod) by 12 (the number of months in a year).
        int numberOfPayments = (int) (mortgagePeriod * 12);

        double monthlyPayment;

        if (monthlyInterestRate == 0) {
            // If there's no interest, use a simple calculation
            monthlyPayment = principle / numberOfPayments;
        } else {
            // Calculate the monthly payment using the formula M = P [ i(1 + i)^n ] / [ (1 + i)^n – 1 ]
            double numerator = principle * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments);
            double denominator = Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1;
            monthlyPayment = numerator / denominator;
        }

        return monthlyPayment;
    }

    /**
     * Set the principal amount.
     *
     * @param principle The principal amount of the mortgage ($).
     */
    public void setPrinciple(double principle) {
        this.principle = principle;
    }

    /**
     * Get the principal amount.
     *
     * @return The principal amount of the mortgage ($).
     */
    public double getPrinciple() {
        return principle;
    }

    /**
     * Set the annual interest rate.
     *
     * @param interestRate The annual interest rate (%).
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Get the annual interest rate.
     *
     * @return The annual interest rate (%).
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Set the mortgage period in years.
     *
     * @param mortgagePeriod The mortgage period in years.
     */
    public void setMortgagePeriod(double mortgagePeriod) {
        this.mortgagePeriod = mortgagePeriod;
    }

    /**
     * Get the mortgage period in years.
     *
     * @return The mortgage period in years.
     */
    public double getMortgagePeriod() {
        return mortgagePeriod;
    }
}