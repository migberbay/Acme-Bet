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


	<display:table name="sponsorships" id="row" requestURI="sponsor/sponsorship/list.do" pagesize="5">
		
		<display:column >
			<a href="sponsor/sponsorship/show.do?sponsorshipId=${row.id}"><spring:message code="sponsorship.show"/></a>
			<a href="sponsor/sponsorship/edit.do?sponsorshipId=${row.id}"><spring:message code="sponsorship.edit"/></a>
			<a href="sponsor/sponsorship/delete.do?sponsorshipId=${row.id}"><spring:message code="sponsorship.delete"/></a>	
		</display:column>
		<display:column property="banner" titleKey="sponsorship.banner" />
		<display:column property="link" titleKey="sponsorship.link" />
		<display:column property="betPool" titleKey="sponsorship.betPool" />
	
	</display:table>
	
	<a href="sponsor/sponsorship/create.do"><spring:message code="sponsorship.create"/></a>
	
<script>



</script>