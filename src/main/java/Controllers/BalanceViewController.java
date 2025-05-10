package Controllers;

import java.io.IOException;

import com.example.personalfinancemanager.BalanceObserver;
import com.example.personalfinancemanager.FinanceManagerApp;
import com.example.personalfinancemanager.SingletoneFinanceDataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class BalanceViewController implements BalanceObserver {

    @FXML
    private Button OkButton;

    @FXML
    private Button backButton;

    @FXML
    private Text balanceText;

    @FXML
    void initialize() {
        SingletoneFinanceDataManager.getInstance().getBalanceManager().addObserver(this);

        updateBalance(SingletoneFinanceDataManager.getInstance().getBalanceManager().getBalance());

        backButton.setOnAction(event -> {
            try {
                FinanceManagerApp.changeScene("main_view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        OkButton.setOnAction(event -> {
            try {
                FinanceManagerApp.changeScene("main_view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void updateBalance(double newBalance) {
        updateBalanceText(newBalance);
    }

    private void updateBalanceText(double balance) {
        balanceText.setText(String.format("%.2f KZT ", balance));
    }
}