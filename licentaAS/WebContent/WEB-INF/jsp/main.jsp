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
</head>

<body>
    <div class="material-header">
		<h1>SimSec</h1>
    </div>
    <div class="material-progress">
  		<div id="active-slide-title" class="slide-title">Log in</div>
		<div class="slide-title right-slide-title">Loading</div>
		<div class="slide-title right-slide-title">Results</div>
   		<div class="slide-title right-slide-title">Recommendations</div>
    </div>
       	<div id="active-slide" class="slide">
       		<p>This app will scan your facebook posts for potential threats to your personal and professional life.</p>
       		<p>Posts such as "I hate my boss", "I hate going to work, i am so sleepy.", "Going on a trip for a week, nobody will be home." will be detected as potentially dangerous and pointed out to the user in form of recommendations to either be deleted or hidden from public view.</p>
			<a href="https://www.facebook.com/dialog/oauth?
	client_id=475736505939108&redirect_uri=http://localhost/loginSolver&
	scope=user_posts">
			   <img id="facebook-login" src="/img/ryt6L.png" alt="facebook login">
			</a>
       	</div>
       	<div class="slide right-slide">
       		<h2 class="slide-header">Your posts are being processed</h2>
			<div class="loading-container">
				<div class="circle circle1">
					<div class="circle circle2">
						<div class="circle circle3">
							<div class="circle circle4"></div>
						</div>
					</div>			
				</div>
			</div>
       	</div>
       	<div class="slide right-slide">
    <!--    		<p>Here are the posts that might contain foul language:</p>
       		<ul>
       			<c:forEach items="${dangerousPostList}" var="post" varStatus="loop">
       				<li><a title="${post.message}" href="${post.actions[0].link}">${post.message} - <fmt:formatDate value="${post.createdTime}" pattern="dd-MM-yyyy HH:mm:ss" /></a></li>      			
       			</c:forEach>
       		</ul>
     -->
       		<p>Here are the posts that might affect professional work life:</p>
       		<ul>
       			<c:forEach items="${workThreatList}" var="workPost" varStatus="workLoop">
       				<li title="${workPost.message}">${workPost.message} - <fmt:formatDate value="${workPost.createdTime}" pattern="dd-MM-yyyy HH:mm:ss" /></li>
				</c:forEach>		
       		</ul>
       		<p>Privacy settings:</p>
       		<ul>
       			<li>
       				Who can see yours posts: ${postPrivacy}
       			</li>
       		</ul>
			
       		<div class="next-button-container pull-right">
       			<a id="next-button" href="#"><span class="glyphicon glyphicon-arrow-right"></span></a>
       		</div>
       	</div>
       	<div class="slide right-slide">
       		<p>The recommendations are purely fictional for the moment</p>
       		<p>You should:</p>
       		<ul>
       			<li>Not post about going on a vacation, especially how long you will be gone. Thieves find this information very valuable and can take full advantage of it. Also post your photos after you come back from vacation.</li>
       			<li>Not create negative work related posts. Imagine your boss reading your facebook wall. Also employers are likely to check your facebook account.</li>
       		</ul>
       		<p>Thank you very much!</p>
       		<div class="next-button-container pull-right return-button">
       			<a id="next-button" href="/"><span class="glyphicon glyphicon-repeat"></span></a>
       		</div>
       	</div>
</body>

</html>
