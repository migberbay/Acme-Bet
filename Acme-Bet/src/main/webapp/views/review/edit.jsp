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

<form:form action="review/user/edit.do" modelAttribute="review">

	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<security:authorize access="hasRole('USER')">
	<acme:textarea code="review.description" path="description"/>
	<acme:textarea code="review.attachments" path="attachments"/>

	<form:label path="score">
		<spring:message code="review.score" />
	</form:label>	
	<form:select path="score">
		<form:options items="${scores}" />
	</form:select>
	<form:errors path="score" cssClass="error" />
	<br/>
	
	<jstl:if test="${listrequest}">
	<acme:select code="review.counselor" path="counselor" items="${counselors}" itemLabel="userAccount.username"/>
	</jstl:if>
	<jstl:if test="${review.id!=0}">
		<acme:checkbox code="review.publish" path="isFinal"/>
	</jstl:if>
	<acme:submit name="save" code="review.save"/>
	<acme:cancel url="review/user/list.do" code="review.cancel"/>
	<br />	
	
	</security:authorize>

</form:form>