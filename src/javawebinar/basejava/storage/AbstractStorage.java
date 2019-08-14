package javawebinar.basejava.storage;

import javawebinar.basejava.exception.ExistStorageException;
import javawebinar.basejava.exception.NotExistStorageException;
import javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        Object index = getIndex(resume.getUuid());
        if (!presentInStorage(index)) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            rewriteResume(index, resume);
        }
    }


    public void save(Resume resume) {
        Object index = getIndex(resume.getUuid());
        if (presentInStorage(index)) {
            throw new ExistStorageException(resume.getUuid());
        }
        writeResume(index, resume);
    }

    public Resume get(String uuid) {
        Object index = getIndex(uuid);
        if (!presentInStorage(index)) {
            throw new NotExistStorageException(uuid);
        }
        return readResume(index);
    }


    public void delete(String uuid) {
        Object index = getIndex(uuid);
        if (!presentInStorage(index)) {
            throw new NotExistStorageException(uuid);
        } else {
            removeResume(index);
        }
    }

    protected abstract Resume readResume(Object index);

    protected abstract void removeResume(Object index);

    protected abstract void writeResume(Object index, Resume resume);

    protected abstract void rewriteResume(Object index, Resume resume);

    protected abstract Object getIndex(String uuid);

    protected abstract boolean presentInStorage(Object index);
}
