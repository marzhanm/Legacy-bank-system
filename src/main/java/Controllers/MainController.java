package Controllers;

import com.example.personalfinancemanager.FinanceManagerApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainController {

    @FXML
    private Button balanceButton;

    @FXML
    private Button expenseButton;

    @FXML
    private Button incomeButton;

    @FXML
    private Button listButton;

    @FXML
    void initialize() {
        incomeButton.setOnAction(event ->{
            try {
                FinanceManagerApp.changeScene("income-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        expenseButton.setOnAction(event ->{
            try {
                FinanceManagerApp.changeScene("expense-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        balanceButton.setOnAction(event ->{
            try {
                FinanceManagerApp.changeScene("balance-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        listButton.setOnAction(event ->{
            try {
                FinanceManagerApp.changeScene("list-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
