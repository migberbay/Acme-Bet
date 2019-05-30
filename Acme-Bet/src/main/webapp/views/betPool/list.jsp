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
	
	<jstl:if test="${isOwner}">
	<display:table name="betPools" id="row" requestURI="${requestURI}" pagesize="5">
		<display:column titleKey="pool.action" >
				<a href="betPool/show.do?betPoolId=${row.id}">show</a><br>	
				
			<jstl:if test="${isOwner and row.isFinal == false}">
				<a href="betPool/bookmaker/edit.do?betPoolId=${row.id}">edit</a><br>
				<a href="betPool/bookmaker/delete.do?betPoolId=${row.id}">delete</a>
			</jstl:if>
			
			<jstl:if test="${date.after(row.endDate) and row.winner == null}">
				<a href="betPool/bookmaker/selectWinners.do?betPoolId=${row.id}">Choose Winners</a><br>
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
	</jstl:if>
	
	<jstl:if test="${isOwner != true }">
	<h3>NOT STARTED</h3>
	<display:table name="betPoolsNotStarted" id="row" requestURI="${requestURI}" pagesize="5">
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
	
	<h3>IN PROGRESS</h3>
	<display:table name="betPoolsInProgress" id="row" requestURI="${requestURI}" pagesize="5">
		<display:column titleKey="pool.action" >
				<a href="betPool/show.do?betPoolId=${row.id}">show</a><br>
			<jstl:if test="${isUser and date.before(row.endDate) and date.after(row.startDate) and row.winner == null}">
				<a href="bet/user/create.do?betPoolId=${row.id}">bet</a>
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
	
	<h3>ENDED</h3>
	<display:table name="betPoolsEnded" id="row" requestURI="${requestURI}" pagesize="5">
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
	</jstl:if>
	
	
	<input type="button" name="back"
		value="<spring:message code="pool.back" />"
		onclick="javascript: window.location.replace('/Acme-Bet')" />
	<br />
<script>



</script>