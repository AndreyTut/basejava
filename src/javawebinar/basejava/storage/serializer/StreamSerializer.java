package javawebinar.basejava.storage.serializer;

import javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {
    Resume doRead(InputStream inputStream) throws IOException;
    void doWrite(Resume resume, OutputStream outputStream) throws IOException;
}
