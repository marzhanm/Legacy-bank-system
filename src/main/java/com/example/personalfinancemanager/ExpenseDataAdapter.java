package com.example.personalfinancemanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class ExpenseDataAdapter implements TransactionDataAdapter {
    private final SingletoneFinanceDataManager dataManager;

    public ExpenseDataAdapter(SingletoneFinanceDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void loadData(ListView<String> listView) {
        List<Expense> expenses = dataManager.getAllExpenses();
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Expense expense : expenses) {
            items.add(expense.getAmount() + " - " + expense.getCategory());
        }
        listView.setItems(items);
        listView.setStyle("-fx-control-inner-background: #e72121;");
    }

    @Override
    public void updateTotal(Text totalText) {
        double total = dataManager.getTotalExpenses();
        totalText.setText(String.format("-%.2f KZT", total));
        totalText.setFill(Color.rgb(231,33,33));
    }
}