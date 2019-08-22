package javawebinar.basejava.model;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends Section {

    private List<Organization> organizations = new ArrayList<>();

    @Override
    void setContent(String... content) {
        if (content.length == 5) {
            organizations.add(new Organization(content[0], content[1], content[2], content[3], content[4]));
        } else if (content.length == 4) {
            organizations.add(new Organization(content[0], content[1], content[2], content[3]));
        } else throw new InvalidParameterException("Wrong parameters number");
    }

    @Override
    public String getContent() {
        StringBuilder builder = new StringBuilder();
        for (Organization org : organizations) {
            builder.append(System.lineSeparator());
            builder.append(org);
        }
        return builder.toString();
    }

    @Override
    public void clearContent() {
        organizations.clear();
    }

    static class Organization {
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
    }
}
