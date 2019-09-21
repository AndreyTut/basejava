package javawebinar.basejava.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.Writer;

public class XmlParser {
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public XmlParser(Class... classesToBeBoumd) {
        try {
            JAXBContext context = JAXBContext.newInstance(classesToBeBoumd);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T> T unmarshal(Reader reader) {
        try {
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    public void marshal(Object object, Writer writer) {
        try {
            marshaller.marshal(object, writer);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}
