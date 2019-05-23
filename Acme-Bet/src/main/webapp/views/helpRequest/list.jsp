<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('USER')">

	<display:table name="helpRequests" id="row" requestURI="helpRequest/user/list.do" pagesize="5">
		
		<display:column>
			<a href="helpRequest/user/show.do?helpRequestId=${row.id}"><spring:message code="helpRequest.show"/></a><br/>
			<jstl:if test="${row.counselor==null}">
				<a href="helpRequest/user/edit.do?helpRequestId=${row.id}"><spring:message code="helpRequest.edit"/></a><br/>
				<a href="helpRequest/user/delete.do?helpRequestId=${row.id}"><spring:message code="helpRequest.delete"/></a><br/>
			</jstl:if>
		</display:column>
		<jstl:if test="${lan=='es'}">
			<display:column titleKey="helpRequest.category" property="category.spanishName" />
		</jstl:if>
		<jstl:if test="${lan=='en'}">
			<display:column titleKey="helpRequest.category" property="category.englishName" />
		</jstl:if>
		
		<spring:message code="helpRequest.moment.format" var="formatMoment"/>
		<display:column titleKey="helpRequest.moment" property="moment" format="{0,date,${formatMoment} }"/>
		<display:column titleKey="helpRequest.status" property="status"/>
		<display:column titleKey="helpRequest.betPool" property="betPool.ticker"/>
		<display:column titleKey="helpRequest.counselor" property="counselor.userAccount.username"/>
	</display:table>

	<div>
	<a href="helpRequest/user/create.do"> <spring:message code="helpRequest.create" /> </a>
	</div>

</security:authorize>
