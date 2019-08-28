package javawebinar.basejava;

import javawebinar.basejava.model.OrganizationSection;
import javawebinar.basejava.model.Resume;
import javawebinar.basejava.model.StringListSection;
import javawebinar.basejava.model.StringSection;

import java.util.ArrayList;
import java.util.List;

import static javawebinar.basejava.model.ContactType.*;
import static javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        resume.addContact(TELEPHONE, " +7(921) 855-0482");
        resume.addContact(SKYPE, "grigory.kislin");
        resume.addContact(EMAIL, "gkislin@yandex.ru");
        resume.addContact(LINKEDIN, "https://www.linkedin.com/in/gkislin/");
        resume.addContact(GITHUB, "https://github.com/gkislin");
        resume.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        resume.addContact(HOMEPAGE, "http://gkislin.ru/");

        resume.addSection(OBJECTIVE, new StringSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.addSection(PERSONAL, new StringSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> stringList = new ArrayList<>();
        stringList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        stringList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        stringList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        resume.addSection(QUALIFICATIONS, new StringListSection(stringList));

        stringList = new ArrayList<>();
        stringList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        stringList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        stringList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        resume.addSection(ACHIEVEMENT, new StringListSection(stringList));

        List<List<String>> listList = new ArrayList<>();
        stringList = new ArrayList<>();
        stringList.add("Java Online Projects");
        stringList.add("Автор проекта");
        stringList.add("Создание, организация и проведение Java онлайн проектов и стажировок.");
        stringList.add("01/10/2013");
        stringList.add(null);
        listList.add(stringList);

        stringList = new ArrayList<>();
        stringList.add("Wrike");
        stringList.add("Старший разработчик (backend)");
        stringList.add("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        stringList.add("01/10/2014");
        stringList.add("01/01/2016");
        listList.add(stringList);

        stringList = new ArrayList<>();
        stringList.add("RIT Center");
        stringList.add("Java архитектор");
        stringList.add("Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        stringList.add("01/04/2012");
        stringList.add("01/10/2014");
        listList.add(stringList);

        resume.addSection(EXPERIENCE, new OrganizationSection(listList));

        listList = new ArrayList<>();
        stringList = new ArrayList<>();
        stringList.add("Alcatel");
        stringList.add("6 месяцев обучения цифровым телефонным сетям (Москва)");
        stringList.add("01/09/1997");
        stringList.add("01/03/1998");
        listList.add(stringList);
        resume.addSection(EDUCATION, new OrganizationSection(listList));

        stringList = new ArrayList<>();
        stringList.add("Coursera");
        stringList.add("\t\"Functional Programming Principles in Scala\" by Martin Odersky");
        stringList.add("01/03/2013");
        stringList.add("01/05/2013");
        listList.add(stringList);
        resume.addSection(EDUCATION, new OrganizationSection(listList));

        stringList = new ArrayList<>();
        stringList.add("Заочная физико-техническая школа при МФТИ");
        stringList.add("Закончил  с отличием");
        stringList.add("01/09/1984");
        stringList.add("01/06/1987");
        listList.add(stringList);
        resume.addSection(EDUCATION, new OrganizationSection(listList));


        System.out.println(resume.toString());

        System.out.println("*******************************");
        System.out.println(resume.getContact(HOMEPAGE));
        System.out.println(resume.getSection(PERSONAL));
        System.out.println(resume.getSection(ACHIEVEMENT));
        System.out.println(resume.getSection(EDUCATION));
        System.out.println(resume.getSection(QUALIFICATIONS));

    }
}
