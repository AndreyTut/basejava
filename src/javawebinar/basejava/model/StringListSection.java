package javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StringListSection extends AbstractSection {

    private List<String> list = new ArrayList<>();

    public StringListSection(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s)
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringListSection)) return false;
        StringListSection section = (StringListSection) o;
        return Objects.equals(list, section.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    public void addContent(String content) {
        list.add(content);
    }
}
