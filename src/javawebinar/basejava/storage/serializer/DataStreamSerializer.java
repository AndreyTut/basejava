package javawebinar.basejava.storage.serializer;

import javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            writeCollection(resume.getContacts().entrySet(), dataOutputStream, entry -> {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sections = resume.getSections();

            writeCollection(sections.entrySet(), dataOutputStream, entry -> {
                SectionType key = entry.getKey();
                dataOutputStream.writeUTF(key.name());
                switch (key) {
                    case PERSONAL:
                    case OBJECTIVE:
                        writeTextSection(dataOutputStream, sections, key);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeListSection(dataOutputStream, sections, key);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeOrganizationSection(dataOutputStream, sections, key);
                }
            });
        }
    }

    private void writeTextSection(DataOutputStream dataOutputStream, Map<SectionType, AbstractSection> sections, SectionType type) throws IOException {
        TextSection textSection = (TextSection) sections.get(type);
        dataOutputStream.writeUTF(textSection.getContent());
    }

    private void writeListSection(DataOutputStream dataOutputStream, Map<SectionType, AbstractSection> sections, SectionType type) throws IOException {
        ListSection listSection = (ListSection) sections.get(type);
        writeCollection(listSection.getItems(), dataOutputStream, dataOutputStream::writeUTF);
    }

    private void writeOrganizationSection(DataOutputStream dataOutputStream, Map<SectionType, AbstractSection> sections, SectionType type) throws IOException {
        OrganizationSection organizationSection = (OrganizationSection) sections.get(type);
        writeCollection(organizationSection.getOrganizations(), dataOutputStream, o -> {
            Link homePage = o.getHomePage();
            dataOutputStream.writeUTF(homePage.getName());
            String url = homePage.getUrl();
            if (url != null) {
                dataOutputStream.writeUTF(url);
            } else {
                dataOutputStream.writeUTF("");
            }

            writeCollection(o.getPositions(), dataOutputStream, p -> {
                dataOutputStream.writeUTF(p.getStartDate().toString());
                dataOutputStream.writeUTF(p.getEndDate().toString());
                dataOutputStream.writeUTF(p.getTitle());
                String description = p.getDescription();
                if (description != null) {
                    dataOutputStream.writeUTF(description);
                } else {
                    dataOutputStream.writeUTF("");
                }
            });
        });
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            resume.setContacts(readMap(dataInputStream, ContactType.class, type -> dataInputStream.readUTF()));
            resume.setSections(readMap(dataInputStream, SectionType.class, type -> {
                switch (type) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        return new TextSection(dataInputStream.readUTF());
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        return new ListSection(readList(dataInputStream, t -> dataInputStream.readUTF()));
                    case "EXPERIENCE":
                    case "EDUCATION":
                        return new OrganizationSection(readOrganizationSection(dataInputStream));
                }
                return null;
            }));
            return resume;
        }
    }

    private List<Organization> readOrganizationSection(DataInputStream dataInputStream) throws IOException {
        return readList(dataInputStream, t -> {
            String pageName = dataInputStream.readUTF();
            String url = dataInputStream.readUTF();

            if (url.equals("")) {
                url = null;
            }
            return new Organization(new Link(pageName, url), readList(dataInputStream, n->{
                LocalDate startDate = LocalDate.parse(dataInputStream.readUTF());
                LocalDate endDate = LocalDate.parse(dataInputStream.readUTF());
                String title = dataInputStream.readUTF();
                String description = dataInputStream.readUTF();
                if (description.equals("")) {
                    description = null;
                }
                return new Organization.Position(startDate, endDate, title, description);
            }));
        });
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream dataOutputStream, DataWriter<T> writer) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    private <K extends Enum<K>, V> Map<K, V> readMap(DataInputStream dataInputStream, Class<K> clazz, DataReader<V> dataReader) throws IOException {
        Map<K, V> map = new HashMap<>();
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            String name = dataInputStream.readUTF();
            map.put(K.valueOf(clazz, name), dataReader.read(name));
        }
        return map;
    }

    private <T> List<T> readList(DataInputStream dataInputStream, DataReader<T> dataReader) throws IOException {
        int listSize = dataInputStream.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(dataReader.read(""));
        }
        return list;
    }
}

