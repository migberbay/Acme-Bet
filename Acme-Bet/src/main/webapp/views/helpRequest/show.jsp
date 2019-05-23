<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
	    
	    <div>
		    <b><spring:message code="category.englishName"/></b> <jstl:out value="${category.englishName}"   /> <br/> 
		    <b><spring:message code="category.spanishName"   /></b> <jstl:out value="${category.spanishName}"   /> <br/> 
		    <b><spring:message code="category.type" /></b> <jstl:out value="${category.type}" /> <br/>
		</div>
		
		<jstl:if test="${empty pools and empty requests}">
		<a href="category/admin/edit.do?categoryId=${category.id}"><spring:message code="category.edit"/></a>
		<br/>
		<a href="category/admin/delete.do?categoryId=${category.id}"><spring:message code="category.delete"/></a><br/>
		</jstl:if>
		<jstl:if test="${category.type == 'POOL' }">
			<h3><spring:message code="category.pools"/></h3>
			<spring:message code="pool.dateformat" var = "format"/>
			<display:table name="pools" id="row" requestURI="category/admin/show.do?categoryId=${category.id}" pagesize="5">

				<display:column property="title" titleKey="pool.title" />
				<display:column property="ticker" titleKey="pool.ticker" />
				<display:column titleKey="pool.range">
					<jstl:out value="${row.minRange}"/> - <jstl:out value="${row.maxRange}"/>
				</display:column>
				<display:column property="startDate" titleKey="pool.startDate">
					<fmt:formatDate pattern = "${format}" value = "${row.startDate}" />
				</display:column>
				<display:column property="endDate" titleKey="pool.endDate">
					<fmt:formatDate pattern = "${format}" value = "${row.endDate}" />
				</display:column>
				<display:column property="resultDate" titleKey="pool.resultDate" >
					<fmt:formatDate pattern = "${format}" value = "${row.resultDate}" />
				</display:column>
	
			</display:table>
		</jstl:if>
		
		
		<br/>
		<acme:cancel url="category/admin/list.do" code="category.back"/>
		
