package javawebinar.basejava.storage;

import javawebinar.basejava.exception.StorageException;
import javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume readResume(Object index) {
        return storage[(int) index];
    }

    @Override
    protected void removeResume(Object index) {
        fillDeleted((int) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void writeResume(Object index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement(resume, (int) index);
            size++;
        }
    }

    @Override
    protected void rewriteResume(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    @Override
    protected boolean presentInStorage(Object index) {
        return (int) index >= 0;
    }

    protected abstract void fillDeleted(int index);

    protected abstract void insertElement(Resume r, int index);
}