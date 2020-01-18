 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
 <%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<tiles:insertDefinition name="angular">
    <tiles:putAttribute name="body">
 
 
   <div class="generic-container">
<%--         <%@include file="authheader.jsp" %> --%>
         
        <div class="alert alert-success lead">
            ${success}
        </div>
         
        <span class="well floatRight">
            Go to <a href="<c:url value='/admin/${type}' />">${type} List</a>
        </span>
    </div>
 
   <script src="<c:url value='/resources/js/app.js' />"></script>
      <script src="<c:url value='/resources/js/service/user_service.js' />"></script>  
      <script src="<c:url value='/resources/js/controller/user_controller.js' />"></script>
 
 
 
    </tiles:putAttribute>
</tiles:insertDefinition>