 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
 <%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<tiles:insertDefinition name="angular">
    <tiles:putAttribute name="body">
 
 
 <div class="generic-container">
<%--         <%@include file="authheader.jsp" %> --%>
 
        <div class="well lead">Add Role Form</div>
        <form:form method="POST" modelAttribute="role" class="form-horizontal">
            <form:input type="hidden" path="id" id="id"/>
             
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="name">Name</label>
                    <div class="col-md-7">
                        <form:input type="text" path="name" id="name" class="form-control input-sm"/>
                        <div class="has-error">
                            <form:errors path="name" class="help-inline"/>
                        </div>
                    </div>
                </div>
            </div>
     
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="description">Description</label>
                    <div class="col-md-7">
                        <form:textarea  path="description" id="description" class="form-control input-sm" />
                        <div class="has-error">
                            <form:errors path="description" class="help-inline"/>
                        </div>
                    </div>
                </div>
            </div>
     
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="editable">Can edit Items</label>
                    <div class="col-md-7">
                        <c:choose>
                            <c:when test="${edit}">
                                <form:checkbox  path="editable" id="editable" class="form-control input-sm" />
                            </c:when>
                            <c:otherwise>
                                <form:checkbox  path="editable" id="editable" class="form-control input-sm" />
                                <div class="has-error">
                                    <form:errors path="editable" class="help-inline"/>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
     
         
     
     
            <div class="row">
                <div class="form-actions floatRight">
                    <c:choose>
                        <c:when test="${edit}">
                            <input type="submit" value="Update" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/admin/roles' />">Cancel</a>
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/admin/roles' />">Cancel</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
    </div>
 
   <script src="<c:url value='/resources/js/app.js' />"></script>
      <script src="<c:url value='/resources/js/service/role_service.js' />"></script>  
      <script src="<c:url value='/resources/js/controller/role_controller.js' />"></script>
 
 
 
    </tiles:putAttribute>
</tiles:insertDefinition>