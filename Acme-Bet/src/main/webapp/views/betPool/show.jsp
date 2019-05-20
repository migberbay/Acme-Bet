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
	<jstl:out value="${betPool.title}"/><br>
	<jstl:out value="${betPool.minRange}"/><br>
	<jstl:out value="${betPool.title}"/><br>
	<jstl:out value="${betPool.title}"/><br>
	<jstl:out value="${betPool.title}"/><br>
	<jstl:out value="${betPool.title}"/><br>

	
	
	<input type="button" name="back"
		value="<spring:message code="pool.back" />"
		onclick="javascript: window.location.replace('/Acme-Bet/betPool/list.do')" />
	<br />
<script>



</script>