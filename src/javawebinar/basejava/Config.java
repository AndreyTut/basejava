package javawebinar.basejava;

import javawebinar.basejava.storage.SqlStorage;
import javawebinar.basejava.storage.Storage;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private final File PROPS = new File(getHome(), "config\\resumes.properties");
    private File storageDir;
    private Storage storage;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream inputStream = new FileInputStream(PROPS)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            storageDir = new File(properties.getProperty("storage.dir"));
            storage = new SqlStorage(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }

    private File getHome() {
        String home = System.getProperty("homeDir");
        File homeDir = new File(home != null ? home : ".");
        System.out.println(homeDir);
        if (!homeDir.isDirectory()) {
            throw new IllegalStateException(homeDir + " is not directory");
        }
        return homeDir;
    }
}
