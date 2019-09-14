package javawebinar.basejava.storage;


public class ObjectStreamStrategyFileStorageTest extends AbstractObjectStreamStrategyTest {
    static SerializeStorage stor = new SerializeStorage(STORAGE_DIR.getAbsolutePath());

    public ObjectStreamStrategyFileStorageTest() {
        super(stor);
        stor.setStorage(SerializeStrategy.FILE_STORAGE);
    }
}