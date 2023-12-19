package ru.mai.information_system.dto;

public class BankAccountTypes {

    private int id;
    private String type;

    public BankAccountTypes() {}

    public BankAccountTypes(String type) {
        this.type = type;
    }

    public BankAccountTypes(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BankAccountTypes{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
