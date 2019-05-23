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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="actor/editFunds.do" modelAttribute="actor">

	<acme:textbox code="actor.funds" path="funds"/> <br/>
	
	<div class = "error">
	<jstl:if test="${incorrectFunds}">
		funds must be a number between 0-1000 $.
	</jstl:if>
	<jstl:if test="${error}">
		an unexpected error ocurred
	</jstl:if>
	</div>
	
	
	<jstl:if test="${isUser}">
		<acme:submit name="saveUser" code="actor.save"/>
	</jstl:if>
	
	<jstl:if test="${isSponsor}">
		<acme:submit name="saveSponsor" code="actor.save"/>
	</jstl:if>
	
	<acme:cancel url="actor/show.do" code="actor.cancel"/>
	
</form:form>
