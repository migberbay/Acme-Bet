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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- 

-->
	<jstl:if test="${rejectReasonEmpty}">
		<div class="error">you must provide a valid reason for a rejection.</div>
	</jstl:if>
	
	
	<display:table name="petitions" id="row" requestURI="petition/bookmaker/list.do" pagesize="5">
		<display:column titleKey="petition.action" class ="${row.status}">
		<jstl:if test='${row.status.equals("PENDING")}'>
			<form:form action="petition/bookmaker/list.do" modelAttribute="petition">
				<form:hidden path="id" value="${row.id}"/>
				<acme:submit name="accept" code="petition.accept"/>
			</form:form>
			<br>
			<form:form action="petition/bookmaker/list.do" modelAttribute="petition">
				<form:hidden path="id" value="${row.id}"/>
				<acme:textbox code="petition.reason" path="rejectReason"/>
				<acme:submit name="reject" code="petition.reject"/>
			</form:form>
		</jstl:if>	
		</display:column>
		
		<display:column property="status" titleKey="petition.status" class ="${row.status}" />
		<display:column property="bet.amount" titleKey="petition.amount"  class ="${row.status}"/>
		<display:column property="user.userAccount.username" titleKey="petition.user" class ="${row.status}" />
		<display:column property="bet.betPool.title" titleKey="petition.betPool"  class ="${row.status}"/>
	</display:table>
	
	<input type="button" name="back"
		value="<spring:message code="petition.back" />"
		onclick="javascript: window.location.replace('/Acme-Bet')" />
	<br/>
<script>



</script>