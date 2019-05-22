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

 <spring:message code="pool.dateformat" var = "format"/>
	
	<b><spring:message code="pool.ticker"/>:</b> <jstl:out value="${betPool.ticker}"/><br>
	<b><spring:message code="pool.title"/>:</b> <jstl:out value="${betPool.title}"/><br>
	<b><spring:message code="pool.range"/>:</b> <jstl:out value="${betPool.minRange}"/> - <jstl:out value="${betPool.maxRange}"/><br>
	<b><spring:message code="pool.description"/>:</b> <jstl:out value="${betPool.description}"/><br>
	<b><spring:message code="pool.startDate"/>:</b> <fmt:formatDate pattern = "${format}" value = "${betPool.startDate}" /><br>
	<b><spring:message code="pool.endDate"/>:</b> <fmt:formatDate pattern = "${format}" value = "${betPool.endDate}" /><br>
	<b><spring:message code="pool.resultDate"/>:</b> <fmt:formatDate pattern = "${format}" value = "${betPool.resultDate}" /><br>
	<display:table name="betPool.participants" id="row" requestURI="betPool/show.do" pagesize="5">
		<display:column titleKey="pool.participants">
			<jstl:out value="${row}"></jstl:out>
		</display:column>
	</display:table>
	
	<display:table name="betPool.winners" id="row" requestURI="betPool/show.do" pagesize="5">
		<display:column titleKey="pool.winners">
			<jstl:out value="${row}"></jstl:out>
		</display:column>
	</display:table>
	
	<display:table name="betPool.bets" id="row" requestURI="betPool/show.do" pagesize="5">
		<display:column titleKey="pool.user" property="user.userAccount.username"/>
		<display:column titleKey="pool.amount" property="amount"/>
		<display:column titleKey="pool.moment" property="moment"/>
		
	</display:table>

	
	
	<input type="button" name="back"
		value="<spring:message code="pool.back" />"
		onclick="javascript: window.location.replace('/Acme-Bet/betPool/list.do')" />
	<br />
