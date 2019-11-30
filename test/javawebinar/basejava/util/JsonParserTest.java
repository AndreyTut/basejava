package javawebinar.basejava.util;

import javawebinar.basejava.ResumeTestData;
import javawebinar.basejava.model.AbstractSection;
import javawebinar.basejava.model.Resume;
import javawebinar.basejava.model.TextSection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonParserTest {

    @Test
    public void testResume() throws Exception{
        Resume resume = ResumeTestData.createFilledResume("test_uuid", "Test_Name");
        String json = JsonParser.write(resume);
        System.out.println(json);
        Resume fromResume = JsonParser.read(json, Resume.class);
        assertEquals(resume, fromResume);
    }

    @Test
    public void write() {
        AbstractSection section = new TextSection("textSection");
        String json = JsonParser.write(section, AbstractSection.class);
        System.out.println(json);
        AbstractSection fromSection = JsonParser.read(json, AbstractSection.class);
        assertEquals(section, fromSection);
    }
}