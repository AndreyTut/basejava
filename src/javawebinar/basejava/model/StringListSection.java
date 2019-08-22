package javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class StringListSection extends Section {

    private List<String> list = new ArrayList<>();

   @Override
    public String getContent() {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(System.lineSeparator())
            .append(s);
        }
        return builder.toString();
    }

    @Override
    public void setContent(String...content) {
        list.add(content[0]);
    }

    @Override
    public void clearContent() {
        list.clear();
    }
}
