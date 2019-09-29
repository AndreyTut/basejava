package javawebinar.basejava.storage.serializer;

import java.io.IOException;

public interface DataWriter<T> {

    void write(T t) throws IOException;
}
