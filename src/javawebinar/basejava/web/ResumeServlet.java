package javawebinar.basejava.web;

import javawebinar.basejava.Config;
import javawebinar.basejava.model.*;
import javawebinar.basejava.storage.SqlStorage;
import javawebinar.basejava.storage.Storage;
import javawebinar.basejava.util.WebUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {

    private Storage storage;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    public void destroy() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                LOG.log(Level.INFO, String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, String.format("Error deregistering driver %s", driver), e);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String fullName = request.getParameter("fullName");
        String uuid = request.getParameter("uuid");
        Resume resume = !uuid.equals("") ? storage.get(uuid) : new Resume(fullName);
        resume.setFullName(fullName);

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value == null || value.trim().length() == 0) {
                continue;
            }
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(type, new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> items = Arrays.asList(value.split("\n"));
                    List<String> trimmed = items.stream()
                            .map(String::trim)
                            .collect(Collectors.toList());
                    resume.addSection(type, new ListSection(trimmed));
            }
        }

        if (!uuid.equals("")) {
            storage.update(resume);
        } else {
            storage.save(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "delete":
                storage.delete(request.getParameter("uuid"));
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                request.setAttribute("resume", storage.get(uuid));
                break;
            case "add":
                break;
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }
        WebUtil util = new WebUtil();
        request.setAttribute("webutil", util);
        String nextPage = "view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp";
        request.getRequestDispatcher(nextPage).forward(request, response);
    }
}
