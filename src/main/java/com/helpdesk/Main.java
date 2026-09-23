package com.helpdesk;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Help Desk Ticketing System");
        String url = "jdbc:sqlserver://YODA;instanceName=SQLEXPRESS;databaseName=HelpDeskDB;trustServerCertificate=true";
        String username = System.getenv("HELPDESK_DB_USER");
        String password = System.getenv("HELPDESK_DB_PASSWORD");

        try (
            Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to database successfully");
            String sql = "SELECT * FROM tickets WHERE status = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "OPEN");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int ticketId = resultSet.getInt("ticket_id");
                String title = resultSet.getString("title");
                String priority = resultSet.getString("priority");
                String status = resultSet.getString("status");
                System.out.println("ID: " + ticketId + ", title " + title + ", priority " + priority +  ", status " + status);
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            System.out.println(e.getMessage());
        }
      }
}
