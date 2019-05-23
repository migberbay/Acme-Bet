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

<form:form action="betPool/bookmaker/edit.do" modelAttribute="form">

	<security:authorize access="hasRole('BOOKMAKER')">
	
	<form:hidden path="id"/>
	
	<acme:select items="${warranties}" itemLabel="title" code="pool.warranty" path="warranty"/>	
	<acme:select items="${categories}" itemLabel="englishName" code="pool.category" path="category"/>	
		
	<acme:textbox code="pool.title" path="title"/>
	<acme:textbox code="pool.description" path="description"/>
	<acme:textbox code="pool.participants" path="participants" placeholder="participant1 , participant2 , ..."/>
	
	<spring:message code = "pool.startDate"/>: 
	<input type= "datetime-local" name="startDate">
	<div class="tooltip"><b>?</b>
  		<span class="tooltiptext">This is when you allow betting</span>
	</div>
	<form:errors path="startDate" cssClass="error" /><br>
	
	
	<spring:message code = "pool.endDate"/>: 
	<input type= "datetime-local" name="endDate">
	<div class="tooltip"><b>?</b>
  		<span class="tooltiptext">This is when you end the betting period (shoud be before the event)</span>
	</div>
	<form:errors path="endDate" cssClass="error" /><br>
	
	<spring:message code = "pool.resultDate"/>: 
	<input type= "datetime-local" name="resultDate">
	<div class="tooltip"><b>?</b>
  		<span class="tooltiptext">This is when you will publish the results</span>
	</div>
	<form:errors path="resultDate" cssClass="error" /><br>
	
	Final:<form:checkbox path="isFinal"/><br/>
	
	<br/>
	<button type="submit" name="save" onclick="confirmLeave();" class="btn btn-primary">
		<spring:message code="pool.save" />
	</button>
	
	<acme:cancel url="betPool/list.do" code="pool.back"/>
	<br />	
	</security:authorize>

</form:form>
	