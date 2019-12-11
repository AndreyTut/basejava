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
import java.time.Month;
import java.util.ArrayList;
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
                Organization newOrg = getNewOrg(request, type);
                if (newOrg != null) {
                    List<Organization.Position> positions = new ArrayList<>();
                    addPositionToList(positions, request, type, "newposstartdate", "newposenddate",
                            "newpostitle", "newposdescription");
                    newOrg.setPositions(positions);
                    resume.addSection(type, new OrganizationSection(newOrg));
                } else {
                    resume.getSections().remove(type);
                }
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
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    int orgsCount = Integer.valueOf(request.getParameter(type.name() + "orgscount"));
                    List<Organization> organizations = new ArrayList<>();
                    for (int i = 0; i < orgsCount; i++) {
                        String orgName = request.getParameter(type.name() + "orgname" + (i + 1));
                        String orgUrl = request.getParameter(type.name() + "orgurl" + (i + 1));
                        Link link = new Link(orgName, orgUrl);
                        int posCount = Integer.valueOf(request.getParameter(type.name() + (i + 1) + "poscount"));
                        List<Organization.Position> positions = new ArrayList<>();
                        for (int j = 0; j < posCount; j++) {
                            addPositionToList(positions, request, type, (i + 1) + "startdate" + (j + 1),
                                    (i + 1) + "enddate" + (j + 1), (i + 1) + "title" + (j + 1), (i + 1) + "description" + (j + 1));
                        }
                        addPositionToList(positions, request, type, orgName + "newposstartdate", orgName + "newposenddate",
                                orgName + "newpostitle", orgName + "newposdescription");
                        Organization organization = new Organization(link, positions);
                        organizations.add(organization);
                    }

                    Organization newOrg = getNewOrg(request, type);
                    if (newOrg != null) {
                        List<Organization.Position> positions = new ArrayList<>();
                        addPositionToList(positions, request, type, "newposstartdate", "newposenddate",
                                "newpostitle", "newposdescription");
                        newOrg.setPositions(positions);
                        organizations.add(newOrg);
                    }

                    AbstractSection section = new OrganizationSection(organizations);
                    resume.addSection(type, section);
                    break;
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
                String posnum;
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "deleteorgpos":
                String orgnum = request.getParameter("orgnum");
                Resume resume = storage.get(uuid);
                OrganizationSection section = (OrganizationSection) resume.getSection(SectionType.valueOf(request.getParameter("type")));
                List<Organization> organizations = section.getOrganizations();
                Organization organization = organizations.get(Integer.parseInt(orgnum) - 1);
                if ((posnum = request.getParameter("posnum")) == null) {
                    organizations.remove(organization);
                } else {
                    organization.getPositions().remove(Integer.parseInt(posnum) - 1);
                }
                storage.update(resume);
                request.setAttribute("resume", resume);
                break;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                request.setAttribute("resume", resume);
                break;
            case "add":
                break;
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }
        String nextPage = "view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp";
        request.getRequestDispatcher(nextPage).forward(request, response);
    }

    private Organization getNewOrg(HttpServletRequest request, SectionType type) {
        String newOrgName = request.getParameter(type.name() + "neworgname");
        String newOrgUrl = request.getParameter(type.name() + "neworgurl");
        Organization newOrganization = null;
        if (newOrgName != null && newOrgName.trim().length() != 0) {
            newOrganization = new Organization(newOrgName, newOrgUrl);
        }
        return newOrganization;
    }

    private void addPositionToList(List<Organization.Position> positions, HttpServletRequest request, SectionType type,
                                   String startDatePostfix, String endDatePostfix, String titlePostfix, String descriptionPostfix) {

        String title = request.getParameter(type.name() + titlePostfix);
        if (title != null && title.trim().length() != 0) {
            String startDate = request.getParameter(type.name() + startDatePostfix);
            String endDate = request.getParameter(type.name() + endDatePostfix);
            String description = request.getParameter(type.name() + descriptionPostfix);
            int startYear = Integer.parseInt(startDate.split("-")[0]);
            int endYear = Integer.parseInt(endDate.split("-")[0]);
            Month startMonth = Month.of(Integer.parseInt(startDate.split("-")[1]));
            Month endMonth = Month.of(Integer.parseInt(endDate.split("-")[1]));

            Organization.Position position = new Organization.Position(
                    startYear, startMonth, endYear, endMonth, title, description);
            positions.add(position);
        }
    }
}
