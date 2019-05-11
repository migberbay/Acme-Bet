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
	Recieves: Box box: box a editar.
			List<Box> boxes - todas las boxes del actor para elegir parent, salvo la que esta editando.
 -->

<form:form action="box/edit.do" modelAttribute="box">

	<form:hidden path="id" />

	<security:authorize access="isAuthenticated()">
	
	<acme:select items="${boxes}" itemLabel="name" code="box.parent" path="parentBox"/>
	
	<acme:textbox code="box.name" path="name"/>
	
	<acme:submit name="save" code="box.save"/>
				
	<acme:cancel url="box/list.do" code="box.cancel" />
	<br />
	
	</security:authorize>

</form:form>