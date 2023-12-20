package ru.mai.information_system;

import com.google.gson.Gson;
import ru.mai.information_system.communication.Communication;
import ru.mai.information_system.communication.Url;
import ru.mai.information_system.dto.User;

public class Starter {

    public static void main(String[] args) {
        App.main(args);
    }

    private static void test() {
//        Communication communication = new Communication();
//        String url = Url.getUsersUrl();
//
//        try {
//            System.out.println("GET: " + communication.sendGetRequest(url));
//            User user = new User( "AndreySemenov", "email2", "password");
//            System.out.println("POST: " + communication.sendPostRequest(url, new Gson().toJson(user)));
//            System.out.println("GET: " + communication.sendGetRequest(url));
//            System.out.println("GET: " + communication.sendGetRequest(url + "/1"));
//            user.setPassword("new password");
//            user.setId(2);
//            System.out.println("PUT: " + communication.sendPutRequest(url, new Gson().toJson(user)));
//            System.out.println("GET: " + communication.sendGetRequest(url));
//            System.out.println("DELETE: " + communication.sendDeleteRequest(url + "/2"));
//            System.out.println("GET: " + communication.sendGetRequest(url));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }
}
