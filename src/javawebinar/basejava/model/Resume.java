package javawebinar.basejava.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static javawebinar.basejava.model.SectionType.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private final Map<ContactType, AbstractSection> contacts = new EnumMap<>(ContactType.class);

    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public <T> void addData(Enum type, T content) {
        AbstractSection section;
        if (type instanceof SectionType) {
            section = sections.computeIfAbsent((SectionType) type, this::createSection);
        } else {
            section = contacts.computeIfAbsent((ContactType) type, this::createSection);
        }
        section.addContent(content);
    }

    public AbstractSection getData(Enum type) {
        AbstractSection as = contacts.get(type);
        return as != null ? as : sections.get(type);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(fullName);
        builder.append(System.lineSeparator());

        for (Map.Entry<ContactType, AbstractSection> entry : contacts.entrySet()) {
            builder.append(entry.getKey().getTitle())
                    .append(": ")
                    .append(entry.getValue().toString())
                    .append(System.lineSeparator());
        }

        for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
            builder.append(entry.getKey().getTitle())
                    .append(System.lineSeparator())
                    .append(entry.getValue().toString())
                    .append(System.lineSeparator());
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }

    private AbstractSection createSection(Enum type) {
        if (type == ACHIEVEMENT || type == QUALIFICATIONS) {
            return new StringListSection();
        } else if (type == EXPERIENCE || type == EDUCATION) {
            return new OrganizationSection();
        }
        return new StringSection();
    }
}