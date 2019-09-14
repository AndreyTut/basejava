package javawebinar.basejava.storage;

import javawebinar.basejava.exception.StorageException;
import javawebinar.basejava.model.Resume;
import org.junit.Test;

public abstract class AbstractObjectStreamStrategyTest extends AbstractStorageTest {
    protected AbstractObjectStreamStrategyTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void nullStrategy() {
        storage = new SerializeStorage(STORAGE_DIR.getAbsolutePath());
        storage.save(new Resume("test"));
    }
}
