package ru.mai.information_system.dto;

import java.util.ArrayList;
import java.util.List;

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

    public static List<TransactionCategory> getTransactionCategoriesList(String response) {
        if (response == null || response.equals("[]")) {
            return null;
        }

        List<TransactionCategory> transactionCategories = new ArrayList<>();

        response = response.replace("[", "").replace("]", "")
                .replace("{", "").replace("'", "");
        response = response.substring(22, response.length() - 1);
        String[] transactionCategoriesStr = response.split("}, TransactionCategoryDTO");

        for (int i = 0; i < transactionCategoriesStr.length; i++) {
            String[] transactionCategoryStr = transactionCategoriesStr[i].split(", ");
            int id = Integer.parseInt(transactionCategoryStr[0].split("=")[1]);
            int userId = Integer.parseInt(transactionCategoryStr[1].split("=")[1]);
            boolean type = transactionCategoryStr[2].split("=")[1].equals("true");
            String category = transactionCategoryStr[3].split("=")[1];
            transactionCategories.add(new TransactionCategory(id, userId, type, category));
        }

        return transactionCategories;
    }

    public static TransactionCategory convertFromString(String response) {
        if (!response.startsWith("TransactionCategory")) {
            return null;
        }

        if (!Character.toString(response.charAt(22)).equals("{")) {
            return null;
        }

        response = response.replace("TransactionCategoryDTO", "").replace("{", "")
                .replace("}", "").replace("'", "");

        String[] info = response.split(", ");
        int id = Integer.parseInt(info[0].split("=")[1]);
        int userId = Integer.parseInt(info[1].split("=")[1]);
        boolean type = info[2].split("=")[1].equals("true");
        String category = info[3].split("=")[1];

        return new TransactionCategory(id, userId, type, category);
    }
}
