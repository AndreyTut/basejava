package javawebinar.basejava.storage.serializer;

import javawebinar.basejava.model.*;
import javawebinar.basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
private XmlParser parser;
    public XmlStreamSerializer() {
        parser = new XmlParser(Resume.class, OrganizationSection.class,
                ListSection.class, TextSection.class, Organization.Position.class, Link.class);
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        Resume resume = parser.unmarshal(reader);
        reader.close();
        return resume;
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        parser.marshal(resume, writer);
        writer.close();
    }
}
