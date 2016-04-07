<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
	var labels = new Array();
	var dataCount = new Array();
	var count;
	var label;
	<c:forEach items="${groupedPostsByMonth}" var="groupedPosts" varStatus="groupedLoop">
		label =  '<fmt:formatDate value="${groupedPosts[0].createdTime}" pattern="MM-yyyy" />';
		labels.push(label);
		count = '${fn:length(groupedPosts)}';
		dataCount.push(count);
	</c:forEach>
</script>
<script src="js/charts.js"></script>
<canvas id="myChart" width="100%"></canvas>