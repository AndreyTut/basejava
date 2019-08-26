package javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrganizationSection extends AbstractSection {

    private Set<Organization> organizations = new TreeSet<>();

    void addItem(String orgName, String text, String from, String to) {
        organizations.add(new Organization(orgName, text, from, to));
    }

    void addItem(String orgName, String position, String text, String from, String to) {
        organizations.add(new Organization(orgName, position, text, from, to));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Organization org : organizations) {
            builder.append(System.lineSeparator());
            builder.append(org);
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationSection)) return false;
        OrganizationSection section = (OrganizationSection) o;
        return Objects.equals(organizations, section.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }

    static class Organization  implements Comparable<Organization>{
        private String orgName;
        private String position;
        private String text;
        private LocalDate from;
        private LocalDate to;
        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        private DateTimeFormatter backFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Organization(String orgName, String text, String from, String to) {
            this(orgName, null, text, from, to);
        }

        Organization(String orgName, String position, String text, String from, String to) {
            this.orgName = orgName;
            this.position = position;
            this.text = text;
            this.from = LocalDate.parse(from, backFormatter);
            if (to != null) {
                this.to = LocalDate.parse(to, backFormatter);
            }
        }

        private String getFromTo() {
            if (to == null) {
                return from.format(formatter) + " - Сейчас";
            }
            return from.format(formatter) + " - " + to.format(formatter);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder(orgName)
                    .append(System.lineSeparator())
                    .append(getFromTo())
                    .append("\t");
            if (position != null) {
                builder.append(position)
                        .append(System.lineSeparator());
            }
            builder.append(text);
            return builder.toString();
        }

        @Override
        public int compareTo(Organization o) {
            return o.from.compareTo(this.from);
        }
    }
}
