package com.mycompany.oop;

import java.io.*;
import java.util.ArrayList;

public class UserManager {
    private static final String USER_FILE = "users.txt";
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;

    static { loadUsersFromFile(); }

    // Inside UserManager.java

public static boolean register(String username, String password, String role, String guardian, String age) {
    System.out.println("UserManager: Attempting to register " + username);
    
    try {
        // Check if user already exists
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                System.out.println("UserManager: Username already exists!");
                return false;
            }
        }

        User newUser;
        if ("Guardian".equalsIgnoreCase(role)) {
            newUser = new Guardian(username, password);
        } else {
            newUser = new Ward(username, password, guardian);
            // Pass 'username' directly here!
            saveProfile(username, "New member", "Not specified", age); 
        }

        users.add(newUser);
        saveUserToFile(newUser);
        System.out.println("UserManager: Registration successful!");
        return true;

    } catch (Exception e) {
        System.out.println("UserManager Error: " + e.getMessage());
        e.printStackTrace(); // This is your best friend for debugging!
        return false;
    }
}

    public static boolean login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                currentUser = u;
                return true;
            }
        }
        return false;
    }

    public static User getCurrentUser() { return currentUser; }

    public static boolean isGuardian() {
        return currentUser instanceof Guardian;
    }
    
    // Add this inside UserManager.java
    public static ArrayList<User> getAllUsers() {
        return users;
    }

    private static void saveUserToFile(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            bw.write(user.toString());
            bw.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private static void loadUsersFromFile() {
        users.clear();
        File file = new File(USER_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    users.add(new Ward(data[0], data[1], data[3]));
                } else if (data.length == 3) {
                    if ("Guardian".equalsIgnoreCase(data[2])) {
                        users.add(new Guardian(data[0], data[1]));
                    } else {
                        users.add(new User(data[0], data[1], data[2]));
                    }
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    // Inside UserManager.java
private static final String REQUEST_FILE = "requests.txt";

public static void sendMatchRequest(String receiver) {
    String sender = currentUser.getUsername();
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(REQUEST_FILE, true))) {
        bw.write(sender + "," + receiver + ",Pending");
        bw.newLine();
        AuditLogger.log("Match request sent from " + sender + " to " + receiver);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// Method to get requests for a specific user (Receiver)
public static ArrayList<String[]> getMyRequests() {
    ArrayList<String[]> myRequests = new ArrayList<>();
    File file = new File(REQUEST_FILE);
    if (!file.exists()) return myRequests;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            // If the current user is the receiver OR the guardian of the receiver
            if (data[1].equals(currentUser.getUsername())) {
                myRequests.add(data);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return myRequests;
}

// Update the getMyRequests method in UserManager.java to include this:
public static ArrayList<String[]> getRequestsForGuardian() {
    ArrayList<String[]> wardRequests = new ArrayList<>();
    if (!(currentUser instanceof Guardian)) return wardRequests;

    // We need to find all Wards linked to this Guardian
    for (User u : users) {
        if (u instanceof Ward) {
            Ward w = (Ward) u;
            if (w.getGuardianName().equals(currentUser.getUsername())) {
                // Now find requests where this Ward is the receiver
                wardRequests.addAll(getRequestsByUsername(w.getUsername()));
            }
        }
    }
    return wardRequests;
}

private static ArrayList<String[]> getRequestsByUsername(String username) {
    ArrayList<String[]> found = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(REQUEST_FILE))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data[1].equals(username)) {
                found.add(data);
            }
        }
    } catch (IOException e) { }
    return found;
}

// Add these to UserManager.java
private static final String PROFILE_FILE = "profiles.txt";

// Update this in UserManager.java
public static void saveProfile(String username, String bio, String interests, String age) {
    // REMOVE the line that says String username = currentUser.getUsername();
    // because we are now passing 'username' as a parameter!

    ArrayList<String> lines = new ArrayList<>();
    boolean found = false;

    File file = new File(PROFILE_FILE);
    if (file.exists()) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(username + "|")) {
                    lines.add(username + "|" + bio + "|" + interests + "|" + age);
                    found = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    if (!found) {
        lines.add(username + "|" + bio + "|" + interests + "|" + age);
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(PROFILE_FILE))) {
        for (String l : lines) {
            bw.write(l);
            bw.newLine();
        }
    } catch (IOException e) { e.printStackTrace(); }
}
public static String[] loadProfile() {
    File file = new File(PROFILE_FILE);
    if (!file.exists()) return null;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split("\\|"); // Use | to avoid confusion with commas in bio
            if (data[0].equals(currentUser.getUsername())) {
                return data; // returns [username, bio, interests, age]
            }
        }
    } catch (IOException e) { e.printStackTrace(); }
    return null;
}
}