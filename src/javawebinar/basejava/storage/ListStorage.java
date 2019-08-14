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
    protected Resume readResume(Object index) {
        return storage.get((int) index);
    }

    @Override
    protected void removeResume(Object index) {
        storage.remove((int) index);
    }

    @Override
    protected void writeResume(Object index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void rewriteResume(Object index, Resume resume) {
        storage.remove((int) index);
        storage.add(resume);
    }

    @Override
    protected Object getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean presentInStorage(Object index) {
        return (int) index >= 0;
    }
}
