package ru.mai.information_system.dto;

public class Support {

    private int id;
    private String email;
    private String message;

    public Support() {}

    public Support(String email, String message) {
        this.email = email;
        this.message = message;
    }

    public Support(int id, String email, String message) {
        this.id = id;
        this.email = email;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Support{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
