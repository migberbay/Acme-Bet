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

<security:authorize access="hasRole('COUNSELOR')">

<form:form action="curricula/personalRecord/edit.do" modelAttribute="personalRecord">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:textbox code="record.fullName" path="fullName"/>
	<acme:textbox code="record.email" path="email"/>
	<acme:textbox code="record.phone" path="phone"/>
	<acme:textbox code="record.photo" path="photo"/>
	<acme:textbox code="record.linkedInUrl" path="linkedInUrl"/>

	<acme:submit code="record.save" name="save"/>
	<acme:cancel url="curricula/counselor/show.do" code="curricula.cancel"/>
	<br/>

</form:form>
</security:authorize>