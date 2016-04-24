<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>SimSec</title>
		<link rel="stylesheet" href="css/materialize.min.css">
		<link rel="stylesheet" href="css/index.css">
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		<script src="js/jquery-2.2.2.js"></script>
		<script src="js/materialize.min.js"></script>
		<script src="js/index.js"></script>
	</head>
    		<header>
			<nav class="indigo darken-4 top-nav">
				<ul id="nav-mobile" class="side-nav fixed z-depth-1">
	<!--				maybe throw a toast telling already logged in or modal to relog-->
					<c:if test="${user == null}">
						<li class="${screenStatus == 'login' ? 'active' : ''}"><a class="waves-effect waves-orange ${screenStatus == 'login' ? 'white-text' : ''}" href="login"><i class="material-icons left">perm_identity</i>Login</a></li>
					</c:if>
					<c:if test="${user != null}">
						<li class="${screenStatus == 'home' ? 'active' : ''}"><a class="waves-effect waves-orange ${screenStatus == 'home' ? 'white-text' : ''}" href="home"><i class="material-icons left">perm_identity</i>Home</a></li>
					</c:if>
					<li class="${screenStatus == 'results' ? 'active' : ''}"><a class="waves-effect waves-orange ${screenStatus == 'results' ? 'white-text' : ''}" href="loading"><i class="material-icons left">assignment</i>Results</a></li>
					<li class="${screenStatus == 'recommendations' ? 'active' : ''}"><a class="waves-effect waves-orange ${screenStatus == 'recommendations' ? 'white-text' : ''}" href="#!"><i class="material-icons left">assignment_late</i>Recommendations</a></li>
				</ul>
				<div class="nav-wrapper">
					<div class="container">
						<a href="#" data-activates="nav-mobile" class="button-collapse top-nav full hide-on-large-only"><i class="mdi-navigation-menu"></i></a>
						<a href="#" class="brand-logo center">
							<h3>SimSec</h3>
						</a>
					</div>
				</div>
			</nav>
		</header>
    <layout:block name="contents"></layout:block>

<div class="footer">
<!--     <hr />
    <a href="https://github.com/kwon37xi/jsp-template-inheritance">jsp template inheritance example</a> -->
</div>
</html>