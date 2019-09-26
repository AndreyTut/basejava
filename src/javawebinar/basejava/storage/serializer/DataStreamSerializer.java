package javawebinar.basejava.storage.serializer;

import javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javawebinar.basejava.model.SectionType.*;

public class DataStreamSerializer implements StreamSerializer {

    private static final String NULL_HOLDER = "It means that argument is NUll w4t4575 ugf689^&$^$&%&$%$%^";

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dataOutputStream.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = resume.getSections();

            writeTextSection(dataOutputStream, sections, PERSONAL);

            writeTextSection(dataOutputStream, sections, OBJECTIVE);

            writeListSection(dataOutputStream, sections, ACHIEVEMENT);

            writeListSection(dataOutputStream, sections, QUALIFICATIONS);

            writeOrganizationSection(dataOutputStream, sections, EXPERIENCE);

            writeOrganizationSection(dataOutputStream, sections, EDUCATION);
        }
    }

    private void writeTextSection(DataOutputStream dataOutputStream, Map<SectionType, AbstractSection> sections, SectionType type) throws IOException {
        TextSection textSection = (TextSection) sections.get(type);
        if (textSection != null) {
            dataOutputStream.writeInt(1);
            dataOutputStream.writeUTF(textSection.getContent());
        } else {
            dataOutputStream.writeInt(-1);
        }
    }

    private void writeListSection(DataOutputStream dataOutputStream, Map<SectionType, AbstractSection> sections, SectionType type) throws IOException {
        ListSection listSection = (ListSection) sections.get(type);
        if (listSection != null) {
            dataOutputStream.writeInt(1);
            List<String> items = listSection.getItems();
            dataOutputStream.writeInt(items.size());
            for (String item : items) {
                dataOutputStream.writeUTF(item);
            }
        } else {
            dataOutputStream.writeInt(-1);
        }
    }

    private void writeOrganizationSection(DataOutputStream dataOutputStream, Map<SectionType, AbstractSection> sections, SectionType type) throws IOException {
        OrganizationSection organizationSection = (OrganizationSection) sections.get(type);
        if (organizationSection != null) {
            dataOutputStream.writeInt(1);
            List<Organization> organizations = organizationSection.getOrganizations();
            dataOutputStream.writeInt(organizations.size());

            for (Organization organization : organizations) {
                Link homePage = organization.getHomePage();
                dataOutputStream.writeUTF(homePage.getName());
                String url = homePage.getUrl();
                if (url != null) {
                    dataOutputStream.writeUTF(url);
                } else {
                    dataOutputStream.writeUTF(NULL_HOLDER);
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
                        dataOutputStream.writeUTF(NULL_HOLDER);
                    }
                }
            }
        } else {
            dataOutputStream.writeInt(-1);
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

            if (dataInputStream.readInt() == 1) {
                resume.addSection(PERSONAL, new TextSection(dataInputStream.readUTF()));
            }

            if (dataInputStream.readInt() == 1) {
                resume.addSection(OBJECTIVE, new TextSection(dataInputStream.readUTF()));
            }

            if (dataInputStream.readInt() == 1) {
                resume.addSection(ACHIEVEMENT, new ListSection(readListSection(dataInputStream, ACHIEVEMENT)));
            }

            if (dataInputStream.readInt() == 1) {
                resume.addSection(QUALIFICATIONS, new ListSection(readListSection(dataInputStream, ACHIEVEMENT)));
            }

            if (dataInputStream.readInt() == 1) {
                resume.addSection(EXPERIENCE, new OrganizationSection(readOrganizationSection(dataInputStream, EXPERIENCE)));
            }

            if (dataInputStream.readInt() == 1) {
                resume.addSection(EDUCATION, new OrganizationSection(readOrganizationSection(dataInputStream, EDUCATION)));
            }
            return resume;
        }
    }

    private List<String> readListSection(DataInputStream dataInputStream, SectionType type) throws IOException {
        int listSize = dataInputStream.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(dataInputStream.readUTF());
        }
        return list;
    }

    private List<Organization> readOrganizationSection(DataInputStream dataInputStream, SectionType type) throws IOException {
        int ogrsCount = dataInputStream.readInt();
        List<Organization> organizations = new ArrayList<>();

        for (int i = 0; i < ogrsCount; i++) {
            String pageName = dataInputStream.readUTF();
            String url = dataInputStream.readUTF();

            if (url.equals(NULL_HOLDER)) {
                url = null;
            }

            int positionsCount = dataInputStream.readInt();
            List<Organization.Position> positions = new ArrayList<>();
            for (int j = 0; j < positionsCount; j++) {
                LocalDate startDate = LocalDate.parse(dataInputStream.readUTF());
                LocalDate endDate = LocalDate.parse(dataInputStream.readUTF());
                String title = dataInputStream.readUTF();
                String description = dataInputStream.readUTF();
                if (description.equals(NULL_HOLDER)) {
                    description = null;
                }
                positions.add(new Organization.Position(startDate, endDate, title, description));
            }
            Organization organization = new Organization(new Link(pageName, url), positions);
            organizations.add(organization);
        }
        return organizations;
    }
}
