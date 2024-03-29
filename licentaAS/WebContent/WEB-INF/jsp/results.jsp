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
			<fmt:parseNumber var="ONE" value="1" />
			<c:forEach items="${postsWithLocation}" var="locationPost">
				markerEntity = new Array();
				id = '${locationPost.snd} times';
				longitude = ${locationPost.fst[0].longitude};
				latitude = ${locationPost.fst[0].latitude};
				markerEntity.push(id);
				markerEntity.push(latitude);
				markerEntity.push(longitude);
				markers.push(markerEntity);
				
				
				infoEntity = new Array();
				infoEntityString = '<div class="info_content" style="width: 150px; overflow-x: hidden;">' +
										<fmt:parseNumber var="locationCount" value="${locationPost.snd}" />
										<c:if test="${locationCount == ONE}">
											'<h5 class="">One time</h5><ul><li><div style="max-height: 100px;">'
										</c:if>
										<c:if test="${locationCount > ONE}">
											'<h5 class="">${locationPost.snd} times</h5><ul><li><div style="max-height: 100px;">'
										</c:if>
										<c:forEach items="${locationPost.fst}" var="post">+ "<a href=\"${post.link}\" target=\"_blank\"><fmt:formatDate value="${post.created_time}"
										pattern="dd-MM-yyyy HH:mm:ss" /></a><br>"</c:forEach>
										
									+ '</div></li></ul></div';
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
			<li class="tab col s3"><a class="active black-text"
				href="#test2"><i class="material-icons">place</i>Locations</a></li>
			<li class="tab col s3"><a class="black-text" href="#test1"><i class="material-icons">poll</i> Posts graph</a></li>
			<li class="tab col s3"><a class="black-text" href="#test3"><i class="material-icons">warning</i> Dangerous
					posts</a></li>
			<li class="tab col s3"><a class="black-text" href="#test4"><i class="material-icons">photo</i> Public
					Photos</a></li>
			<li class="tab col s3"><a class="black-text" href="#test5"><i class="material-icons">visibility_off</i> Privacy
					Settings</a></li>
			<li class="tab col s3"><a class="black-text" href="#test6"><i class="material-icons">local_airport</i> Holiday warning
					</a></li>						
		</ul>
			<div class="row">
				<div class="col s12 container"></div>
				<div id="test1" class="col s12">
					<jsp:include page="include/results/stats.jsp"/>
				</div>
				<div id="test2" class="col s12">
					<c:if test="${empty postsWithLocation}">
						<h5 class="center">None of your posts have location</h5>
					</c:if>
					<c:if test="${! empty postsWithLocation}">
						<div id="map_wrapper">
							<div id="map_canvas" class="mapping"></div>
						</div>
					</c:if>
				</div>
				<div id="test3" class="col s12">
					<c:if test="${! empty workThreatList}">
					<h5 class="center">These are the posts that might affect your professional life</h5>
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
					</c:if>
					<c:if test="${empty workThreatList}">
						<h5 class="center">There is no dangerous post detected</h5>
					</c:if>
				</div>
				<div id="test4" class="col s12">
				<c:if test="${! empty photoPostList}">
					<h5 class="center">These are the photos that can be seen by anyone on facebook</h5>
					<table class="highlight">
						<thead>
							<tr>
								<th data-field="id">Date</th>
								<th data-field="name">Description</th>
							</tr>
						</thead>
	
						<tbody>
							<c:forEach items="${photoPostList}" var="photoPost">
								<tr>
									<td><a target="_blank"
									href="https://www.facebook.com/${user.id}/posts/${photoPost.object_id}"><fmt:formatDate
											value="${photoPost.created_time}" pattern="dd-MM-yyyy HH:mm:ss" />
									</a></td>
									<td>${photoPost.message == null ? photoPost.story : photoPost.message}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty photoPostList}">
					<h5 class="center">You have no public photos, that is excellent!</h5>
				</c:if>
				</div>
				<div id="test5" class="col s12">
					<h5 class="center">Your default privacy setting for new posts is: ${postPrivacy}</h5>
					<div id="privacyChart"></div>
					<script>
						var privacyText = new Array();
						<c:forEach items="${privacyCount}" var="privacyPair">
							privacyText.push("${privacyPair.fst}".replace(/_/g,' ').toLowerCase());
						</c:forEach>
						var privacyChart = c3.generate({
						    bindto: '#privacyChart',
						    size: {
						    	//height: 600
						    	height: $(window).height() - 220
						    },
						    data: {
						        // iris data from R
						        columns: [
										<c:forEach items="${privacyCount}" var="privacyPair" varStatus="loop">
											[privacyText[${loop.index}], "${privacyPair.snd}"],
										</c:forEach>
										[]
						        ],
						        type : 'pie',
						    }
						});
					</script>
				</div>
				<div id="test6" class="col s12">
					<c:if test="${! empty groupedPostsByHoliday}">
						<h5 class="center">These are all the posts considered to be created when you were not home</h5>
						<p class="center">Showing on facebook that you are not home can attract thieves</p>
						<ul class="collapsible popout" data-collapsible="accordion">
						<c:forEach items="${groupedPostsByHoliday}" var="holidayPlace" varStatus="loop">
							<li>
					    		<div id="cluster${loop.index}" class="collapsible-header">${holidayPlace.snd}</div>
							    <script>
							      	$.get('http://nominatim.openstreetmap.org/reverse?lat=${holidayPlace.fst[0].latitude}&lon=${holidayPlace.fst[0].longitude}', function(data){
							      		var $xml = $(data);
			  							var $city = $xml.find("city");
			  							if($city.length == 0){
			  								$city = $xml.find("town");
			  							}
			  							console.log($city);
			  							var times${holidayPlace.snd} = '${holidayPlace.snd}';
			  							if(times${holidayPlace.snd} > 1){
			  								$('#cluster${loop.index}').html($city.text() + " - ${holidayPlace.snd} times");	
			  							} else {
			  								$('#cluster${loop.index}').html($city.text() + " - one time");
			  							}
							      		
							      	});
							    </script>
					      		<div class="collapsible-body">
							      	<c:forEach items="${holidayPlace.fst}" var="place">
							      		<p style="padding-top: 8px; padding-bottom: 8px;"><a href="${place.link}" target="_blank">${place.story}</a></p>
							      	</c:forEach>
					      		</div>
					     	</li>
						</c:forEach>
					  </ul>
					</c:if>
					<c:if test="${empty groupedPostsByHoliday}">
						<h5 class="center">There are no posts showing that you were away from home</h5>
						<p class="center">Showing on facebook that you are away from home can attract thieves</p>
					</c:if>
				</div>
			</div>
		</main>
	</layout:put>
</layout:extends>