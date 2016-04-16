<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance"
	prefix="layout"%>

<layout:extends name="base">
	<layout:put block="header" type="REPLACE"></layout:put>
	<layout:put block="contents">
		<script src="js/main.js"></script>
		<script src="js/Chart.js"></script>
		<script src="js/d3.min.js"></script>
		<script src="js/c3.min.js"></script>
		<link rel="stylesheet" href="css/c3.css">
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
				longitude = ${locationPost.fst.longitude};
				latitude = ${locationPost.fst.latitude};
				markerEntity.push(id);
				markerEntity.push(latitude);
				markerEntity.push(longitude);
				markers.push(markerEntity);
				
				infoEntity = new Array();
				infoEntityString = '<div class="info_content">' +
										'<h5>${locationPost.fst.message}</h5>' +
										'<p><a href="${locationPost.fst.link}" target="_blank">You were here ${locationPost.snd} times</a></p>' +
									'</div';
				infoEntity.push(infoEntityString);
				infoWindowContent.push(infoEntity);
				
			</c:forEach>
		                         
		</script>
		<style>
			#map_wrapper {
			   height: 80vh;
			}
			
			#map_canvas {
			    width: 100%;
			    height: 100%;
			}
		</style>
		<main>
		<ul id="tabs" class="tabs">
			<li class="tab col s3"><a class="black-text" href="#test1">Stats</a></li>
			<li class="tab col s3"><a class="active black-text"
				href="#test2">Locations</a></li>
			<li class="tab col s3"><a class="black-text" href="#test3">Dangerous
					posts</a></li>
			<li class="tab col s3"><a class="black-text" href="#test4">Public
					Photos</a></li>
			<li class="tab col s3"><a class="black-text" href="#test5">Privacy
					Settings</a></li>
		</ul>
			<div class="row">
				<div class="col s12 container"></div>
				<div id="test1" class="col s12">
					<jsp:include page="include/results/stats.jsp"/>
				</div>
				<div id="test2" class="col s12">
					<div id="map_wrapper">
						<div id="map_canvas" class="mapping"></div>
					</div>
				</div>
				<div id="test3" class="col s12">
				<table class="highlight">
					<thead>
						<tr>
							<th data-field="id">Date</th>
							<th data-field="name">Description</th>
							<th data-field="price">Privacy</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${workThreatList}" var="workPost"
							varStatus="workLoop">
							<tr>
								<td><a target="_blank" href="${workPost.link}"><fmt:formatDate value="${workPost.created_time}"
										pattern="dd-MM-yyyy HH:mm:ss" /></a></td>
								<td>${workPost.message}</td>
								<td>${workPost.privacy}</td>
							</tr>						
						</c:forEach>
					</tbody>
				</table>
				</div>
				<div id="test4" class="col s12">
					<p>Photos seen by everyone (current cover and profile picture
						cannot have privacy changed)</p>
					<ul>
						<c:forEach items="${photoPostList}" var="photoPost">
							<li title="${photoPost.message}"><a target="_blank"
								href="${photoPost.link}"> ${photoPost.message} - <fmt:formatDate
										value="${photoPost.created_time}" pattern="dd-MM-yyyy HH:mm:ss" />
							</a></li>
						</c:forEach>
					</ul>
				</div>
				<div id="test5" class="col s12">
					<ul>
						<li>Who can see yours posts: ${postPrivacy}</li>
					</ul>
				</div>
			</div>
		</main>
	</layout:put>
</layout:extends>