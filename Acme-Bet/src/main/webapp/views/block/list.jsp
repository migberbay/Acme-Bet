<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('USER')">
<display:table name="${counselors }" id="row" pagesize="5" requestURI="block/user/list.do">

	<display:column titleKey="actor.name" property="name" />

	<display:column titleKey="actor.surnames" property="surnames" />
	
	<display:column>
	<a href="actor/show.do?actorId=${row.id}"><jstl:out value="${row.userAccount.username}"/></a><br>
	</display:column>

	<display:column titleKey="actor.action">
		<jstl:if test="${user.blockedCounselors.contains(row)}">
			<a href="block/user/unblock.do?counselorId=${row.id}"> <spring:message code="admin.unBanActor" /></a>
		</jstl:if>
		<jstl:if test="${!user.blockedCounselors.contains(row)}">
			<a href="block/user/block.do?counselorId=${row.id}"> <spring:message code="admin.banActor" /></a>
		</jstl:if>
	</display:column>
	
</display:table>
</security:authorize>
