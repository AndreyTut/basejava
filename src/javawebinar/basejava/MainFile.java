package javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

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

        //HW8.2
        File projectRoot = new File("..\\");
        getFilesNames(projectRoot);
    }

    static void getFilesNames(File inputDir) {
        for (File file : Objects.requireNonNull(inputDir.listFiles())) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                getFilesNames(file);
            } else {
                System.out.println("file: " + file.getName());
            }
        }
    }
}