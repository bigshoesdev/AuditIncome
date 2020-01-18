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
    <link href="<c:url value='/resources/lib/kendo/content/shared/styles/examples-offline.css' />" rel="stylesheet">
    <link href="<c:url value='/resources/lib/kendo/styles/kendo.common.min.css' />" rel="stylesheet">
    <link href="<c:url value='/resources/lib/kendo/styles/kendo.rtl.min.css' />" rel="stylesheet">
    <link href="<c:url value='/resources/lib/kendo/styles/kendo.default.min.css' />" rel="stylesheet">
    <link href="<c:url value='/resources/lib/kendo/styles/kendo.default.mobile.min.css' />" rel="stylesheet">
    <script src="<c:url value='/resources/lib/kendo/js/jszip.min.js' />"></script>
    <script src="<c:url value='/resources/lib/kendo/js/kendo.all.min.js' />"></script>
    <script src="<c:url value='/resources/lib/kendo/content/shared/js/console.js' />"></script>
    <link href="<c:url value='/resources/css/main.css' />" rel="stylesheet"></link>
    <style>
        /*
            Use the DejaVu Sans font for display and embedding in the PDF file.
            The standard PDF fonts have no support for Unicode characters.
        */
        .k-grid {
            font-family: "DejaVu Sans", "Arial", sans-serif;
        }

        /* Hide the Grid header and pager during export */
        .k-pdf-export .k-grid-toolbar,
        .k-pdf-export .k-pager-wrap
        {
            display: none;
        }
        .k-confirm .k-window-titlebar::before, 
        .k-alert .k-window-titlebar::before { 
            content: 'Alert'; 
            font-weight: bold; 
        } 
        .k-confirm .k-window-titlebar .k-dialog-title { 
            visibility: collapse; 
        } 
        .k-dialog .k-window-titlebar .k-dialog-title { 
            visibility: hidden; 
        } 
        .k-picker-wrap {
            padding-left: 1px;
        }

        .k-dialog .k-window-titlebar { 
            display: none; 
        } 
        
    </style>
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