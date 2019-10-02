package javawebinar.basejava.storage.serializer;

import java.io.IOException;

public interface DataReader<T> {
    T read() throws IOException;
}
