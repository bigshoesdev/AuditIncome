<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>

<head>
    <title>main</title>
    <meta name="viewport" content="width=device-with, initial-scale=1.0"/>
 <link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
 <link href="./resources/css/styles.css" rel="stylesheet">
</head>

<body>
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="body"/>
    <tiles:insertAttribute name="footer"/>

 
     <script type="text/javascript" src="webjars/jquery/2.2.4/jquery.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.js"></script>
</body>

</html>