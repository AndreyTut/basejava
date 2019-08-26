package javawebinar.basejava.model;

import java.awt.datatransfer.StringSelection;
import java.util.*;

import static javawebinar.basejava.model.ContactType.*;
import static javawebinar.basejava.model.SectionType.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

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

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public void setPhone(String phone) {
        contacts.put(TELEPHONE, phone);
    }

    public void setSkype(String skype) {
        contacts.put(SKYPE, skype);
    }

    public void setEmail(String email) {
        contacts.put(EMAIL, email);
    }

    public void setLinkedIn(String linkedIn) {
        contacts.put(LINKEDIN, linkedIn);
    }

    public void setGitHub(String gitHub) {
        contacts.put(GITHUB, gitHub);
    }

    public void setStackOverflow(String stackOverflow) {
        contacts.put(STACKOVERFLOW, stackOverflow);
    }

    public void setHomePage(String homePage) {
        contacts.put(HOMEPAGE, homePage);
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public void setPersonal(String personal) {
        StringSection section = (StringSection) sections.computeIfAbsent(PERSONAL, k -> new StringSection());
        section.setContent(personal);
    }

    public void setObjective(String objective) {
        StringSection section = (StringSection) sections.computeIfAbsent(OBJECTIVE, k -> new StringSection());
        section.setContent(objective);
    }

    public void addAchievement(String achievement) {
        StringListSection section = (StringListSection) sections.computeIfAbsent(ACHIEVEMENT, k -> new StringListSection());
        section.setContent(achievement);
    }

    public void addQualification(String qualification) {
        StringListSection section = (StringListSection) sections.computeIfAbsent(QUALIFICATIONS, k -> new StringListSection());
        section.setContent(qualification);
    }

    public void addExperience(String organization, String position, String text, String from, String to) {
        OrganizationSection section = (OrganizationSection) sections.computeIfAbsent(EXPERIENCE, k -> new OrganizationSection());
        section.addItem(organization, position, text, from, to);
    }

    public void addEducation(String organization, String text, String from, String to) {
        OrganizationSection section = (OrganizationSection) sections.computeIfAbsent(EDUCATION, k -> new OrganizationSection());
        section.addItem(organization, text, from, to);
    }

    public String getContent() {
        StringBuilder builder = new StringBuilder(fullName);
        builder.append(System.lineSeparator());

        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            builder.append(entry.getKey().getTitle())
                    .append(": ")
                    .append(entry.getValue())
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
    public String toString() {
        return uuid + '(' + fullName + ')';
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
}