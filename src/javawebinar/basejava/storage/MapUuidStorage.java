package javawebinar.basejava.storage;

import javawebinar.basejava.model.Resume;

public class MapUuidStorage extends AbstractMapStorage {
    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        map.put((String) searchKey, resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        map.put((String) searchKey, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get(searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove(searchKey);
    }
}