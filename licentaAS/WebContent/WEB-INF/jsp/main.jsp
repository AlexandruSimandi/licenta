<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <title>SimSec</title>
	<meta charset="UTF-8">
    <link rel="stylesheet" href="css/main.css">
    <script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="js/main.js"></script>
    <c:if test="${screenStatus == 'logged'}">  
		<script>
			$(window).ready(function(){
				$('#next-button').trigger('click');
				simulateLoad();
			});
		</script>
	</c:if>  
</head>

<body>
    <div class="card">
        <h1>Status: ${screenStatus}</h1>
    </div>

    <div class="card">
  		<div id="active-slide-title" class="slide-title">Log in</div>
		<div class="slide-title right-slide-title">Loading</div>
		<div class="slide-title right-slide-title">Results</div>
   		<div class="slide-title right-slide-title">Recommendations</div>
    </div>
	<hr>
    <div class="card">
       	<div id="active-slide" class="slide">
       		<p>This app will scan your facebook posts for potential threats to your personal and professional life.</p>
       		<p>Posts such as "I hate my boss", "I hate going to work, i am so sleepy.", "Going on a trip for a week, nobody will be home." will be detected as potentially dangerous and pointed out to the user in form of recommendations to either be deleted or hidden from public view.</p>
			<a href="https://www.facebook.com/dialog/oauth?
	client_id=475736505939108&redirect_uri=http://localhost:8080/loginSolver&
	scope=email">
			   <img id="facebook-login" src="img/ryt6L.png" alt="facebook login">
			</a>
       	</div>
       	<div class="slide right-slide">
       		<p>Your posts are being processed</p>
       		<div class="loadingBarContainer">
       			<div id="loadingBar"></div>
       		</div>
       		<p>The above bar is fake since the app does not yet have permission to read posts</p>
       	</div>
       	<div class="slide right-slide">
       		<p>These links are purely fictional</p>
       		<p>Here are the posts that might represent danger:</p>
       		<ul>
       			<li><a href="#">link1</a></li>
       			<li><a href="#">link2</a></li>
       			<li><a href="#">link3</a></li>
       		</ul>
       		<h3>
       			<a id="next-button" href="#">Continue with recommendations</a>
       		</h3>
       	</div>
       	<div class="slide right-slide">
       		<p>The recommendations are purely fictional for the moment</p>
       		<p>You should:</p>
       		<ul>
       			<li>Not post about going on a vacation, especially how long you will be gone. Thieves find this information very valuable and can take full advantage of it. Also post your photos after you come back from vacation.</li>
       			<li>Not create negative work related posts. Imagine your boss reading your facebook wall. Also employers are likely to check your facebook account.</li>
       		</ul>
       		<p>Thank you very much!</p>
       		<h3><a href="/">Back to login</a></h3>
       	</div>
    </div>
</body>

</html>
