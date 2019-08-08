package javawebinar.basejava.storage;

import javawebinar.basejava.exception.ExistStorageException;
import javawebinar.basejava.exception.NotExistStorageException;
import javawebinar.basejava.exception.StorageException;
import javawebinar.basejava.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public class AbstractArrayStorageTest {

    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());

    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_3);
        storage.update(resume);
        Assert.assertSame(resume, storage.get(UUID_3));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expected = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Assert.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void save() throws Exception {
        Resume resume = new Resume("new_uuid");
        storage.save(resume);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(resume, storage.get("new_uuid"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(UUID_3));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            int num = 4;
            while (storage.size() < STORAGE_LIMIT) {
                storage.save(new Resume("uuid" + num++));
            }
        } catch (StorageException e) {
            Assert.fail("StorageException appeared before storage was full");
        }
        storage.save(new Resume("one more"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(new Resume(UUID_3), storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}