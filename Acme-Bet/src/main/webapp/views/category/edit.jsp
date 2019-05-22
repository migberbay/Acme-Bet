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

<form:form action="category/admin/edit.do" modelAttribute="category">

	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<security:authorize access="hasRole('ADMIN')">
	
	<acme:textbox code="category.englishName" path="englishName"/>
	
	<acme:textbox code="category.spanishName" path="spanishName"/>

	<form:label path="type">
		<spring:message code="category.type" />
	</form:label>	
	<form:select path="type">
		<form:option value="0" label="----" />		
		<form:options items="${types}" />
	</form:select>
	<form:errors path="type" cssClass="error" />
	<br/>
	
	<acme:submit name="save" code="category.save"/>
	<acme:cancel url="category/admin/list.do" code="category.cancel"/>
	<br />	
	
	</security:authorize>

</form:form>