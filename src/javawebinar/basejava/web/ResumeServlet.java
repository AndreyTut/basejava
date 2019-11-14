package javawebinar.basejava.web;

import javawebinar.basejava.Config;
import javawebinar.basejava.model.AbstractSection;
import javawebinar.basejava.model.ContactType;
import javawebinar.basejava.model.Resume;
import javawebinar.basejava.model.SectionType;
import javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        Config config = Config.getInstance();
        Storage storage = config.getStorage();
        String uuid = request.getParameter("uuid");
        PrintWriter printWriter = response.getWriter();
        printHead(printWriter);
        if (uuid != null) {
            Resume resume = storage.get(uuid);
            printWriter.println("<h1>" + resume.getFullName() + "</h1>");
            printWriter.println("<div> Контакты:" + resume.getFullName());
            printWriter.println("<ul>");
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                printWriter.println("<li>" + entry.getKey().getTitle() + ": " + entry.getValue());
            }
            printWriter.println("</div>");
            printWriter.println("</ul>");
            printWriter.println("<br/>");
            printWriter.println("<div>");
            printWriter.println("<ul>");
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                printWriter.println("<li>" + entry.getKey().getTitle() + ": " + entry.getValue());
            }
            printWriter.println("</ul>");
            printWriter.println("</div>");
        } else {
            List<Resume> resumes = storage.getAllSorted();
            printWriter.println("<table  border=\"1\">");
            for (Resume r : resumes) {
                printWriter.println("<tr><td>" + r.getUuid() + "</td>" + "<td>" + r.getFullName() + "</td></tr>");
            }
            printWriter.println("</table>");
        }
        printFoot(printWriter);
    }

    private void printHead(PrintWriter printWriter) {
        String s = "<head>\n" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                "    <title>Резюме</title>\n" +
                "</head>\n" +
                "<body>" +
                "<header><a href=#>Управление резюме</a></header>";
        printWriter.println(s);

    }

    private void printFoot(PrintWriter printWriter) {
        printWriter.println("</body>\n" +
                "<footer>\n" +
                "    Проект <a href=\"http://javaops.ru/reg/basejava\">Разработка Web приложения База данных резюме</a>\n" +
                "</footer>" +
                "</html>");
    }
}
