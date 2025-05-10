
package com.example.personalfinancemanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class IncomeDataAdapter implements TransactionDataAdapter {
    private final SingletoneFinanceDataManager dataManager;

    public IncomeDataAdapter(SingletoneFinanceDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void loadData(ListView<String> listView) {
        List<Income> incomes = dataManager.getAllIncomes();
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Income income : incomes) {
            items.add(income.getAmount() + " - " + income.getCategory());
        }
        listView.setItems(items);
        listView.setStyle("-fx-control-inner-background: #629e5f;");
    }

    @Override
    public void updateTotal(Text totalText) {
        double total = dataManager.getTotalIncomes();
        totalText.setText(String.format("+%.2f KZT", total));

        totalText.setFill(Color.rgb(98, 158, 95));
    }
}