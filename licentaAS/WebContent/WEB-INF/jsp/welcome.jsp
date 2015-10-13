<!DOCTYPE html>
<html>

<head>
    <title>SimSec</title>
	<meta charset="UTF-8">
    <link rel="stylesheet" href="css/welcome.css">
    <script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="js/welcome.js"></script>
</head>

<body>
    <div class="card">
        <h1>Header</h1>
    </div>
    <div class="card">
    	<h1><a id="next-button" href="#">Next</a></h1>
    </div>
    <div class="card">
  		<div id="active-slide-title" class="slide-title">Log in</div>
		<div class="slide-title right-slide-title">slide 2</div>
		<div class="slide-title right-slide-title">slide 3</div>
   		<div class="slide-title right-slide-title">slide 4</div>
    </div>
    <hr>
    <div class="card">
       	<div id="active-slide" class="slide">
			<a href="https://www.facebook.com/dialog/oauth?
	client_id=475736505939108&redirect_uri=http://localhost:8080&
	scope=email">
			   <img id="facebook-login" src="img/ryt6L.png" alt="facebook login">
			</a>
       	</div>
       	<div class="slide right-slide">slide 2</div>
       	<div class="slide right-slide">slide 3</div>
       	<div class="slide right-slide">slide 4</div>
    </div>
</body>

</html>
