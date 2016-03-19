<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="slide right-slide">
	<script>
		var markers = new Array();
		var id;
		var longitude;
		var latitude;
		var markerEntity;
		
		
		var infoWindowContent = new Array();
		var infoEntity;
		var infoEntityString;
		
		<c:forEach items="${postsWithLocation}" var="locationPost">
			markerEntity = new Array();
			id = '${locationPost.fst.message}';
			longitude = ${locationPost.fst.place.location.longitude};
			latitude = ${locationPost.fst.place.location.latitude};
			markerEntity.push(id);
			markerEntity.push(latitude);
			markerEntity.push(longitude);
			markers.push(markerEntity);
			
			infoEntity = new Array();
			infoEntityString = '<div class="info_content">' +
									'<h3>${locationPost.fst.message}</h3>' +
									'<p><a href="${locationPost.fst.actions[0].link}" target="_blank">You were here ${locationPost.snd} times</a></p>' +
								'</div';
			infoEntity.push(infoEntityString);
			infoWindowContent.push(infoEntity);
			
		</c:forEach>
	                         
	</script>

	<p>Here are the posts that might affect professional work life:</p>
	<ul id="result-container">
		<c:forEach items="${workThreatList}" var="workPost"
			varStatus="workLoop">
			<li title="${workPost.message}"><a target="_blank"
				href="${workPost.actions[0].link}"> ${workPost.message} - <fmt:formatDate
						value="${workPost.createdTime}" pattern="dd-MM-yyyy HH:mm:ss" />
			</a></li>
		</c:forEach>
	</ul>
	<p>Privacy settings:</p>
	<ul>
		<li>Who can see yours posts: ${postPrivacy}</li>
	</ul>

	<p>Photos seen by everyone (current cover and profile picture
		cannot have privacy changed)</p>
	<ul>
		<c:forEach items="${photoPostList}" var="photoPost">
			<li title="${photoPost.message}"><a target="_blank"
				href="${photoPost.actions[0].link}"> ${photoPost.message} - <fmt:formatDate
						value="${photoPost.createdTime}" pattern="dd-MM-yyyy HH:mm:ss" />
			</a></li>
		</c:forEach>
	</ul>
	
	<div id="map_wrapper">
    	<div id="map_canvas" class="mapping"></div>
	</div>

	<div class="next-button-container pull-right">
		<a class="next-button" href="#"><span
			class="glyphicon glyphicon-arrow-right"></span></a>
	</div>
</div>