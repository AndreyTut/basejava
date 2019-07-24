package javawebinar.basejava.storage;

import javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final static int CAPACITY = 10_000;
    private Resume[] storage = new Resume[CAPACITY];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println(String.format("Resume with uuid %s already present in storage", resume.getUuid()));
            return;
        }
        if (size == storage.length) {
            System.out.println("Storage is full");
            return;
        }
        storage[size] = resume;
        size++;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println(String.format("There isn't resume with uuid %s in storage", uuid));
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println(String.format("There isn't resume with uuid %s in storage", uuid));
            return;
        }
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        size--;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println(String.format("There isn't resume with uuid %s in storage", resume.getUuid()));
            return;
        }
        storage[index] = resume;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int getSize() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
