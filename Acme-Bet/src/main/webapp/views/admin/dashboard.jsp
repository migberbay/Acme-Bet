<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

		<spring:message code="admin.datatable"/>
	<table style="width:'100%' border='0' align='center' ">
			<tr>
				<th><spring:message code="admin.type"/></th>
				<th><spring:message code="admin.betsPerBetPool"/></th>
				<th><spring:message code="admin.betsPerUser"/></th>
				<th><spring:message code="admin.helpRequestsPerUser"/></th>
				<th><spring:message code="admin.reviewsPerUser"/></th>
				<th><spring:message code="admin.sponsorshipsPerSponsor"/></th>
			</tr>
			<tr>
				<td><spring:message code="admin.average"/></td>
				<td><jstl:out value="${avgBetsPerBetPool}"/></td>
				<td><jstl:out value="${avgBetsPerUser}"/></td>
				<td><jstl:out value="${avgHelpRequestsPerUser}"/></td>		
				<td><jstl:out value="${avgReviewsPerUser}"/></td>	
				<td><jstl:out value="${avgSponsorshipsPerSponsor}"/></td>	
			</tr>
			<tr>
				<td><spring:message code="admin.minimum"/></td>
				<td><jstl:out value="${minBetsPerBetPool}"/></td>
				<td><jstl:out value="${minBetsPerUser}"/></td>
				<td><jstl:out value="${minHelpRequestsPerUser}"/></td>	
				<td><jstl:out value="${minReviewsPerUser}"/></td>	
				<td><jstl:out value="${minSponsorshipsPerSponsor}"/></td>	
			</tr>	
			<tr>
				<td><spring:message code="admin.maximum"/></td>
				<td><jstl:out value="${maxBetsPerBetPool}"/></td>
				<td><jstl:out value="${maxBetsPerUser}"/></td>
				<td><jstl:out value="${maxHelpRequestsPerUser}"/></td>	
				<td><jstl:out value="${maxReviewsPerUser}"/></td>	
				<td><jstl:out value="${maxSponsorshipsPerSponsor}"/></td>	
			</tr>
			<tr>
				<td><spring:message code="admin.stdv"/></td>
				<td><jstl:out value="${stdevBetsPerBetPool}"/></td>
				<td><jstl:out value="${stdevBetsPerUser}"/></td>
				<td><jstl:out value="${stdevHelpRequestsPerUser}"/></td>	
				<td><jstl:out value="${stdevReviewsPerUser}"/></td>	
				<td><jstl:out value="${stdevSponsorshipsPerSponsor}"/></td>	
			</tr>
	</table>
	


	<b><spring:message code="admin.maxBetPoolsBookmakers"/></b>
	<jstl:if test="${empty maxBetPoolsBookmakers}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${maxBetPoolsBookmakers}">
		<tr>
			<td><jstl:out value="${i.name}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)  </td>
		</tr>			
		</jstl:forEach>
	</table>
	
	<b><spring:message code="admin.maxBetsUsers"/></b>
	<jstl:if test="${empty maxBetsUsers}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${maxBetsUsers}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surnames}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>
	</table>
	
	<b><spring:message code="admin.maxRequestsUsers"/></b>
	<jstl:if test="${empty maxRequestsUsers}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${maxRequestsUsers}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surnames}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>
	</table>
	
	<b><spring:message code="admin.maxRequestsUsers"/></b>
	<jstl:if test="${empty maxRequestsUsers}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${maxRequestsUsers}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surnames}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>
	</table>
	
	<b><spring:message code="admin.highestAvgScoreCounselor"/></b>
	<jstl:if test="${highestAvgScoreCounselor==null}"><spring:message code="admin.empty"/></jstl:if>
	<table style="width:'100%' border='0' align='center' ">
		<tr>
			<td><jstl:out value="${highestAvgScoreCounselor.name}"/> <jstl:out value="${highestAvgScoreCounselor.surnames}"/> (<a href="actor/show.do?actorId=${highestAvgScoreCounselor.id}"><jstl:out value="${highestAvgScoreCounselor.userAccount.username}"/>)</a></td>
		</tr>			
	</table>
	
	<b><spring:message code="admin.topInActivatedSponsorships"/></b>
	<jstl:if test="${empty topInActivatedSponsorships}"><spring:message code="admin.empty"/></jstl:if>	
	<table style="width:'100%' border='0' align='center' ">
		<jstl:forEach var="i" items="${topInActivatedSponsorships}">
		<tr>
			<td><jstl:out value="${i.name}"/> <jstl:out value="${i.surnames}"/> (<a href="actor/show.do?actorId=${i.id}"><jstl:out value="${i.userAccount.username}"/></a>)</td>
		</tr>			
		</jstl:forEach>		
	</table>
</security:authorize>

