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
	<spring:message code="warranty.title"/>:
	<jstl:out value="${warranty.title}"/><br>
	<spring:message code="warranty.terms"/>:
	<jstl:out value="${warranty.terms}"/><br>
	<spring:message code="warranty.laws"/>:
	<jstl:out value="${warranty.laws}"/><br>
	
	<input type="button" name="back"
		value="<spring:message code="warranty.back" />"
		onclick="javascript: window.location.replace('/Acme-Bet/bookmaker/warranty/list.do')" />
	<br />
<script>



</script>