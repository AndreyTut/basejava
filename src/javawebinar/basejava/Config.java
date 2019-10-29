package javawebinar.basejava;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private Properties properties = new Properties();
    protected final File PROPS = new File(".//config/resumes.properties");
    private File storageDir;
    private String dbUser;
    private String dbPsw;
    private String dbUrl;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream inputStream = new FileInputStream(PROPS)) {
            properties.load(inputStream);
            storageDir = new File(properties.getProperty("storage.dir"));
            dbUser = properties.getProperty("db.user");
            dbPsw = properties.getProperty("db.password");
            dbUrl = properties.getProperty("db.url");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPsw() {
        return dbPsw;
    }

    public String getDbUrl() {
        return dbUrl;
    }
}
