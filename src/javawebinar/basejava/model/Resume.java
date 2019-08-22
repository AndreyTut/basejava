package javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static javawebinar.basejava.model.ContactType.*;
import static javawebinar.basejava.model.SectionType.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private final Map<ContactType, String> contacts = new HashMap<>();

    private final Map<SectionType, Section> sections = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;

        sections.put(PERSONAL, new StringSection());
        sections.put(OBJECTIVE, new StringSection());
        sections.put(ACHIEVEMENT, new StringListSection());
        sections.put(QUALIFICATIONS, new StringListSection());
        sections.put(EXPERIENCE, new OrganizationSection());
        sections.put(EDUCATION, new OrganizationSection());
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

    public String getPhone() {
        return contacts.get(TELEPHONE);
    }

    public String getSkype() {
        return contacts.get(SKYPE);
    }

    public String getEmail() {
        return contacts.get(EMAIL);
    }

    public String getLinkedIn() {
        return contacts.get(LINKEDIN);
    }

    public String getGitHub() {
        return contacts.get(GITHUB);
    }

    public String getStackOverflow() {
        return contacts.get(STACKOVERFLOW);
    }

    public String getHomePage() {
        return contacts.get(HOMEPAGE);
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

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public String getPersonal() {
        return sections.get(PERSONAL).getContent();
    }

    public String getObjective() {
        return sections.get(OBJECTIVE).getContent();
    }

    public String getAchievement() {
        return sections.get(ACHIEVEMENT).getContent();
    }

    public String getQualification() {
        return sections.get(QUALIFICATIONS).getContent();
    }

    public String getExperience() {
        return sections.get(EXPERIENCE).getContent();
    }

    public String getEducation() {
        return sections.get(EDUCATION).getContent();
    }

    public void setPersonal(String personal) {
        (sections.get(PERSONAL)).setContent(personal);
    }

    public void setObjective(String objective) {
        (sections.get(OBJECTIVE)).setContent(objective);
    }

    public void addAchievement(String achiev) {
        sections.get(ACHIEVEMENT).setContent(achiev);
    }

    public void addQualification(String qulif) {
        sections.get(QUALIFICATIONS).setContent(qulif);
    }

    public void addExperience(String company, String position, String duties, String from, String to) {
        sections.get(EXPERIENCE).setContent(company, duties, position, from, to);
    }

    public void addEducation(String school, String course, String from, String to) {
        sections.get(EDUCATION).setContent(school, course, from, to);
    }

    public void clearPersonal(){
        sections.get(PERSONAL).clearContent();
    }

    public void clearObjective(){
        sections.get(OBJECTIVE).clearContent();
    }

    public void clearAchievement(){
        sections.get(ACHIEVEMENT).clearContent();
    }

    public void clearQualification(){
        sections.get(QUALIFICATIONS).clearContent();
    }

    public void clearExperience(){
        sections.get(EXPERIENCE).clearContent();
    }

    public void clearEducation(){
        sections.get(EDUCATION).clearContent();
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

        for (Map.Entry<SectionType, Section> entry: sections.entrySet()) {
            builder.append(entry.getKey().getTitle())
                    .append(System.lineSeparator())
                    .append(entry.getValue().getContent())
                    .append(System.lineSeparator());
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return Objects.equals(getUuid(), resume.getUuid()) &&
                Objects.equals(getFullName(), resume.getFullName()) &&
                Objects.equals(getContacts(), resume.getContacts()) &&
                Objects.equals(getSections(), resume.getSections());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getFullName(), getContacts(), getSections());
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}