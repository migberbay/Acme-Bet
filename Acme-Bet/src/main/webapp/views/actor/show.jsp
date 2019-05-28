<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	<div>		
		<b><spring:message code="actor.username"/></b>: <jstl:out value="${actor.userAccount.username}"/> <br/>			
		<b><spring:message code="actor.name"/></b>: <jstl:out value="${actor.name}"/> <br/>
		<b><spring:message code="actor.surnames"/></b>: <jstl:out value="${actor.surnames}"/> <br/>
		<b><spring:message code="actor.photo"/></b>: <jstl:out value="${actor.photo}"/> <br/>				 				 
		<b><spring:message code="actor.email"/></b>: <jstl:out value="${actor.email}"/> <br/>			 				 
		<b><spring:message code="actor.phone"/></b>: <jstl:out value="${actor.phone}"/> <br/>
		<b><spring:message code="actor.address"/></b>: <jstl:out value="${actor.address}"/> <br/>
		
    	<jstl:if test="${isUser or isSponsor}">
			<b>Funds</b>: <jstl:out value="${actor.funds}"/> <br/>
		</jstl:if>
		
		<jstl:if test="${isCounselor}">
			<b>Funds</b>: <jstl:out value="${actor.funds}"/> <br/>
			<b>Fare</b>: <jstl:out value="${actor.fare}"/> <br/>
		</jstl:if> 

		
	</div>
	<br/>
	
  		<jstl:if test="${principalIsAdmin}">
			<b>Suspicious:</b> <jstl:out value="${actor.isSuspicious}"/><br/>
			
			<jstl:if test="${actorIsUser}">
			<b>Luck score:</b> <jstl:out value="${actor.luckScore}"/><br/>
			</jstl:if>
			
		</jstl:if>  
		
		<br>
		
		<jstl:if test="${logged}">
			<b><spring:message code="actor.holder"/></b>: <jstl:out value="${actor.creditCard.holder}"/> <br/>
			<b><spring:message code="actor.make"/></b>: <jstl:out value="${actor.creditCard.make}"/> <br/>
			<b><spring:message code="actor.number"/></b>: <jstl:out value="${actor.creditCard.number}"/> <br/>
			<b><spring:message code="actor.CVV"/></b>: <jstl:out value="${actor.creditCard.CVV}"/> <br/>
			<b><spring:message code="actor.expiration"/></b>: <jstl:out value="${actor.creditCard.expirationDate.getMonth()}"/> / <jstl:out value="${actor.creditCard.expirationDate.getYear()+1900}"/> <br/>
			<br>
			<b><a href="actor/editPersonal.do"><spring:message code="actor.edit" /></a> Personal Data</b> <br/>
			<b><a href="actor/editCreditCard.do"><spring:message code="actor.edit" /></a> CreditCard Data</b> <br/>
			<b><a href="actor/editUserAccount.do"><spring:message code="actor.edit" /></a> UserAccount Data</b> <br/>
			
		</jstl:if>
		
	<h3><spring:message code="actor.socialProfile"/>:</h3> 
		
		<jstl:if test="${logged}">
			<a href="socialProfile/create.do"> <spring:message code="actor.create" /> </a><br>
		</jstl:if>
		
		<display:table name="socialProfiles" id="row" requestURI="socialProfile/actor/list.do" pagesize="5">
		<jstl:if test="${logged}">
			<display:column>
				<a href="socialProfile/edit.do?socialProfileId=${row.id}"> <spring:message code="actor.edit" /></a> <br/>

				<a href="socialProfile/delete.do?socialProfileId=${row.id}"> <spring:message code="actor.delete" /></a><br>
				
			</display:column>
		</jstl:if>
		    <display:column titleKey="actor.socialProfile.nick" property="nick" />
		    <display:column titleKey="actor.socialProfile.socialNetwork" property="socialNetwork" />
		    <display:column titleKey="actor.socialProfile.link" property="link" />	
	</display:table>
		<br>
		
		
	<jstl:if test="${reviews.isEmpty() == false}">
		<h3><spring:message code="actor.reviews"/>:</h3> 
		<display:table name="reviews" id="row" requestURI="actor/show.do" pagesize="5">
		<display:column>
			<a href="review/show.do?reviewId=${row.id}"><spring:message code="review.show"/></a><br/>
		</display:column>
		<display:column titleKey="review.description" property="description" />
		<spring:message code="review.moment.format" var="formatMoment"/>
		<display:column titleKey="review.moment" property="moment" format="{0,date,${formatMoment} }"/>
		<display:column titleKey="review.score" property="score"/>
		<display:column titleKey="review.user">
			<a href="actor/show.do?actorId=${row.user.id}"><jstl:out value="${row.user.userAccount.username}"/></a>
		</display:column>
	</display:table>
			
	</jstl:if>

	<jstl:if test="${logged}">
				<a href="actor/delete.do" onclick="confirmLeave();">delete ALL data</a><br>
				<a href="actor/generateData.do"> <spring:message code="actor.generate" /> </a> <br>
	</jstl:if>
		
		<input type="button" name="back"
		value="<spring:message code="actor.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br/>
	
<script>
	function confirmLeave() {
	if(!confirm("Are you sure about this? this operation is not reversible and will delete all your data...")) return;
	}
</script>