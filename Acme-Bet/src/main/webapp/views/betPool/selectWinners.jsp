<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="betPool/bookmaker/selectWinners.do" modelAttribute="form">

	<security:authorize access="hasRole('BOOKMAKER')">
	
	<form:hidden path="betPoolId"/>
	
	Winners: 
	<form:select path="winners">
		<jstl:forEach items="${possibles}" var="x">
			<form:option value="${x}"/>
		</jstl:forEach>
	</form:select>
	<div class="tooltip"><b>?</b>
  		<span class="tooltiptext">
  			NO CONTEST: the event didnt happen and the funds will be returned to the users.
  			TIE: the contestants tied, the bets will be equally distributed.
			note: No contest has priority over tie and tie has it over all the other options, be carefull when selecting them.
  		</span>
	</div>
	
	<jstl:if test="${isEmpty}">
		<div class="error">You must select at least one element!</div>
	</jstl:if>
	
	<br/>
	<acme:submit name="save" code="pool.save"/>
	
	<acme:cancel url="betPool/bookmaker/list.do" code="pool.back"/>
	<br />	
	</security:authorize>

</form:form>
	