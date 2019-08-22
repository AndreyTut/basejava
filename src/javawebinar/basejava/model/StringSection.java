package javawebinar.basejava.model;

public class StringSection extends Section {

    private String content;

    @Override
    public String getContent() {
        return content;
    }

   @Override
    public void setContent(String...content) {
        this.content = content[0];
    }

    @Override
    void clearContent() {
        content = "";
    }
}
