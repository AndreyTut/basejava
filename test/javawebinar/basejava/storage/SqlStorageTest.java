package javawebinar.basejava.storage;

import javawebinar.basejava.Config;

public class SqlStorageTest extends AbstractStorageTest {
    private static Config config = Config.getInstance();
    public SqlStorageTest() {
        super(config.getStorage());
    }
}
