<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>

<layout:extends name="base">
    <layout:put block="header" type="REPLACE"></layout:put>
    <layout:put block="contents">
		<main>
			<div class="container">
				<h3>Hello ${user.first_name}</h3>
				<c:if test="${user.analyzed == false}">
					<h4>You never analyzed your account</h4>
				</c:if>
				<c:if test="${user.analyzed == true}">
					<h5>You analyzed this account last time on <fmt:formatDate value="${user.last_analysis}" pattern="HH:mm:ss dd-MM-yyyy" /></h5>
				</c:if>
				<a class="waves-effect waves-orange btn-large indigo darken-4" href="/analyse">Analyze</a>
				<a class="waves-effect waves-orange btn-large indigo darken-4" href="/logout">Logout</a>
			</div>
		</main>
	</layout:put>
</layout:extends>