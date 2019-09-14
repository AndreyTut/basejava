package javawebinar.basejava.storage;

import javawebinar.basejava.exception.StorageException;
import javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.List;

public class SerializeStorage implements Storage {
    private final String DIRECTORY;
    private Storage storage;

    public SerializeStorage(String DIRECTORY) {
        this.DIRECTORY = DIRECTORY;
    }

    public void setStorage(SerializeStrategy strategy) {
        switch (strategy) {
            case FILE_STORAGE:
                storage = new ObjectStreamStorage(new File(DIRECTORY));
                break;
            case PATH_STORAGE:
                storage = new ObjectStreamPathStorage(DIRECTORY);
                break;
        }
    }

    @Override
    public void clear() {
        checkStorage();
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        checkStorage();
        storage.update(resume);
    }

    @Override
    public void save(Resume resume) {
        checkStorage();
        storage.save(resume);
    }

    @Override
    public Resume get(String uuid) {
        checkStorage();
        return storage.get(uuid);
    }

    @Override
    public void delete(String uuid) {
        checkStorage();
        storage.delete(uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        checkStorage();
        return storage.getAllSorted();
    }

    @Override
    public int size() {
        checkStorage();
        return storage.size();
    }

    private void checkStorage(){
        if (storage==null){
            throw new StorageException("Strategy wasn't chosen", null);
        }
    }
}
