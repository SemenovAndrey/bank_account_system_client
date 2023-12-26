package ru.mai.information_system.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private int id;
    private int bankAccountId;
    private double amount;
    private int transactionCategoryId;
    private String transactionDate;

    public Transaction() {}

    public Transaction(int bankAccountId, double amount, int transactionCategoryId) {
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.transactionCategoryId = transactionCategoryId;
        this.transactionDate = String.valueOf(LocalDate.now());
    }

    public Transaction(int id, int bankAccountId, double amount, int transactionCategoryId) {
        this.id = id;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.transactionCategoryId = transactionCategoryId;
        this.transactionDate = String.valueOf(LocalDate.now());
    }

    public Transaction(int id, int bankAccountId, double amount, int transactionCategoryId,
                       String transactionDate) {
        this.id = id;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.transactionCategoryId = transactionCategoryId;
        this.transactionDate = transactionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(int bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTransactionCategoryId() {
        return transactionCategoryId;
    }

    public void setTransactionCategoryId(int transactionCategoryId) {
        this.transactionCategoryId = transactionCategoryId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", bankAccountId=" + bankAccountId +
                ", amount=" + amount +
                ", transactionCategoryId=" + transactionCategoryId +
                ", transactionDate='" + transactionDate + '\'' +
                '}';
    }

    public static Transaction convertFromString(String response) {
        if (!response.startsWith("Transaction")) {
            return null;
        }

        if (!Character.toString(response.charAt(14)).equals("{")) {
            return null;
        }

        response = response.replace("TransactionDTO", "").replace("{", "")
                .replace("}", "").replace("'", "");

        String[] info = response.split(", ");
        int id = Integer.parseInt(info[0].split("=")[1]);
        int bankAccountId = Integer.parseInt(info[1].split("=")[1]);
        double amount = Double.parseDouble(info[2].split("=")[1]);
        int transactionCategoryId = Integer.parseInt(info[3].split("=")[1]);
        String transactionDate = info[4].split("=")[1];

        return new Transaction(id, bankAccountId, amount, transactionCategoryId, transactionDate);
    }

    public static List<Transaction> getTransactionsList(String response) {
        if (response.equals("[]")) {
            return null;
        }

        List<Transaction> transactions = new ArrayList<>();

        response = response.replace("TransactionDTO", "").replace("[", "")
                .replace("]", "").replace("{", "").replace("'", "");
        response = response.substring(0, response.length() - 1);
        String[] strTransactions = response.split("}, ");
        for (String transaction : strTransactions) {
            String[] transactionArr = transaction.split(", ");
            int id = Integer.parseInt(transactionArr[0].split("=")[1]);
            int bankAccountId = Integer.parseInt(transactionArr[1].split("=")[1]);
            double amount = Double.parseDouble(transactionArr[2].split("=")[1]);
            int transactionCategoryId = Integer.parseInt(transactionArr[3].split("=")[1]);
            String transactionDate = transactionArr[4].split("=")[1];
            transactions.add(new Transaction(id, bankAccountId, amount, transactionCategoryId, transactionDate));
        }

        return transactions;
    }
}
