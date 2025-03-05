package Controllers;

import com.example.personalfinancemanager.FinanceManagerApp;
import com.example.personalfinancemanager.Income;
import com.example.personalfinancemanager.SingletoneFinanceDataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class IncomeViewController {

    @FXML
    private Button OkButton;

    @FXML
    private TextField amountField;

    @FXML
    private Button backButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    void initialize() {
        initializeCategories();

        backButton.setOnAction(event ->{
            try {
                FinanceManagerApp.changeScene("main_view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        cancelButton.setOnAction(event -> {
            try {
                FinanceManagerApp.changeScene("main_view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                amountField.setText(newValue.replaceAll("\\D", ""));
            }
        });
        OkButton.setOnAction(event -> handleOkButtonAction());

    }

    private void initializeCategories() {
        categoryChoiceBox.getItems().addAll("Salary", "Bonus", "Investing", "Present", "Other");
        categoryChoiceBox.setValue("Salary");
    }

    private void handleOkButtonAction() {
        String amountStr = amountField.getText();
        if (amountStr.isEmpty()) {
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount == 0) {

                return;
            }

            String category = categoryChoiceBox.getValue();
            Income income = new Income(amount, category);
            SingletoneFinanceDataManager.getInstance().addIncome(income);


            FinanceManagerApp.changeScene("main_view.fxml");
        } catch (NumberFormatException e) {

            System.out.println("Something went wrong with OK button");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

