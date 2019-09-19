package javawebinar.basejava.storage.serializer;

import javawebinar.basejava.exception.StorageException;
import javawebinar.basejava.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(os)) {
            outputStream.writeObject(resume);
        }
    }


    @Override
    public Resume doRead(InputStream ios) throws IOException {
        try (ObjectInputStream inputStream = new ObjectInputStream(ios)) {
            return (Resume) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Can't read resume", null, e);
        }
    }
}
