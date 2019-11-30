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
                        items="${listsection.items}" var="item">${item} &#10;</c:forEach></textarea>
                </dd>
            </dl>
        </c:if>
    </c:forEach>
    <hr>
    <button type="submit">Сохранить</button>
    <button type="reset" onclick="window.history.back()">Отменить</button>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
