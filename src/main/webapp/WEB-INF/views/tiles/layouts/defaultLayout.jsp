<%@ page contentType="text/html;charset=UTF-8" %>
<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> --%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-with, initial-scale=1.0"/>
        <c:set var="context" value="${pageContext.request.contextPath}"/>
        <title><tiles:getAsString name="title" /></title>
        <link href="<c:url value='/webjars/bootstrap/3.3.6/css/bootstrap.min.css' />" rel="stylesheet">
        <link href="<c:url value='/resources/css/styles.css' />" rel="stylesheet"></link>
        <script type="text/javascript" src="<c:url value='/webjars/jquery/2.2.4/jquery.js' />"></script>
        <script type="text/javascript" src="<c:url value='/webjars/bootstrap/3.3.6/js/bootstrap.js' />"></script>
        <script type="text/javascript" src="<c:url value='/webjars/angularjs/1.5.5/angular.js' />"></script>
        <script type="text/javascript" src="<c:url value='/webjars/angularjs/1.5.5/angular-resource.js' />"></script>  
    </head>

    <body>
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
    </body>
</html>