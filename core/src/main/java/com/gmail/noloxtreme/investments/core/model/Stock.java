package com.gmail.noloxtreme.investments.core.model;

public class Stock {
    public String symbol;
    public String name;
    public String industry;
    public String priceToEarnings;
    public String price;
    public String priceToSales;
    public String priceToBook;
    public String freeCashFlow;
    public String debtToEquity;
    public String quickRatio;
    public String dividendYield;
    public String revenue;
    public String marketCap;
    public String yearHigh;
    public String yearLow;
    public String yearReturn;

    public String getDeadCatBounce() {
        try {
            Double yearLow = Double.valueOf(this.yearLow);
            Double price = Double.valueOf(this.price);
            Double change = (price - yearLow) / yearLow;
            return String.format("%.2f", 100 * change);
        } catch (ArithmeticException ae) {
            return "-";
        }
    }

    public String changeFromHigh() {
        try {
            Double yearHigh = Double.valueOf(this.yearHigh);
            Double price = Double.valueOf(this.price);
            Double change = (price - yearHigh) / yearHigh;
            return String.format("%.2f", 100 * change);
        } catch (ArithmeticException ae) {
            return "-";
        }
    }
}
