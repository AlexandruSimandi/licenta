<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="slide right-slide">
	<p>The recommendations are generated based on the results</p>
	<p>You should:</p>
	<ul>
		<c:if test="${hasHolidayThreats}">
			<li>Not post about going on a vacation, especially how long you
				will be gone. Thieves find this information very valuable and can
				take full advantage of it. Also post your photos after you come back
				from vacation.</li>
		</c:if>
		<c:if test="${hasWorkThreats}">
			<li>Not create negative work related posts. Imagine your boss
				reading your facebook wall. Also employers are likely to check your
				facebook account before they call you in for an interview.</li>
		</c:if>
	</ul>
	<p>Thank you very much!</p>
	<div class="next-button-container pull-right return-button">
		<a class="next-button" href="/">
			<span class="glyphicon glyphicon-repeat"></span>
		</a>
	</div>
</div>