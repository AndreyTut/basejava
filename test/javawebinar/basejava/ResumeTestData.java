package javawebinar.basejava;

import javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.Arrays;

import static javawebinar.basejava.model.ContactType.*;
import static javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {


    static {
    }

    public static Resume createResume(String uuid, String name) {
        return new Resume(uuid, name);
    }

    public static Resume createFilledResume(String uuid, String name) {
        Resume resume = createResume(uuid, name);

        resume.addContact(PHONE, "+7(921) 855-0482");
        resume.addContact(SKYPE, "grigory.kislin");
        resume.addContact(MAIL, "gkislin@yandex.ru");
        resume.addContact(LINKEDIN, "https://www.linkedin.com/in/gkislin/");
        resume.addContact(GITHUB, "https://github.com/gkislin");
        resume.addContact(STATCKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        resume.addContact(HOME_PAGE, "http://gkislin.ru/");

        resume.addSection(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.addSection(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        resume.addSection(ACHIEVEMENT, new ListSection(Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.")));

        resume.addSection(QUALIFICATIONS, new ListSection(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,\n" +
                        "MySQL, SQLite, MS SQL, HSQLDB")));

        resume.addSection(EXPERIENCE, new OrganizationSection(Arrays.asList(
                new Organization("Java Online Projects", "http://javaops.ru/",
                        Arrays.asList(new Position(LocalDate.of(2013, 10, 1),
                                LocalDate.now(),
                                "Автор проекта.",
                                "Создание, организация и проведение Java онлайн проектов и стажировок."))),
                new Organization("Wrike", "https://www.wrike.com/",
                        Arrays.asList(new Position(LocalDate.of(2014, 10, 1),
                                LocalDate.of(2016, 1, 1),
                                "Старший разработчик (backend)",
                                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."))),
                new Organization("RIT Center", null,
                        Arrays.asList(new Position(LocalDate.of(2012, 4, 1),
                                LocalDate.of(2014, 10, 1),
                                "Java архитектор",
                                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python0000000")))
        )));

        resume.addSection(EDUCATION, new OrganizationSection(Arrays.asList(
                new Organization("Coursera", "https://www.coursera.org/learn/progfun1",
                        Arrays.asList(new Position(LocalDate.of(2013, 3, 1),
                                LocalDate.of(2013, 5, 1),
                                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                                null))),
                new Organization("Luxoft", "www.luxoft-training.ru",
                        Arrays.asList(new Position(LocalDate.of(2011, 3, 1),
                                LocalDate.of(2011, 4, 1),
                                "\tКурс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                                null))),
                new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                        "http://www.ifmo.ru",
                        Arrays.asList(new Position(LocalDate.of(1993, 9, 1),
                                        LocalDate.of(1996, 7, 1),
                                        "Аспирантура (программист С, С++)",
                                        null),
                                new Position(LocalDate.of(1987, 9, 1),
                                        LocalDate.of(1993, 7, 1),
                                        "Инженер (программист Fortran, C)",
                                        null)
                        )))));
        return resume;
    }
}
