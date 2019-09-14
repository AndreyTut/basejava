package javawebinar.basejava.storage;

import static javawebinar.basejava.storage.SerializeStrategy.*;

public class ObjectStreamStrategyPathStorageTest extends AbstractObjectStreamStrategyTest {
    static SerializeStorage stor = new SerializeStorage(STORAGE_DIR.getAbsolutePath());

    public ObjectStreamStrategyPathStorageTest() {
        super(stor);
        stor.setStorage(PATH_STORAGE);
    }
}
