<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="en_US" scope="session"/>
<script>
	var labels = new Array();
	var dataCount = new Array();
	var count;
	var label;
	<c:forEach items="${groupedPostsByMonth}" var="groupedPosts" varStatus="groupedLoop">
		label =  '<fmt:formatDate value="${groupedPosts[0].created_time}" pattern="yyyy-MM-01" />';
		labels.push(label);
		count = '${fn:length(groupedPosts)}';
		dataCount.push(count);
	</c:forEach>

	var locationLabels = new Array();
	var locationDataCount = new Array();
	var locationLabel;
	var locationCount;
	<c:forEach items="${groupedPostsWithLocationByMonth}" var="groupedPosts" varStatus="groupedLoop">
		locationLabel =  '<fmt:formatDate value="${groupedPosts[0].created_time}" pattern="yyyy-MM-01" />';
		locationLabels.push(locationLabel);
		locationCount = '${fn:length(groupedPosts)}';
		locationDataCount.push(locationCount);
	</c:forEach>	
	
	var photoLabels = new Array();
	var photoDataCount = new Array();
	var photoLabel;
	var photoCount;
	<c:forEach items="${groupedPostsWithPhotoByMonth}" var="groupedPosts" varStatus="groupedLoop">
		photoLabel =  '<fmt:formatDate value="${groupedPosts[0].created_time}" pattern="yyyy-MM-01" />';
		photoLabels.push(photoLabel);
		photoCount = '${fn:length(groupedPosts)}';
		photoDataCount.push(photoCount);
	</c:forEach>
</script>
<script src="js/charts.js"></script>

<style>

#chart svg {
  /* height: 600px; */
  width: 100%;
}

#chart {
	width: 100%;
}

</style>


<div id="chart"></div>