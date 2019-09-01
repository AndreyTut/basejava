package javawebinar.basejava.model;

import java.util.List;

public class Organization {
    private final Link homePage;

    private final List<Position> positions;

    public Organization(String name, String url, List<Position> positions) {
        this.positions = positions;
        this.homePage = new Link(name, url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", positions=" + positions +
                '}';
    }
}