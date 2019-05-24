<%--
 * edit.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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

<!-- 
	Recibe: Message userMessage: el mensaje a editar.
			List<Actor> actors: todos los actores del sistema.
			boolean isBroadcast: indica si el mensaje a crear es de braodcast o no.
 -->

<form:form action="helpRequest/counselor/answer.do" modelAttribute="m">

	<security:authorize access="isAuthenticated()">
	
	<acme:textbox code="helpRequest.m.subject" path="subject"/>
	
	<acme:textarea code="helpRequest.m.body" path="body"/>
	
	<acme:submit name="save" code="helpRequest.save"/>
	<acme:cancel url="helpRequest/counselor/list.do" code="helpRequest.cancel"/>
	<br />
	
	</security:authorize>

</form:form>