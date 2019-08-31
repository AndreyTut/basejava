package javawebinar.basejava.model;

import java.util.List;

public class Organization {
    private final Link homePage;

    private final List<Employing> employings;

    public Organization(String name, String url, List<Employing> employings) {
        this.employings = employings;
        this.homePage = new Link(name, url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return employings.equals(that.employings);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + employings.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", employings=" + employings +
                '}';
    }
}