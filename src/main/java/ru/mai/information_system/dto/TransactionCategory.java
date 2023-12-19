package ru.mai.information_system.dto;

public class TransactionCategory {

    private int id;
    private int userId;
    private boolean type;
    private String category;

    public TransactionCategory() {}

    public TransactionCategory(int userId, boolean type, String category) {
        this.userId = userId;
        this.type = type;
        this.category = category;
    }

    public TransactionCategory(int id, int userId, boolean type, String category) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "TransactionCategory{" +
                "id=" + id +
                ", userId=" + userId +
                ", type=" + type +
                ", category='" + category + '\'' +
                '}';
    }
}
