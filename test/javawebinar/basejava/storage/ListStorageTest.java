package javawebinar.basejava.storage;

import org.junit.Ignore;
import org.junit.Test;

public class ListStorageTest extends AbstractArrayStorageTest{

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    @Test
    @Ignore
    public void saveOverflow() throws Exception {
        super.saveOverflow();
    }
}