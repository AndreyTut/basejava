package javawebinar.basejava.storage;

import javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public interface Storage {

    Comparator<Resume> comparator = ((r1, r2) -> !r1.getFullName().equals(r2.getFullName())
            ? r1.getFullName().compareTo(r2.getFullName())
            : r1.getUuid().compareTo(r2.getUuid()));

    void clear();

    void update(Resume resume);

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();
}
