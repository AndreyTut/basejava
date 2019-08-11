package javawebinar.basejava.storage;

import javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
       return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume readResume(int index) {
        return storage.get(index);
    }

    @Override
    protected void removeResume(int index) {
        storage.remove(index);
    }

    @Override
    protected void writeResume(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void rewriteResume(int index, Resume resume) {
        storage.remove(index);
        storage.add(resume);
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
