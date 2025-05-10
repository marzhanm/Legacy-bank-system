package Controllers;


import com.example.personalfinancemanager.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;

public class ListViewController {

    @FXML
    private Button backButton;

    @FXML
    private Text balanceText;

    @FXML
    private Button expensesButton;

    @FXML
    private Button incomesButton;

    @FXML
    private ListView<String> transactionsListView;

    private final SingletoneFinanceDataManager dataManager = SingletoneFinanceDataManager.getInstance();

    @FXML
    void initialize() {

        backButton.setOnAction(event ->{
            try {
                FinanceManagerApp.changeScene("main_view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        incomesButton.setOnAction(event -> switchFactory(new IncomeFactory(dataManager)));
        expensesButton.setOnAction(event -> switchFactory(new ExpenseFactory(dataManager)));
        switchFactory(new IncomeFactory(dataManager));


    }
    private void switchFactory(TransactionFactory factory) {
        TransactionDataAdapter currentAdapter = factory.createDataAdapter();
        currentAdapter.loadData(transactionsListView);
        currentAdapter.updateTotal(balanceText);

    }


}
