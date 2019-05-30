<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<jstl:if test="${moreReviews eq false}">
		<b style="color:red"><spring:message code="more.requests.needed" /></b>
	</jstl:if>

	<display:table name="reviews" id="row" requestURI="review/user/list.do" pagesize="5">
		
		<display:column>
			<a href="review/show.do?reviewId=${row.id}"><spring:message code="review.show"/></a><br/>
		</display:column>
		<display:column titleKey="review.description" property="description" />
		<spring:message code="review.moment.format" var="formatMoment"/>
		<display:column titleKey="review.moment" property="moment" format="{0,date,${formatMoment} }"/>
		<display:column titleKey="review.score" property="score"/>
		<display:column titleKey="review.counselor">
			<a href="actor/show.do?actorId=${row.counselor.id}"><jstl:out value="${row.counselor.userAccount.username}"/></a>
		</display:column>
	</display:table>
	<br/>
	<jstl:if test="${moreReviews}">
		<a href="review/user/create.do"> <spring:message code="review.create" /> </a>
	</jstl:if>

