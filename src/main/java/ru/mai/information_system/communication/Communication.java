package ru.mai.information_system.communication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Communication {

    public String sendGetRequest(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        return sendResponse(connection);
    }

    public String sendPostRequest(String url, String user) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        writeData(connection, user);

        return sendResponse(connection);
    }

    public String sendPutRequest(String url, String user) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        writeData(connection, user);

        return sendResponse(connection);
    }

    public String sendDeleteRequest(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("DELETE");

        return sendResponse(connection);
    }

    private String sendResponse(HttpURLConnection connection) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            connection.disconnect();
        }

        return response.toString();
    }

    private void writeData(HttpURLConnection connection, String data) throws IOException {
        try (OutputStream output = connection.getOutputStream();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"))) {
            writer.write(data);
        }
    }
}