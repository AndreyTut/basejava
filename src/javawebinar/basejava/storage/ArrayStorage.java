package javawebinar.basejava.storage;

import javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void save(Resume r) {
        if (getIndexOf(r.getUuid()) >= 0) {
            System.out.println(String.format("javawebinar.basejava.model.Resume with uuid %s already present in storage", r.getUuid()));
            return;
        }
        if (size == storage.length) {
            System.out.println("Storage full");
            return;
        }
        storage[size] = r;
        size++;
    }

    public Resume get(String uuid) {
        int index = getIndexOf(uuid);
        if (index < 0) {
            System.out.println(String.format("There isn't resume with uuid %s in storage", uuid));
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndexOf(uuid);
        if (index < 0) {
            System.out.println(String.format("There isn't resume with uuid %s in storage", uuid));
            return;
        }
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
    }

    public void update(Resume resume) {
        int index = getIndexOf(resume.getUuid());
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

    private int getIndexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
