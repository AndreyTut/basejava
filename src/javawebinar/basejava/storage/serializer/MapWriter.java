package javawebinar.basejava.storage.serializer;

import java.io.DataOutputStream;
import java.io.IOException;

public interface MapWriter<T> {

    void write(Enum key, T value) throws IOException;
}
