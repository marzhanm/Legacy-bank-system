package com.example.personalfinancemanager;

import java.sql.*;
import java.util.*;

public class SingletoneFinanceDataManager {
    private static final SingletoneFinanceDataManager instance = new SingletoneFinanceDataManager();
    private Connection DBconnection;
    private final BalanceManager balanceManager = new BalanceManager();

    private SingletoneFinanceDataManager() {
        try
        {
            String url = "jdbc:mysql://localhost:3306/transactions";
            String user = "root";
            String password = "12345";
            this.DBconnection = DriverManager.getConnection(url, user, password);
            initializeBalance();
        }
        catch (SQLException e) {
            System.out.println("Something is wrong in DB connecting");
        }
    }


    public static SingletoneFinanceDataManager getInstance() {
        return instance;
    }

    public BalanceManager getBalanceManager() {
        return balanceManager;
    }

    public void addIncome(Income income) {
        String query = "INSERT INTO incomes (amount, category) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = DBconnection.prepareStatement(query))
        {
            preparedStatement.setDouble(1, income.getAmount());
            preparedStatement.setString(2, income.getCategory());
            preparedStatement.executeUpdate();
            balanceManager.addIncome(income.getAmount());
        }
        catch (SQLException e) {
            System.out.println("Something is wrong in INSERTION Incomes");
        }
    }

    public void addExpense(Expense expense) {
        String query = "INSERT INTO expenses (amount, category) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = DBconnection.prepareStatement(query))
        {
            preparedStatement.setDouble(1, expense.getAmount());
            preparedStatement.setString(2, expense.getCategory());
            preparedStatement.executeUpdate();
            balanceManager.addExpense(expense.getAmount());
        }
        catch (SQLException e) {
            System.out.println("Something is wrong in INSERTION Expenses ");
        }
    }

    public List<Income> getAllIncomes() {
        List<Income> incomes = new ArrayList<>();
        String query = "SELECT * FROM incomes";
        try (Statement statement = DBconnection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    incomes.add(new Income(resultSet.getDouble("amount"), resultSet.getString("category")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Something is wrong in getAllIncomes method");
        }
        return incomes;
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM expenses";
        try (Statement statement = DBconnection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {expenses.add(new Expense(resultSet.getDouble("amount"),resultSet.getString("category")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Something is wrong in getAllExpenses method");
        }
        return expenses;
    }

    public void initializeBalance() {
        double totalIncomes = getTotalIncomes();
        double totalExpenses = getTotalExpenses();
        balanceManager.setBalance(totalIncomes - totalExpenses);
    }

    public double getTotalIncomes() {
        double total = 0;
        String query = "SELECT SUM(amount) as total FROM incomes";
        try (Statement statement = DBconnection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next())
                total = resultSet.getDouble("total");

        } catch (SQLException e) {
            System.out.println("Something is wrong in getTotalIncomes method");

        }
        return total;
    }

    public double getTotalExpenses() {
        double total = 0;
        String query = "SELECT SUM(amount) as total FROM expenses";
        try (Statement statement = DBconnection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next())
                total = resultSet.getDouble("total");

        } catch (SQLException e) {
            System.out.println("Something is wrong in getTotalExpenses method");

        }
        return total;
    }

}
