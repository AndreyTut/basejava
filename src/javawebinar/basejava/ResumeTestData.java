package javawebinar.basejava;

import javawebinar.basejava.model.Resume;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        resume.setPhone(" +7(921) 855-0482");
        resume.setSkype("grigory.kislin");
        resume.setEmail("gkislin@yandex.ru");
        resume.setLinkedIn("https://www.linkedin.com/in/gkislin/");
        resume.setGitHub("https://github.com/gkislin");
        resume.setStackOverflow("https://stackoverflow.com/users/548473/gkislin");
        resume.setHomePage("http://gkislin.ru/");
        resume.setObjective("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setPersonal("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addAchievement("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        resume.addAchievement("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.addAchievement("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        resume.addAchievement("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        resume.addAchievement("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        resume.addAchievement("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.addQualification("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        resume.addQualification("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.addQualification("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,\n" +
                "MySQL, SQLite, MS SQL, HSQLDB");
        resume.addQualification("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        resume.addQualification("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        resume.addQualification("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        resume.addQualification("Python: Django.");
        resume.addQualification("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        resume.addQualification("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        resume.addQualification("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        resume.addQualification("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");

        resume.addExperience("Java Online Projects", "Автор проекта",
                "Создание, организация и проведение Java онлайн проектов и стажировок.","01/10/2013", null);
        resume.addExperience("Wrike", "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                "01/10/2014", "01/01/2016");
        resume.addExperience("RIT Center", "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python.",
                "01/04/2012", "01/10/2014");
        resume.addExperience("Alcatel", "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)","01/09/1997", "01/01/2005");

        resume.addEducation("Coursera", "\"Functional Programming Principles in Scala\" by Martin Odersky", "01/03/2013","01/05/2013");
        resume.addEducation("Luxoft", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", "01/03/2011","01/04/2011");
        resume.addEducation("Siemens AG", "3 месяца обучения мобильным IN сетям (Берлин)", "01/01/2005","01/04/2005");

        System.out.println(resume.getContent());
    }
}
