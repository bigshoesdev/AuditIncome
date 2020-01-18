<%-- <%@ page contentType="text/html;charset=UTF-8" %> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="dashboard">
    <tiles:putAttribute name="body">
        <body>
            <div class="dashboard-container hidden-on-narrow" id="dashboard-container" style="visibility:  hidden;">
            </div>
            <script>
                kendo.ui.progress($("body"), true);
            </script>
            <link href="<c:url value='/resources/css/kendo/dashboard.css' />" rel="stylesheet">
            <script src="<c:url value='/resources/js/kendo/dashboard/global.js' />"></script>
            <script src="<c:url value='/resources/js/kendo/dashboard/grid.js' />"></script>
            <script src="<c:url value='/resources/js/kendo/dashboard/line.js' />"></script>
            <script src="<c:url value='/resources/js/kendo/dashboard/pie.js' />"></script>
            <script src="<c:url value='/resources/js/kendo/dashboard/dashboard.js' />"></script>
        </body>
    </tiles:putAttribute>
</tiles:insertDefinition>