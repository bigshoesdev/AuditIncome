<%-- <%@ page contentType="text/html;charset=UTF-8" %> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<tiles:insertDefinition name="grid">
    <tiles:putAttribute name="body">

        <body ng-app="app">
            <div ng-controller="MainCtrl">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <td>Count of PC</td>
                            <td>Count of LT</td>
                            <td>Count of LCD</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><table class="table table-striped">
                                    <tr ng-repeat="lcdObj in dailyaudited.pcList track by $index">
                                        <td>{{lcdObj.count}}</td>
                                        <td>{{lcdObj.date| date:'yyyy-MM-dd'}}</td>
                                    </tr>
                                </table></td>
                            <td><table class="table table-striped">
                                    <tr ng-repeat="lcdObj in dailyaudited.ltList track by $index">
                                        <td>{{lcdObj.count}}</td>
                                        <td>{{lcdObj.date| date:'yyyy-MM-dd'}}</td>
                                    </tr>
                                </table></td>
                            <td><table class="table table-striped">
                                    <tr ng-repeat="lcdObj in dailyaudited.lcdList track by $index">
                                        <td>{{lcdObj.count}}</td>
                                        <td>{{lcdObj.date| date:'yyyy-MM-dd'}}</td>
                                    </tr>
                                </table></td>
                        </tr>

                    </tbody>
                    <tfoot>
                        <tr>
                            <td>Grand Total&nbsp;&nbsp;{{dailyaudited.pcGrandTotal}}</td>
                            <td>Grand Total&nbsp;&nbsp;{{dailyaudited.ltGrandTotal}}</td>
                            <td>Grand Total&nbsp;&nbsp;{{dailyaudited.lcdGrandTotal}}</td>
                        </tr>
                    </tfoot>
                </table>
            </div>


            <script
            src="<c:url value='/resources/js/controller/dailyaudited_grid_controller.js' />"></script>
        </body>
        <c:import url="/resources/parcial/edit_confirm.html" />

    </tiles:putAttribute>
</tiles:insertDefinition>