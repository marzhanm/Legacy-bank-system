package com.example.personalfinancemanager;

public class ExpenseFactory implements TransactionFactory {
    private final SingletoneFinanceDataManager dataManager;

    public ExpenseFactory(SingletoneFinanceDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public TransactionDataAdapter createDataAdapter() {
        return new ExpenseDataAdapter(dataManager);
    }
}