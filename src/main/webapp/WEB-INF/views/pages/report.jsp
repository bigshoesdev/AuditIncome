<%-- <%@ page contentType="text/html;charset=UTF-8" %> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="kendogrid">
    <tiles:putAttribute name="body">
        <body>
            <div class="container">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="customerID">Customer ID:</label>
                            <input type="text" class="form-control" id="customerID">
                        </div>
                        <div class="form-group">
                            <label for="lotID">Lot ID:</label>
                            <input type="text" class="form-control" id="lotID">
                        </div>
                        <button type="button" class="btn btn-primary" id="makeReport">Export Customer Report </button>
                    </div>
                </div>
            </div>
            <script src="<c:url value='/resources/js/kendo/report/index.js' />"></script>
        </body>
        <c:import url="/resources/parcial/edit_confirm.html"/>
    </tiles:putAttribute>
</tiles:insertDefinition>