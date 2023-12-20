package ru.mai.information_system.dto;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;

    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static User convertFromString(String response) {
        if (!response.startsWith("User")) {
            return null;
        }

        if (!Character.toString(response.charAt(4)).equals("{")) {
            return null;
        }

        String strArray = response.substring(5, response.length() - 1);
        String[] infoAboutUser = strArray.split(", ");
        String[] userStr = new String[infoAboutUser.length];
        for (int i = 0; i < infoAboutUser.length; i++) {
            userStr[i] = infoAboutUser[i].split("=")[1].replace("'", "");
        }

        int id = Integer.parseInt(userStr[0]);
        String name = userStr[1];
        String email = userStr[2];
        String password = userStr[3];

        return new User(id, name, email, password);
    }
}
