<%@ page import="javawebinar.basejava.model.ContactType" %>
<%@ page import="javawebinar.basejava.model.SectionType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:useBean id="resume" class="javawebinar.basejava.model.Resume" scope="request"/>
    <jsp:useBean id="webutil" type="javawebinar.basejava.util.WebUtil" scope="request"/>
    <title>${resume.fullName}</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<h1>${resume.uuid==null ?'Creating new resume':'Editig resume'}</h1>
<form method="post" action="resume">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <dl>
        <dt>Имя:</dt>
        <dd><input type="text" name="fullName" value="${resume.fullName}"></dd>
    </dl>
    <hr>


    <c:forEach items="<%=ContactType.values()%>" var="type">
        <dl>
            <dt>${type.name()}</dt>
            <dd><input name="${type.name()}" type="text" value="${resume.getContact(type)}" size="30"></dd>
        </dl>
    </c:forEach>
    <hr>
    <c:forEach items="<%=SectionType.values()%>" var="type">
        <jsp:useBean id="type" type="javawebinar.basejava.model.SectionType"/>
        <c:if test="${type==SectionType.PERSONAL||type==SectionType.OBJECTIVE}">
            <dl>
                <dt>${type.name()}</dt>
                <dd><input name="${type.name()}" type="text" value="${resume.getSection(type)}" size="50"></dd>
            </dl>
        </c:if>

        <c:if test="${type==SectionType.ACHIEVEMENT||type==SectionType.QUALIFICATIONS}">
            <dl>
                <dt>${type.name()}</dt>
                <c:set var="listsection" value="${resume.getSection(type)}"/>
                <c:if test="${listsection!=null}">
                    <jsp:useBean id="listsection" type="javawebinar.basejava.model.ListSection"/>
                </c:if>
                <dd><textarea id="textarea1" name="${type.name()}" cols="40" rows="5"><c:forEach
                        items="${listsection.items}" var="item">${item}&#10;</c:forEach></textarea>
                </dd>
            </dl>
        </c:if>
        <c:if test="${type==SectionType.EXPERIENCE||type==SectionType.EDUCATION}">
            <c:set var="orgsection" value="${resume.getSection(type)}"/>
            <c:choose>
                <c:when test="${orgsection!=null}">
                    <dl>
                        <dt>${type.name()}</dt>
                        <dd><input type="text" value="${type.title}" name="${type.name()}"></dd>
                        <a href="resume?action=neworg&type=${type}&uuid=${resume.uuid}">add new organization
                            to ${type}</a>
                    </dl>
                    <jsp:useBean id="orgsection" type="javawebinar.basejava.model.OrganizationSection"/>
                    <input type="hidden" name="${type.name()}orgscount" value="${orgsection.organizations.size()}"/>
                    <c:set var="orgcounter" value="0"/>
                    <c:forEach var="organization" items="${orgsection.organizations}">
                        <c:set var="orgcounter" value="${orgcounter+1}"/>
                        <dl>
                            <dt>Organization:</dt>
                            <dd>
                                <a href="resume?action=delete&uuid=${resume.uuid}&orgnum=${orgcounter}&type=${type.name()}"><img
                                        src="img/delete.png"></a>
                                <input name="${type.name()}orgname${orgcounter}" type="text"
                                       value="${organization.homePage.name}"/>
                                <input name="${type.name()}orgurl${orgcounter}" type="text"
                                       value="${organization.homePage.url}"/>
                                <a href="resume?action=newpos&uuid=${resume.uuid}&type=${type.name()}&orgnum=${orgcounter}">add
                                    new position to ${organization.homePage.name}</a>
                            </dd>
                        </dl>
                        <input type="hidden" name="${type.name()}${orgcounter}poscount"
                               value="${organization.positions.size()}">
                        <c:set var="poscounter" value="0"/>
                        <c:forEach var="position" items="${organization.positions}">
                            <c:set var="poscounter" value="${poscounter+1}"/>
                            <dl>
                                <dt>position title:</dt>
                                <dd>
                                    <a href="resume?action=delete&uuid=${resume.uuid}&type=${type.name()}&orgnum=${orgcounter}&posnum=${poscounter}"><img
                                            src="img/delete.png"></a>
                                    <input type="text" name="${type.name()}${orgcounter}title${poscounter}"
                                           value="${position.title}">
                                </dd>
                            </dl>

                            <dl>
                                <dt>start date:</dt>
                                <dd>
                                    <input type="date"
                                           name="${type.name()}${orgcounter}startdate${poscounter}"
                                           value="${position.startDate}"/>
                                </dd>
                            </dl>
                            <dl>
                                <dt>end date:</dt>
                                <dd>
                                    <input type="date"
                                           name="${type.name()}${orgcounter}enddate${poscounter}"
                                           value="${position.endDate}"/>
                                </dd>
                            </dl>
                            <dl>
                                <dt>description:</dt>
                                <dd>
                                <textarea name="${type.name()}${orgcounter}description${poscounter}"
                                          cols="40" rows="5">${position.description}</textarea>
                                </dd>
                            </dl>
                        </c:forEach>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:if test="${resume.uuid!=null}">
                        <dl>
                            <dt>${type}:</dt>
                            <dd><a href="resume?action=neworg&type=${type}&uuid=${resume.uuid}">add new organization
                                to ${type}</a>
                            </dd>
                        </dl>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </c:if>
    </c:forEach>
    <hr>
    <button type="submit">Сохранить</button>
    <button type="reset" onclick="window.history.back()">Отменить</button>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
