package com.example.personalfinancemanager;

import java.util.ArrayList;
import java.util.List;


public class BalanceManager {
    private double balance;
    private final List<BalanceObserver> observers = new ArrayList<>();

    public void addObserver(BalanceObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (BalanceObserver observer : observers) {
            observer.updateBalance(balance);
        }
    }

    public void addIncome(double amount) {
        balance += amount;
        notifyObservers();
    }

    public void addExpense(double amount) {
        balance -= amount;
        notifyObservers();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}