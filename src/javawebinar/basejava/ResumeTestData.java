package javawebinar.basejava;

import javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static javawebinar.basejava.model.ContactType.*;
import static javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        resume.addToContacts(TELEPHONE, " +7(921) 855-0482");
        resume.addToContacts(SKYPE, "grigory.kislin");
        resume.addToContacts(EMAIL, "gkislin@yandex.ru");
        resume.addToContacts(LINKEDIN, "https://www.linkedin.com/in/gkislin/");
        resume.addToContacts(GITHUB, "https://github.com/gkislin");
        resume.addToContacts(STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        resume.addToContacts(HOMEPAGE, "http://gkislin.ru/");

        resume.addToSections(OBJECTIVE, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addToSections(PERSONAL, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addToSections(QUALIFICATIONS, "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        resume.addToSections(QUALIFICATIONS, "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.addToSections(QUALIFICATIONS, "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,\n" +
                "MySQL, SQLite, MS SQL, HSQLDB");

        resume.addToSections(ACHIEVEMENT, "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        resume.addToSections(ACHIEVEMENT, "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.addToSections(ACHIEVEMENT, "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");

        resume.addToSections(EXPERIENCE, Arrays.asList("Java Online Projects", "Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.",
                "01/10/2013", null));
        resume.addToSections(EXPERIENCE, Arrays.asList("Wrike", "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                "01/10/2014", "01/01/2016"));
        resume.addToSections(EXPERIENCE, Arrays.asList("RIT Center", "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
                "01/04/2012", "01/10/2014"));
        resume.addToSections(EDUCATION, Arrays.asList("Alcatel", "6 месяцев обучения цифровым телефонным сетям (Москва)",
                "01/09/1997", "01/03/1998"));
        resume.addToSections(EDUCATION, Arrays.asList("Coursera", "\t\"Functional Programming Principles in Scala\" by Martin Odersky",
                "01/03/2013", "01/05/2013"));
        resume.addToSections(EDUCATION, Arrays.asList("Заочная физико-техническая школа при МФТИ", "Закончил  с отличием",
                "01/09/1984", "01/06/1987"));
        System.out.println(resume.toString());
    }
}
