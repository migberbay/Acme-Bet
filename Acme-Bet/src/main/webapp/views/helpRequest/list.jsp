<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

	<display:table name="categories" id="row" requestURI="category/admin/list.do" pagesize="5">
		
		<display:column>
			<a href="category/admin/show.do?categoryId=${row.id}"><spring:message code="category.show"/></a><br/>
		</display:column>
		<jstl:if test="${lan=='es'}">
			<display:column titleKey="category.name" property="spanishName" />
		</jstl:if>
		<jstl:if test="${lan=='en'}">
			<display:column titleKey="category.name" property="englishName" />
		</jstl:if>
		<display:column titleKey="category.type" property="type" />
		
	</display:table>

	<div>
	<a href="category/admin/create.do"> <spring:message code="category.create" /> </a>
	</div>

</security:authorize>
