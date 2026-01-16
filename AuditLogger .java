package com.mycompany.oop;
import java.io.*;
import java.time.LocalDateTime;

public class AuditLogger {

    private static final String LOG_FILE = "audit.log";

    public static void log(String action) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            bw.write("[" + LocalDateTime.now() + "] " + action);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readLogs() {
        StringBuilder logs = new StringBuilder();
        File file = new File(LOG_FILE);
        if (!file.exists()) return "No logs available.";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                logs.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logs.toString();
    }
}
