package com.example.personalfinancemanager;

import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public interface TransactionDataAdapter {
    void loadData(ListView<String> listView);
    void updateTotal(Text totalText);
}
