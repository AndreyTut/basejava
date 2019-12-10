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
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <i><h3>Contacts:</h3></i>
    <c:forEach items="${resume.contacts}" var="pair">
        <jsp:useBean id="pair" type="java.util.Map.Entry<javawebinar.basejava.model.ContactType, java.lang.String>"/>
        <%=webutil.toHtml(pair.getKey().getTitle(), pair.getValue())%><br>
    </c:forEach>
    <hr>
    <c:forEach items="${resume.sections}" var="entry">
        <jsp:useBean id="entry"
                     type="java.util.Map.Entry<javawebinar.basejava.model.SectionType, javawebinar.basejava.model.AbstractSection>"/>
        <%=webutil.toWebSection(entry)%>
    </c:forEach>
</section>
<a href="resume">К списку резюме</a>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
