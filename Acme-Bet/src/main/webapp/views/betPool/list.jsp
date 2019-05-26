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

-->
	<spring:message code="pool.dateformat" var = "format"/>
	
	<display:table name="betPools" id="row" requestURI="${requestURI}" pagesize="5">
		<display:column titleKey="pool.action" >
				<a href="betPool/show.do?betPoolId=${row.id}">show</a><br>
				
			<jstl:if test="${isUser and date.before(row.endDate)}">
				<a href="bet/user/create.do?betPoolId=${row.id}">bet</a>
			</jstl:if>
			
			<jstl:if test="${isOwner and row.isFinal == false}">
				<a href="betPool/bookmaker/edit.do?betPoolId=${row.id}">edit</a><br>
				<a href="betPool/bookmaker/delete.do?betPoolId=${row.id}">delete</a>
			</jstl:if>
			
			<jstl:if test="${isOwner and row.isFinal and date.after(row.endDate) and row.winners.isEmpty()}">
				<a href="betPool/bookmaker/selectWinners.do?betPoolId=${row.id}">chooseWinners</a><br>
			</jstl:if>
			
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
	
	<input type="button" name="back"
		value="<spring:message code="pool.back" />"
		onclick="javascript: window.location.replace('/')" />
	<br />
<script>



</script>