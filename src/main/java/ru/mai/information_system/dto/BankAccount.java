package ru.mai.information_system.dto;

public class BankAccount {

    private int id;
    private String name;
    private int userId;
    private int bankAccountTypeId;
    private String creationDate;
    private double balance;

    public BankAccount() {}

    public BankAccount(String name, int userId, int bankAccountTypeId, String creationDate, double balance) {
        this.name = name;
        this.userId = userId;
        this.bankAccountTypeId = bankAccountTypeId;
        this.creationDate = creationDate;
        this.balance = balance;
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
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", bankAccountTypeId=" + bankAccountTypeId +
                ", creationDate='" + creationDate + '\'' +
                ", balance=" + balance +
                '}';
    }
}
