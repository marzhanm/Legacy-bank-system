package com.example.personalfinancemanager;

public class IncomeFactory implements TransactionFactory {
    private final SingletoneFinanceDataManager dataManager;

    public IncomeFactory(SingletoneFinanceDataManager dataManager){
        this.dataManager = dataManager;
    }
    @Override
    public TransactionDataAdapter createDataAdapter() {
        return new IncomeDataAdapter(dataManager);
    }
}
