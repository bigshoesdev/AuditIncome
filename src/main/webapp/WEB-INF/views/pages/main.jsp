<%-- <%@ page contentType="text/html;charset=UTF-8" %> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="kendogrid">
    <tiles:putAttribute name="body">
        <body>
            <div id="tabstrip" style="visibility:  hidden;margin-bottom: 40px;">
                <ul>
                    <li class="k-state-active">
                        Grid Show
                    </li>
                    <li >
                        Line Chart Show
                    </li>
                    <li>
                        Pie Chart Show
                    </li>
                </ul>
                <div>
                    <div id="grid">
                    </div>
                </div>
                <div>
                    <div id="line">
                        <div class="line-container hidden-on-narrow">
                            <div class="line-filter-body">
                                <div class="line-filter-addon">
                                    <button id="filterAddBtn" class="k-primary">ADD MORE</button>
                                </div>
                                <div class="line-filter-adddash">
                                    <button id="addDashBtn" class="k-primary">ADD TO DASHBOARD</button>
                                </div>
                                <div class="line-filter-wrapper">
                                </div>
                            </div>

                            <div class="line-axis-body">
                                <div class="line-axis-data-type k-content">
                                    <h4>Data Type</h4>
                                    <input id="line-axis-data-type" style="width: 100%" />
                                </div>
                                <div class="line-axis-start-date k-content">
                                    <h4>Start Date</h4>
                                    <input id="line-axis-start-date" style="width: 100%" />
                                </div>
                                <div class="line-axis-end-date k-content">
                                    <h4>End Date</h4>
                                    <input id="line-axis-end-date" style="width: 100%" />
                                </div>
                                <div class="line-axis-show-btn">
                                    <button id="showChartBtn" class="k-primary">SHOW CHART</button>
                                </div>
                            </div>
                            <div class="line-main-body box wide">
                                <div class="box-col line-main-data-unit">
                                    <h4>Base date unit</h4>
                                    <ul class="options">
                                        <li>
                                            <input id="baseUnitAuto" name="baseUnit"
                                                   type="radio" value="" autocomplete="off" />
                                            <label for="baseUnitAuto">Automatic (default)</label>
                                        </li>
                                        <li>
                                            <input id="baseUnitYears" name="baseUnit"
                                                   type="radio" value="years" autocomplete="off" />
                                            <label for="baseUnitYears">Years</label>
                                        </li>
                                        <li>
                                            <input id="baseUnitMonths" name="baseUnit"
                                                   type="radio" value="months" autocomplete="off" />
                                            <label for="baseUnitMonths">Months</label>
                                        </li>
                                        <li>
                                            <input id="baseUnitWeeks" name="baseUnit"
                                                   type="radio" value="weeks" checked="checked" autocomplete="off" />
                                            <label for="baseUnitWeeks">Weeks</label>
                                        </li>
                                        <li>
                                            <input id="baseUnitDays" name="baseUnit"
                                                   type="radio" value="days" autocomplete="off" />
                                            <label for="baseUnitDays">Days</label>
                                        </li>
                                    </ul>
                                </div>

                                <div class="box-col line-main-aggregate">
                                    <h4>Aggregate function</h4>
                                    <ul class="options">
                                        <li>
                                            <input id="aggregateMax" name="aggregate"
                                                   type="radio" value="max" autocomplete="off" />
                                            <label for="aggregateMax">Max (default)</label>
                                        </li>
                                        <li>
                                            <input id="aggregateMin" name="aggregate"
                                                   type="radio" value="min" autocomplete="off" />
                                            <label for="aggregateMin">Min</label>
                                        </li>
                                        <li>
                                            <input id="aggregateSum" name="aggregate"
                                                   type="radio" value="sum" autocomplete="off" />
                                            <label for="aggregateSum">Sum</label>
                                        </li>
                                        <li>
                                            <input id="aggregateAvg" name="aggregate"
                                                   type="radio" value="avg" checked="checked" autocomplete="off" />
                                            <label for="aggregateAvg">Avg</label>
                                        </li>
                                    </ul>
                                </div>

                                <div class="box-col line-main-export-pdf">
                                    <button class='export-line-pdf k-button'>Export as PDF</button>
                                </div>

                                <div class="box-col line-main-export-image">
                                    <button class='export-line-img k-button'>Export as Image</button>
                                </div>

                                <div class="box-col line-main-export-svg">
                                    <button class='export-line-svg k-button'>Export as SVG</button>
                                </div>

                                <div class="line-main-view" id="line-main-view" style="background: center no-repeat url(<c:url value='/resources/lib/kendo/content/styles/world-map.png' />');"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <div id="pie">
                        <div class="pie-container hidden-on-narrow">
                            <div class="pie-filter-body">
                                <div class="pie-filter-grid-wrapper">
                                    <h4>Please Select Filter Here</h4>
                                    <div id="pie-filter-grid"></div>
                                </div>
                                <div class="box-col pie-filter-groupby">
                                    <h4>Choose Column To Chart By</h4>
                                    <input id="pie-filter-groupby" style="width: 100%" />
                                </div>
                                <div class="box-col pie-filter-groupby">
                                    <h4>Data Type</h4>
                                    <input id="pie-filter-data-type" style="width: 100%" />
                                </div>
                                <div class="box-col pie-filter-groupby">
                                    <h4>Start Date</h4>
                                    <input id="pie-filter-start-date" style="width: 100%" />
                                </div>
                                <div class="box-col pie-filter-groupby">
                                    <h4>End Date</h4>
                                    <input id="pie-filter-end-date" style="width: 100%" />
                                </div>
                                <div class="box-col pie-show-chart">
                                    <button id="showPieBtn" class="k-primary">SHOW CHART</button>
                                    <button id="addToDashBtn" class="k-primary">ADD TO DASHBOARD</button>
                                </div>
                            </div>

                            <div class="pie-main-body box wide">
                                <div class="box-col pie-main-export">
                                    <h4>Aggregate function</h4>
                                    <ul class="options">
                                        <li>
                                            <button class='export-pie-pdf k-button'>Export as PDF</button>
                                        </li>
<!--                                        <li>
                                            <button class='export-pie-img k-button'>Export as Image</button>
                                        </li>-->
                                        <li>
                                            <button class='export-pie-svg k-button'>Export as SVG</button>
                                        </li>
                                    </ul>
                                </div>
                                <div class="pie-main-view" id="pie-main-view" style="background: center no-repeat url(<c:url value='/resources/lib/kendo/content/styles/world-map.png' />');"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <script>
        kendo.ui.progress($("body"), true);
    </script>
    <link href="<c:url value='/resources/css/kendo/grid.css' />" rel="stylesheet">
    <script src="<c:url value='/resources/js/kendo/main/line.js' />"></script>
    <script src="<c:url value='/resources/js/kendo/main/grid.js' />"></script>
    <script src="<c:url value='/resources/js/kendo/main/pie.js' />"></script>
    <script src="<c:url value='/resources/js/kendo/main/index.js' />"></script>

</body>
<c:import url="/resources/parcial/edit_confirm.html"/>
</tiles:putAttribute>
</tiles:insertDefinition>