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

	<security:authorize access="hasRole('ADMIN')">

		<display:table name="warranties" id="row" requestURI="warranty/admin/list.do" pagesize="5">
			
			<display:column >
				<a href="warranty/admin/show.do?warrantyId=${row.id}"><spring:message code="warranty.show"/></a>
				<jstl:if test="${row.isFinal == false}">
					<a href="warranty/admin/edit.do?warrantyId=${row.id}"><spring:message code="warranty.edit"/></a>
					<a href="warranty/admin/delete.do?warrantyId=${row.id}"><spring:message code="warranty.delete"/></a>
				</jstl:if>	
			</display:column>
			<display:column property="title" titleKey="warranty.title" />
			<display:column property="terms" titleKey="warranty.terms" />
			<display:column property="laws" titleKey="warranty.laws" />
		
		</display:table>
		
		<a href="warranty/admin/create.do"><spring:message code="warranty.create"/></a>
	
	</security:authorize>
	
<script>



</script>