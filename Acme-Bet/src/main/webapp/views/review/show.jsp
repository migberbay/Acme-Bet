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
		    <b><spring:message code="review.description"/>:</b> <jstl:out value="${review.description}"   /> <br/> 
		    <b><spring:message code="review.attachments" />:</b> 
		    <jstl:forEach var="x" items="${review.attachments}">
		    	<a href="${x}"><jstl:out value="${x}" /></a>
		    </jstl:forEach><br/>
		    <b><spring:message code="review.score" />:</b> <jstl:out value="${review.score}" /> <br/>
		    
		    <spring:message code="review.moment.format" var="format"/>
		    <b><spring:message code="review.moment" />:</b> <fmt:formatDate pattern = "${format}" value = "${review.moment}" /> <br/>
			
			 <b><spring:message code="review.counselor" />:</b> 
			 <a href="actor/show.do?actorId=${review.counselor.id}"><jstl:out value="${review.counselor.userAccount.username}" /></a> <br/>
			 
			 <b><spring:message code="review.user" />:</b> 
			 <a href="actor/show.do?actorId=${review.user.id}"><jstl:out value="${review.user.userAccount.username}" /></a> <br/>
		</div>
	    
		
