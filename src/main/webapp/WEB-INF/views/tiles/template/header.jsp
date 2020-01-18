<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <a href="<c:url value="/home/" />" class="navbar-brand">Home</a>
        <a href="<c:url value="/lcd/" />" class="navbar-brand">LCD</a>
        <a href="<c:url value="/rma/" />" class="navbar-brand">RMA</a>
        <a href="<c:url value="/dailyaudited/" />" class="navbar-brand">Daily Audited</a>
        <a href="<c:url value="/dashboard/" />" class="navbar-brand">Dashboard</a>
        <a href="<c:url value="/report/" />" class="navbar-brand">Report</a>

        <div class="collapse navbar-collapse navHeaderCollapse">
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="hasRole('ADMIN')">
                    <li><a href="<c:url value="/admin/users" />">Users</a></li>
                    <li><a href="<c:url value="/admin/roles" />">Roles</a></li>
                    </sec:authorize>

                <li><a href="<c:url value="/logout" />">Logout &nbsp;&nbsp; <strong>${loggedinuser}</strong></a></li>
            </ul>
        </div>
    </div>
</div>