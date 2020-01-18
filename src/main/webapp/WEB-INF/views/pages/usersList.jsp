
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
 <%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 
<tiles:insertDefinition name="angular">
    <tiles:putAttribute name="body">
  <c:import url="/resources/parcial/delete_confirm.html"/>

    <div class="generic-container">
<%--         <%@include file="authheader.jsp" %>    --%>
        <div class="panel panel-default">
              <!-- Default panel contents -->
            <div class="panel-heading"><span class="lead">List of Users </span></div>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Firstname</th>
                        <th>Lastname</th>
                        <th>Email</th>
                        <th>SSO ID</th>
                        <sec:authorize access="hasRole('ADMIN')">
                            <th width="100"></th>
                           <th width="100"></th>
                        </sec:authorize>
                         
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>
                        <td>${user.ssoId}</td>
                         <c:if test="${user.ssoId ne 'admin'}"> 
                         
                        <sec:authorize access="hasRole('ADMIN')">
                            <td><a href="<c:url value='/admin/edit-user-${user.ssoId}' />" class="btn btn-success custom-width">edit</a></td>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ADMIN')">
                            <td>
                              <a href="#" class="btn btn-danger custom-width" data-href="<c:url value='/admin/delete-user-${user.ssoId}' />" data-toggle="modal" data-target="#confirmDelete" data-title="Delete Role" data-message="Are you sure you want to delete this item ?">Delete</a>
                            </td>
                        </sec:authorize>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <sec:authorize access="hasRole('ADMIN')">
            <div class="well">
                <a href="<c:url value='/admin/newuser' />" class="btn btn-primary">Add New User</a>
            </div>
        </sec:authorize>
    </div>
      <script src="<c:url value='/resources/js/app.js' />"></script>
      <script src="<c:url value='/resources/js/service/user_service.js' />"></script>  
      <script src="<c:url value='/resources/js/controller/user_controller.js' />"></script>
 
 
 
    </tiles:putAttribute>
</tiles:insertDefinition>