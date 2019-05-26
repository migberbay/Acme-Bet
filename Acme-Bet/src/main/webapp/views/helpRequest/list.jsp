<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


	<display:table name="helpRequests" id="row" requestURI="helpRequest/user/list.do" pagesize="5">
		
		<display:column>
			<a href="helpRequest/show.do?helpRequestId=${row.id}"><spring:message code="helpRequest.show"/></a><br/>
			<security:authorize access="hasRole('USER')">
			<jstl:if test="${row.counselor==null}">
				<a href="helpRequest/user/edit.do?helpRequestId=${row.id}"><spring:message code="helpRequest.edit"/></a><br/>
				<a href="helpRequest/user/delete.do?helpRequestId=${row.id}"><spring:message code="helpRequest.delete"/></a><br/>
			</jstl:if>
			<jstl:if test="${row.counselor!=null and row.status=='PENDING'}">
				<a href="helpRequest/user/solve.do?helpRequestId=${row.id}"><spring:message code="helpRequest.solve"/></a><br/>
			</jstl:if>

			</security:authorize>
			<security:authorize access="hasRole('COUNSELOR')">
			<jstl:if test="${row.status=='OPEN'}">
				<a href="helpRequest/counselor/answer.do?helpRequestId=${row.id}"><spring:message code="helpRequest.answer"/></a><br/>
			</jstl:if>
			</security:authorize>
		</display:column>
		<display:column titleKey="helpRequest.ticker" property="ticker" />
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
		<security:authorize access="hasRole('USER')">
		<display:column titleKey="helpRequest.counselor" property="counselor.userAccount.username"/>
		</security:authorize>
		<security:authorize access="hasRole('COUNSELOR')">
		<display:column titleKey="helpRequest.user" property="user.userAccount.username"/>
		</security:authorize>
	</display:table>

<security:authorize access="hasRole('USER')">
	<div>
	<a href="helpRequest/user/create.do"> <spring:message code="helpRequest.create" /> </a>
	</div>
</security:authorize>