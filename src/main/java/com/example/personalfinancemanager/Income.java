package com.example.personalfinancemanager;

public class Income implements IncomeExpenseBridge {
    private final double amount;
    private final String category;

    public Income(double amount, String category) {
        this.amount = amount;
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

}
