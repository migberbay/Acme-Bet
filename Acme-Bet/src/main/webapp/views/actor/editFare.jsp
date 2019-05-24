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

<form:form action="actor/editFare.do" modelAttribute="actor">
	<form:hidden path="id"/>

	<acme:textbox code="actor.fare" path="fare"/> <br/>
	
	<div class = "error">
	<jstl:if test="${valueTooHigh}">
		fare must be a number between 0-2, your personal maximum is: <jstl:out value="${max}"/>
	</jstl:if>
	
	<jstl:if test="${changed}">Value changed successfully</jstl:if>
	</div>
	

	
	<acme:submit name="save" code="actor.save"/>
	<acme:cancel url="actor/show.do" code="actor.cancel"/>
	
</form:form>
