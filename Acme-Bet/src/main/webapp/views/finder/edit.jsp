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

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<!-- PARAMETERS FROM CONTROLLER: finder: Finder, finder a editar
									 betPools: Collection<BetPool>
					 				 -->
									 

<form:form action="finder/user/filter.do" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="moment" />
    <form:hidden path="user" />
	
	
	<security:authorize access="hasRole('USER')">
	
	<acme:textbox code="finder.keyword" path="keyword"/>
	<acme:textbox code="finder.minRange" path="minRange"/>
	<acme:textbox code="finder.maxRange" path="maxRange"/>
	<acme:textbox code="finder.openingDate" path="openingDate" placeholder="01/02/2010 12:00"/>
	<acme:textbox code="finder.endDate" path="endDate" placeholder="01/02/2010 12:00"/>
	<jstl:if test="${lan=='es'}">
		<acme:select code="finder.category" path="category" items ="${categories}" itemLabel="spanishName"/>
	</jstl:if>
	<jstl:if test="${lan=='en'}">
		<acme:select code="finder.category" path="category" items ="${categories}" itemLabel="englishName"/>
	</jstl:if>
	
	<acme:submit name="filter" code="finder.search" />
	<acme:submit name="clear" code="finder.clear" />
	</security:authorize>

</form:form>
<br/>
<jstl:if test="${cachedMessage != null}">
	<p style="color: #3a3a3a"><spring:message code="${cachedMessage}" /></p>
	<p style="color: #3a3a3a"><spring:message code="finder.cachedMoment"/>
		<jstl:out value="${finder.moment}"/></p>
	<br/>
</jstl:if>
	
<spring:message code="pool.dateformat" var = "format"/>
	<display:table name="betPools" id="row" requestURI="${requestURI}" pagesize="5">
		<display:column titleKey="pool.action" >
			<a href="betPool/show.do?betPoolId=${row.id}">show</a><br>			
		</display:column>
		<display:column property="title" titleKey="pool.title" />
		<display:column property="ticker" titleKey="pool.ticker" />
		<display:column titleKey="pool.range">
		<jstl:out value="${row.minRange}"/> - <jstl:out value="${row.maxRange}"/>
		</display:column>
		<display:column titleKey="pool.startDate" property="startDate" format="{0,date,${format} }"/>
		<display:column titleKey="pool.endDate" property="endDate" format="{0,date,${format} }"/>
		<display:column titleKey="pool.resultDate" property="resultDate" format="{0,date,${format} }"/>
	
	</display:table>

