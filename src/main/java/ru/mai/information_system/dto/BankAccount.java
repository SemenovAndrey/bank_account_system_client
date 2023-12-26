package ru.mai.information_system.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private int id;
    private String name;
    private int userId;
    private int bankAccountTypeId;
    private String creationDate;
    private double balance;

    public BankAccount() {}

    public BankAccount(String name, int userId, int bankAccountTypeId) {
        this.name = name;
        this.userId = userId;
        this.bankAccountTypeId = bankAccountTypeId;
        this.creationDate = LocalDate.now().toString();
        this.balance = 0;
    }

    public BankAccount(int id, String name, int userId, int bankAccountTypeId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.bankAccountTypeId = bankAccountTypeId;
        this.creationDate = LocalDate.now().toString();
        this.balance = 0;
    }

    public BankAccount(int id, String name, int userId, int bankAccountTypeId, String creationDate, double balance) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.bankAccountTypeId = bankAccountTypeId;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBankAccountTypeId() {
        return bankAccountTypeId;
    }

    public void setBankAccountTypeId(int bankAccountTypeId) {
        this.bankAccountTypeId = bankAccountTypeId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", name=" + name +
                ", userId=" + userId +
                ", bankAccountTypeId=" + bankAccountTypeId +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                '}';
    }

    public String toJson() {
        return "{id=" + this.id + ", name=" + this.name + ", userId=" + this.userId + ", bankAccountTypeId="
                + this.bankAccountTypeId + ", creationDate=" + this.creationDate + ", balance=" + this.balance + "}";
    }

    public static List<BankAccount> getBankAccounts(String response) {
        if (response.equals("[]")) {
            return null;
        }

        List<BankAccount> bankAccounts = new ArrayList<>();

        response = response.replace("[", "").replace("]", "")
                .replace("{", "");
        response = response.substring(14, response.length() - 1);
        String[] strBankAccounts = response.split("}, BankAccountDTO");
        for (String bankAccount : strBankAccounts) {
            String[] bankAccountArr = bankAccount.split(", ");
            int id = Integer.parseInt(bankAccountArr[0].split("=")[1]);
            String name = bankAccountArr[1].split("=")[1];
            int userId = Integer.parseInt(bankAccountArr[2].split("=")[1]);
            int bankAccountTypeId = Integer.parseInt(bankAccountArr[3].split("=")[1]);
            String date = bankAccountArr[4].split("=")[1];
            double balance = Double.parseDouble(bankAccountArr[5].split("=")[1]);
            bankAccounts.add(new BankAccount(id, name, userId, bankAccountTypeId, date, balance));
        }

        return bankAccounts;
    }

    public void updateBalance(boolean transactionType, double amount) {
        if (transactionType) {
            balance += amount;
        } else {
            balance -= amount;
        }
    }
}
