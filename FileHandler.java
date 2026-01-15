package com.mycompany.project;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    private String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + filePath);
        }
    }

    public void writeLine(String data) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath, true))) {

            writer.write(data);
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error writing to file: " + filePath);
        }
    }

    public ArrayList<String> readAllLines() {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(filePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath);
        }

        return lines;
    }

    public void clearFile() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath))) {

            writer.write("");

        } catch (IOException e) {
            System.out.println("Error clearing file: " + filePath);
        }
    }

    public boolean fileExists() {
        return new File(filePath).exists();
    }
}
