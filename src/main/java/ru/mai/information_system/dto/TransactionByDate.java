package ru.mai.information_system.dto;

import java.util.ArrayList;
import java.util.List;

public class TransactionByDate {

    private int id;
    private int bankAccountId;
    private double amount;
    private int transactionCategoryId;
    private String transactionDate;

    public TransactionByDate() {}

    public TransactionByDate(int bankAccountId, double amount, int transactionCategoryId,
                             String transactionDate) {
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.transactionCategoryId = transactionCategoryId;
        this.transactionDate = transactionDate;
    }

    public TransactionByDate(int id, int bankAccountId, double amount, int transactionCategoryId,
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
        return "TransactionByDate{" +
                "id=" + id +
                ", bankAccountId=" + bankAccountId +
                ", amount=" + amount +
                ", transactionCategoryId=" + transactionCategoryId +
                ", transactionDate='" + transactionDate + '\'' +
                '}';
    }

    public static List<TransactionByDate> getTransactionsBydateList(String response) {
        if (response.equals("[]")) {
            return null;
        }

        List<TransactionByDate> transactionsByDate = new ArrayList<>();

        response = response.replace("TransactionDTO", "").replace("[", "")
                .replace("]", "").replace("{", "").replace("'", "");
        response = response.substring(0, response.length() - 1);
        String[] strTransactions = response.split("}, ");
        for (String transactionByDate : strTransactions) {
            String[] transactionArr = transactionByDate.split(", ");
            int id = Integer.parseInt(transactionArr[0].split("=")[1]);
            int bankAccountId = Integer.parseInt(transactionArr[1].split("=")[1]);
            double amount = Double.parseDouble(transactionArr[2].split("=")[1]);
            int transactionCategoryId = Integer.parseInt(transactionArr[3].split("=")[1]);
            String transactionDate = transactionArr[4].split("=")[1];
            transactionsByDate.add(new TransactionByDate(id, bankAccountId, amount,
                    transactionCategoryId, transactionDate));
        }

        return transactionsByDate;
    }
}
