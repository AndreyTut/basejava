package javawebinar.basejava.storage;

import javawebinar.basejava.model.Resume;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MapStorageTest extends AbstractArrayStorageTest{

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void getAll() throws Exception {
        Resume[] array = storage.getAll();
        Arrays.sort(array);
        assertEquals(3, array.length);
        assertEquals(RESUME_1, array[0]);
        assertEquals(RESUME_2, array[1]);
        assertEquals(RESUME_3, array[2]);
    }

    @Override
    @Test
    @Ignore
    public void saveOverflow() throws Exception {
        super.saveOverflow();
    }
}