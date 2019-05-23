<%--
 * edit.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="bet/user/edit.do" modelAttribute="form">

	<security:authorize access="hasRole('USER')">
	<form:hidden path="betPoolId"/>
	
	<acme:textbox code="bet.amount" path="amount"/>
	<br/>	
	<form:label path="winner">
		<spring:message code="bet.winner" />
	</form:label>	
	
	<form:select path="winner">
		<jstl:forEach items="${participants}" var="x">
			<form:option value="${x}" label="${x}" />	
		</jstl:forEach>	
	</form:select>
	<form:errors path="winner" cssClass="error" />
	<br>
	<div class="error">
	<jstl:if test="${hasEnoughFunds == false}">
		Not enough funds punk, go get some money!
	</jstl:if>
	<br>
	<jstl:if test="${betAmountIsCorrect == false}">
		The range in this bet is <jstl:out value="${range}"/>, you either exceeded it or you went too low.
	</jstl:if>
	</div>
	
	
	<br/>
	<button type="submit" name="save" onclick="confirmLeave();" class="btn btn-primary">
		<spring:message code="bet.save" />
	</button>
	<acme:cancel url="betPool/list.do" code="bet.back"/>
	<br />	
	</security:authorize>

</form:form>

<script>
	function confirmLeave() {
	if(!confirm("This operation is not reversible and funds will be substracted from your account, you're sure you want to proceed?")) return;
	}
</script>