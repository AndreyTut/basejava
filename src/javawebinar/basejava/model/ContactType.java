package javawebinar.basejava.model;

public enum ContactType {
    TELEPHONE("Тел."),
    SKYPE("Skype"),
    EMAIL("E-mail"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("Stackoverflow"),
    HOMEPAGE("Site");

    private String title;

    ContactType(String name) {
        this.title = name;
    }

    public String getTitle() {
        return title;
    }
}
