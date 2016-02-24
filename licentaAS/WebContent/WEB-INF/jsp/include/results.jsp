<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="slide right-slide">
	<!--    		<p>Here are the posts that might contain foul language:</p>
       		<ul>
       			<c:forEach items="${dangerousPostList}" var="post" varStatus="loop">
       				<li><a title="${post.message}" href="${post.actions[0].link}">${post.message} - <fmt:formatDate value="${post.createdTime}" pattern="dd-MM-yyyy HH:mm:ss" /></a></li>      			
       			</c:forEach>
       		</ul>
     -->
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

	<div class="next-button-container pull-right">
		<a class="next-button" href="#"><span
			class="glyphicon glyphicon-arrow-right"></span></a>
	</div>
</div>