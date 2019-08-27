package javawebinar.basejava.model;

import java.util.Objects;

public class StringSection extends AbstractSection<String> {

    private String content;

   @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringSection)) return false;
        StringSection section = (StringSection) o;
        return Objects.equals(content, section.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public void addContent(String content) {
        this.content = content;
    }
}
