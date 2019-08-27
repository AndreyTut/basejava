package javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrganizationSection extends AbstractSection<List<String>> {

    private Set<Organization> organizations = new TreeSet<>();

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Organization org : organizations) {
            builder.append(org)
                    .append(System.lineSeparator());
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

    @Override
    public void addContent(List<String> list) {
        if (list.size() == 4) {
            organizations.add(new Organization(list.get(0), list.get(1), list.get(2), list.get(3)));
        } else if (list.size() == 5) {
            organizations.add(new Organization(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)));
        }
    }

    static class Organization implements Comparable<Organization> {
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
