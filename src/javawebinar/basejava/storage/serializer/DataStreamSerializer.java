package javawebinar.basejava.storage.serializer;

import javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javawebinar.basejava.model.SectionType.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            writeMap(resume.getContacts(), dataOutputStream, (t, s) -> dataOutputStream.writeUTF(s));

            Map<SectionType, AbstractSection> sections = resume.getSections();

            writeMap(sections, dataOutputStream, (t, s) -> {
                switch ((SectionType) t) {
                    case PERSONAL:
                    case OBJECTIVE:
                        writeTextSection(dataOutputStream, sections, (SectionType) t);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeListSection(dataOutputStream, sections, (SectionType) t);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeOrganizationSection(dataOutputStream, sections, (SectionType) t);
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
        List<String> items = listSection.getItems();
        dataOutputStream.writeInt(items.size());
        for (String item : items) {
            dataOutputStream.writeUTF(item);
        }
    }

    private void writeOrganizationSection(DataOutputStream dataOutputStream, Map<SectionType, AbstractSection> sections, SectionType type) throws IOException {
        OrganizationSection organizationSection = (OrganizationSection) sections.get(type);
        List<Organization> organizations = organizationSection.getOrganizations();
        dataOutputStream.writeInt(organizations.size());

        for (Organization organization : organizations) {
            Link homePage = organization.getHomePage();
            dataOutputStream.writeUTF(homePage.getName());
            String url = homePage.getUrl();
            if (url != null) {
                dataOutputStream.writeUTF(url);
            } else {
                dataOutputStream.writeUTF("");
            }

            List<Organization.Position> positions = organization.getPositions();
            dataOutputStream.writeInt(positions.size());

            for (Organization.Position position : positions) {
                dataOutputStream.writeUTF(position.getStartDate().toString());
                dataOutputStream.writeUTF(position.getEndDate().toString());
                dataOutputStream.writeUTF(position.getTitle());
                String description = position.getDescription();
                if (description != null) {
                    dataOutputStream.writeUTF(description);
                } else {
                    dataOutputStream.writeUTF("");
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dataInputStream.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }

            size = dataInputStream.readInt();

            for (int i = 0; i < size; i++) {
                String type = dataInputStream.readUTF();

                switch (type) {
                    case "PERSONAL":
                        resume.addSection(PERSONAL, new TextSection(dataInputStream.readUTF()));
                        break;
                    case "OBJECTIVE":
                        resume.addSection(OBJECTIVE, new TextSection(dataInputStream.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                        resume.addSection(ACHIEVEMENT, new ListSection(readListSection(dataInputStream)));
                        break;
                    case "QUALIFICATIONS":
                        resume.addSection(QUALIFICATIONS, new ListSection(readListSection(dataInputStream)));
                        break;
                    case "EXPERIENCE":
                        resume.addSection(EXPERIENCE, new OrganizationSection(readOrganizationSection(dataInputStream)));
                        break;
                    case "EDUCATION":
                        resume.addSection(EDUCATION, new OrganizationSection(readOrganizationSection(dataInputStream)));
                        break;
                }
            }
            return resume;
        }
    }

    private List<String> readListSection(DataInputStream dataInputStream) throws IOException {
        int listSize = dataInputStream.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(dataInputStream.readUTF());
        }
        return list;
    }

    private List<Organization> readOrganizationSection(DataInputStream dataInputStream) throws IOException {
        int ogrsCount = dataInputStream.readInt();
        List<Organization> organizations = new ArrayList<>();

        for (int i = 0; i < ogrsCount; i++) {
            String pageName = dataInputStream.readUTF();
            String url = dataInputStream.readUTF();

            if (url.equals("")) {
                url = null;
            }

            int positionsCount = dataInputStream.readInt();
            List<Organization.Position> positions = new ArrayList<>();
            for (int j = 0; j < positionsCount; j++) {
                LocalDate startDate = LocalDate.parse(dataInputStream.readUTF());
                LocalDate endDate = LocalDate.parse(dataInputStream.readUTF());
                String title = dataInputStream.readUTF();
                String description = dataInputStream.readUTF();
                if (description.equals("")) {
                    description = null;
                }
                positions.add(new Organization.Position(startDate, endDate, title, description));
            }
            Organization organization = new Organization(new Link(pageName, url), positions);
            organizations.add(organization);
        }
        return organizations;
    }

    private <T> void writeMap(Map<? extends Enum, T> map, DataOutputStream dataOutputStream, MapWriter<T> writer) throws IOException {
        dataOutputStream.writeInt(map.size());
        for (Map.Entry<? extends Enum, T> entry : map.entrySet()) {
            dataOutputStream.writeUTF(entry.getKey().name());
            writer.write(entry.getKey(), entry.getValue());
        }
    }
}
