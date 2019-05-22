<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- 
	Recieves: List<Messages> messages - los mensajes de un box y un actor correspondientes.
			  List<Box> boxes - los boxes de un actor para mover el mensaje. (no se incluye la trashbox)
-->


	<spring:message code="pool.dateformat" var = "format"/>
	<display:table name="betPools" id="row" requestURI="betPool/list.do" pagesize="5">
		<display:column titleKey="pool.action" >
			<a href="betPool/show.do?betPoolId=${row.id}">show</a><br>
			<jstl:if test="${isUser}">
			<a href="bet/user/create.do?betPoolId=${row.id}">bet</a>
			</jstl:if>
		</display:column>
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
	
	<input type="button" name="back"
		value="<spring:message code="pool.back" />"
		onclick="javascript: window.location.replace('/')" />
	<br />
<script>



</script>