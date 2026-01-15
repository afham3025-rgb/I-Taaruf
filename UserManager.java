package com.mycompany.project;

import java.io.*;
import java.util.ArrayList;

public class UserManager {

    private static final String USER_FILE = "users.txt";
    private static ArrayList<User> users = new ArrayList<>();

    static {
        loadUsersFromFile();
    }

    public static boolean register(String username, String password, String role) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false; // username exists
            }
        }

        User newUser = new User(username, password, role);
        users.add(newUser);
        saveUserToFile(newUser);

        AuditLogger.log("User registered: " + username);
        return true;
    }

    public static boolean login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) &&
                u.getPassword().equals(password)) {

                AuditLogger.log("User logged in: " + username);
                return true;
            }
        }
        return false;
    }

    private static void saveUserToFile(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            bw.write(user.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadUsersFromFile() {
        File file = new File(USER_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                users.add(new User(data[0], data[1], data[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
