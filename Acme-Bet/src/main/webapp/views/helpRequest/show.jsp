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
		    <b><spring:message code="helpRequest.description"/>:</b> <jstl:out value="${helpRequest.description}"   /> <br/> 
		    <b><spring:message code="helpRequest.attachments" />:</b> 
		    <jstl:forEach var="x" items="${helpRequest.attachments}">
		    	<a href="${x}"><jstl:out value="${x}" /></a>
		    </jstl:forEach><br/>
		    <b><spring:message code="helpRequest.status" />:</b> <jstl:out value="${helpRequest.status}" /> <br/>
		    
		    <spring:message code="helpRequest.moment.format" var="format"/>
		    <b><spring:message code="helpRequest.moment" />:</b> <fmt:formatDate pattern = "${format}" value = "${helpRequest.moment}" /> <br/>
		    <jstl:if test="${lan=='es'}">
				 <b><spring:message code="helpRequest.category" />:</b> <jstl:out value="${helpRequest.category.spanishName}" /> <br/>
			</jstl:if>
			<jstl:if test="${lan=='en'}">
				 <b><spring:message code="helpRequest.category" />:</b> <jstl:out value="${helpRequest.category.englishName}" /> <br/>
			</jstl:if>
			
			 <b><spring:message code="helpRequest.counselor" />:</b> 
			 <a href="actor/show.do?actorId=${helpRequest.counselor.id}"><jstl:out value="${helpRequest.counselor.userAccount.username}" /></a> <br/>
			 
			 <b><spring:message code="helpRequest.betPool" />:</b> 
			 <a href="betPool/show.do?betPoolId=${helpRequest.betPool.id}"><jstl:out value="${helpRequest.betPool.title}" /></a> <br/>
		</div>
		
		<br/>
		<acme:cancel url="${requestURI }" code="helpRequest.back"/>
		
