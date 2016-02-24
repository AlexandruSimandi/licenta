<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <title>SimSec</title>
	<meta charset="UTF-8">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/main.css">
    <script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="js/main.js"></script>
    <c:if test="${screenStatus == 'logged'}">  
		<script>
			$(window).ready(function(){
				nextSlide(1);
				nextSlide(1000);
			});
		</script>
	</c:if> 
	<c:if test="${screenStatus == 'loading'}">  
		<script>
			$(window).ready(function(){
				nextSlide(1000);
			});
			setTimeout(function(){
				window.location = "logged";
			},1000);
		</script>
	</c:if> 
</head>

<body>
	<jsp:include page="include/header.jsp"/>
	<jsp:include page="include/login.jsp"/>
	<jsp:include page="include/loading.jsp"/>
	<jsp:include page="include/results.jsp"/>
	<jsp:include page="include/recommendations.jsp"/>
</body>

</html>
