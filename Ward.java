package com.mycompany.oop;
public class Ward extends User {
    private String guardianName;

    public Ward(String username, String password, String guardianName) {
        super(username, password, "Ward");
        this.guardianName = guardianName;
    }

    public String getGuardianName() { return guardianName; }

    @Override
    public String toString() {
        // Saves as: username,password,role,guardianName
        return username + "," + password + "," + role + "," + guardianName;
    }
}