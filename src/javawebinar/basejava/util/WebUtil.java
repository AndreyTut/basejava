package javawebinar.basejava.util;

import javawebinar.basejava.model.*;

import java.util.List;
import java.util.Map;

public class WebUtil {
    protected String toHtml0(String title, String value) {
        return title + ": " + value;
    }

    public String toHtml(String title, String value) {
        return (value == null) ? "" : toHtml0(title, value);
    }

    public String toLink(String href, String title) {
        if (!"".equals(href)) {
            return "<a href='" + href + "'>" + title + "</a>";
        } else {
            return title;
        }
    }

    public String  toWebSection(Map.Entry<SectionType, AbstractSection> entry) {
        SectionType type = entry.getKey();
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                return "<h2><i>" + type.getTitle() + ":</i></h2>" + entry.getValue().toString();
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return "<h2><i>" + type.getTitle() + ":</i></h2>" +
                        listToHtml(((ListSection) entry.getValue()).getItems(),
                                "<ul>", "</ul>", item -> "<li>" + item + "</li>");
            case EXPERIENCE:
            case EDUCATION:
                return "<h2><i>" + type.getTitle() + ":</i></h2>" +
                        listToHtml(((OrganizationSection) entry.getValue()).getOrganizations(),
                                "", "", this::orgToHtml);
        }
        return "";
    }

    private <T> String listToHtml(List<T> list, String startTag, String endTag, TagWrapper<T> wrapper) {
        StringBuilder stringBuilder = new StringBuilder(startTag);
        for (T t : list) {
            stringBuilder.append(wrapper.wrap(t));
        }
        stringBuilder.append(endTag);
        return stringBuilder.toString();
    }

    private String orgToHtml(Organization organization) {
        String title = toLink(organization.getHomePage().getUrl(), organization.getHomePage().getName());
        StringBuilder stringBuilder = new StringBuilder("<h3>" + title + "</h3>");
        stringBuilder.append("<table>");
        for (Organization.Position position : organization.getPositions()) {
            stringBuilder.append("<tr>");
            stringBuilder.append("<td style=\"vertical-align: top\">");
            stringBuilder.append(DateUtil.getShortDate(position.getStartDate()));
            stringBuilder.append(" - " + DateUtil.getShortDate(position.getEndDate()));
            stringBuilder.append("</td>");
            stringBuilder.append("<td>");
            stringBuilder.append("<b>" + position.getTitle() + "</b><br>");
            stringBuilder.append(position.getDescription());
            stringBuilder.append("</td>");
            stringBuilder.append("</tr>");
        }
        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }

    private interface TagWrapper<T> {
        String wrap(T t);
    }
}
