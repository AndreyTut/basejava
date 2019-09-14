package javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println((char) fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File projectRoot = new File("./src/javawebinar/basejava");
        printDirectoryDeeply(projectRoot, 0);
    }

    public static void printDirectoryDeeply(File dir, int level) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(addSpaces(level) + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(addSpaces(++level) + file.getName());
                    printDirectoryDeeply(file, ++level);
                }
            }
        }
    }

    private static String addSpaces(int number) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}