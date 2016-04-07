<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>

<layout:extends name="base">
    <layout:put block="header" type="REPLACE"></layout:put>
    <layout:put block="contents">
		<main>
			<div class="container">
				<div class="section">
					<div class="divider"></div>
					<h5 class="caption">This app will scan your facebook posts for potential threats to your personal and professional life.
	
	Posts such as "I hate my boss", "I hate going to work, i am so sleepy.", "Going on a trip for a week, nobody will be home." will be detected as potentially dangerous and pointed out to the user in form of recommendations to either be deleted or hidden from public view.</h5>
					<div class="divider"></div>
					<div class="row"></div>
					<a class="waves-effect waves-orange btn-large indigo darken-4" href="https://www.facebook.com/dialog/oauth?
		client_id=475736505939108&redirect_uri=http://localhost/loginSolver&
		scope=user_posts,user_photos"><i class="material-icons left">perm_identity</i>Login</a>
				</div>
			</div>
		</main>
	</layout:put>
    
</layout:extends>