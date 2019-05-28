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

<form:form action="warranty/admin/edit.do" modelAttribute="warranty">
	
	<security:authorize access="hasRole('ADMIN')">
				
		<acme:textbox code="warranty.title" path="title"/>
		<br />	
		<acme:textbox code="warranty.terms" path="terms" placeholder="term1,term2, term3"/>
		<br />
		<acme:textbox code="warranty.laws" path="laws"/>
		<br />
		<jstl:if test="${create == false}">
			<acme:checkbox code="warranty.isFinal" path="isFinal"/>
			<br />
		</jstl:if>
		
		<acme:submit name="save" code="warranty.save"/>
		<acme:cancel url="warranty/admin/list.do" code="warranty.cancel"/>
		<br />
		
	</security:authorize>

</form:form>