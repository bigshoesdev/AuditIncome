<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> --%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-with, initial-scale=1.0"/>
    <c:set var="context" value="${pageContext.request.contextPath}"/>
    <title><tiles:getAsString name="title" /></title>
    <link href="<c:url value='/webjars/bootstrap/3.3.6/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/resources/css/styles.css' />" rel="stylesheet"></link>
    <script type="text/javascript" src="<c:url value='/webjars/jquery/2.2.4/jquery.js' />"></script>
    <script type="text/javascript" src="<c:url value='/webjars/jquery-ui/1.12.0/jquery-ui.js' />"></script>
    <script type="text/javascript" src="<c:url value='/webjars/bootstrap/3.3.6/js/bootstrap.js' />"></script>
    <script type="text/javascript" src="<c:url value='/webjars/angularjs/1.5.5/angular.js' />"></script> 
    <script type="text/javascript" src="<c:url value='/webjars/angularjs/1.5.5/angular-touch.js' />"></script> 
    <script type="text/javascript" src="<c:url value='/webjars/angularjs/1.5.5/angular-animate.js' />"></script> 
    <script src="<c:url value='/resources/lib/angular-ui-grid/csv.js' />"></script>
    <script src="<c:url value='/resources/lib/angular-ui-grid/pdfmake.js' />"></script>
    <script src="<c:url value='/resources/lib/angular-ui-grid/vfs_fonts.js' />"></script>
    <script src="<c:url value='/resources/lib/angular-ui-grid/ui-grid.js' />"></script>
    <link href="<c:url value='/resources/lib/angular-ui-grid/ui-grid.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/resources/css/main.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/webjars/jquery-ui/1.12.0/jquery-ui.css' />" rel="stylesheet"></link>
</head>

<header id="header">
    <tiles:insertAttribute name="header" />
</header>

<section id="menu">
    <tiles:insertAttribute name="menu" />
</section>


<section id="site-content">
    <tiles:insertAttribute name="body" />
</section>

<footer id="footer">
    <tiles:insertAttribute name="footer" />
</footer>
<sec:csrfMetaTags/>



</html>