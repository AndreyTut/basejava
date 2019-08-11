package javawebinar.basejava.storage;

import javawebinar.basejava.exception.ExistStorageException;
import javawebinar.basejava.exception.NotExistStorageException;
import javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            rewriteResume(index, resume);
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        writeResume(index, resume);
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return readResume(index);
    }


    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            removeResume(index);
        }
    }

    protected abstract Resume readResume(int index);

    protected abstract void removeResume(int index);

    protected abstract void writeResume(int index, Resume resume);

    protected abstract void rewriteResume(int index, Resume resume);

    protected abstract int getIndex(String uuid);
}
