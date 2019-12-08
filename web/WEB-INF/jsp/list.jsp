<%@ page import="javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Title</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table class="list" border="1">
        <tr>
            <th>Name</th>
            <th>email</th>
            <th>delete</th>
            <th>edit</th>
        </tr>
        <c:forEach var="resume" items="${resumes}">
            <jsp:useBean id="resume" class="javawebinar.basejava.model.Resume"/>
            <tr>
                <c:set var="uuid" value="${resume.uuid}"/>
                <c:set var="full_name" value="${resume.fullName}"/>
                <td><a href="resume?action=view&uuid=${uuid}">${full_name}</a></td>
                <td><a href="${resume.getContact(ContactType.MAIL)}">${resume.getContact(ContactType.MAIL)}<img src="img/email.png"></a> </td>
                <td><a href="resume?action=delete&uuid=${uuid}"><img src="img/delete.png"></a></td>
                <td><a href="resume?action=edit&uuid=${uuid}"><img src="img/pencil.png"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<a href="resume?action=add">Добавить резюме<img src="img/add.png"></a>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
