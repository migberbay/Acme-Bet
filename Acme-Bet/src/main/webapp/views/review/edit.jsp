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

<form:form action="helpRequest/user/edit.do" modelAttribute="helpRequest">

	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<security:authorize access="hasRole('USER')">
	<acme:textarea code="helpRequest.description" path="description"/>
	<acme:textarea code="helpRequest.attachments" path="attachments"/>
	
	<jstl:if test="${lan=='es'}">
		<acme:select code="helpRequest.category" path="category" items="${categories}" itemLabel="spanishName"/>
	</jstl:if>
	<jstl:if test="${lan=='en'}">
		<acme:select code="helpRequest.category" path="category" items="${categories}" itemLabel="englishName"/>
	</jstl:if>
	
	<acme:select code="helpRequest.betPool" path="betPool" items="${pools}" itemLabel="title"/>
	
	<acme:submit name="save" code="helpRequest.save"/>
	<acme:cancel url="helpRequest/user/list.do" code="helpRequest.cancel"/>
	<br />	
	
	</security:authorize>

</form:form>