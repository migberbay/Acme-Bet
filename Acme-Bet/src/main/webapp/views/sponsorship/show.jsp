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

	
	<b><spring:message code="sponsorship.banner"/></b>: <a href="${sponsorship.banner}"><jstl:out value="${sponsorship.banner}"/></a> <br/>
	<img src="${sponsorship.banner}" height="100" width="1000"/><br/>
	<spring:message code="sponsorship.link"/>:
	<jstl:out value="${sponsorship.link}"/><br>
	<spring:message code="sponsorship.betPool"/>:
	<jstl:out value="${sponsorship.betPool.title}"/><br>
	
	<input type="button" name="back"
		value="<spring:message code="sponsorship.back" />"
		onclick="javascript: window.location.replace('/Acme-Bet/sponsorship/sponsor/list.do')" />
	<br />
<script>



</script>