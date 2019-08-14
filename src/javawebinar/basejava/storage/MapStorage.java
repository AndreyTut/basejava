package javawebinar.basejava.storage;

import javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume readResume(Object index) {
        return storage.get(index);
    }

    @Override
    protected void removeResume(Object index) {
        storage.remove(index);
    }

    @Override
    protected void writeResume(Object index, Resume resume) {
        storage.put((String) index, resume);
    }

    @Override
    protected void rewriteResume(Object index, Resume resume) {
        storage.put((String) index, resume);
    }

    @Override
    protected Object getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected boolean presentInStorage(Object index) {
        return storage.containsKey(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override


    public int size() {
        return storage.size();
    }
}

