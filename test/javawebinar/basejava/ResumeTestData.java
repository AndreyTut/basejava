package javawebinar.basejava;

import javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static javawebinar.basejava.model.ContactType.*;
import static javawebinar.basejava.model.SectionType.*;
import static javawebinar.basejava.util.DateUtil.of;

public class ResumeTestData {


    static {
    }

    public static Resume createResume(String uuid, String name) {
        return new Resume(uuid, name);
    }

    public static Resume createFilledResume(String uuid, String name) {
        Resume resume = createResume(uuid, name);


        resume.addContact(MAIL, "mail1@ya.ru");
        resume.addContact(PHONE, "11111");
        resume.addSection(OBJECTIVE, new TextSection("Objective1"));
        resume.addSection(PERSONAL, new TextSection("Personal data"));
        resume.addSection(ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
        resume.addSection(QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        resume.addSection(EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        resume.addSection(EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
        resume.addSection(EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://Organization2.ru",
                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));
        return resume;
    }
}