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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<spring:message code="record.dateformat" var = "format"/>

 <jstl:choose>

	<jstl:when test="${curricula.personalRecord!=null}">
		<h1>
			<spring:message code="curricula.personalRecord" />
		</h1>
		
		<b><spring:message code="curricula.ticker"/>:</b> <jstl:out value="${curricula.ticker}"/><br>
		
		<display:table pagesize="5" class="displaytag" name="personalRecord" requestURI="${requestURI}" id="row">
			
		<spring:message code="personalRecord.fullName" var="fullNameHeader" />
			<display:column property="fullName" title="${fullNameHeader}"
				sortable="false" />
				
		<spring:message code="personalRecord.email" var="emailHeader" />
			<display:column property="email" title="${emailHeader}"
				sortable="false" />
		
		<spring:message code="personalRecord.phone" var="phoneHeader" />
			<display:column property="phone" title="${phoneHeader}"
				sortable="false" />
				
		<spring:message code="personalRecord.photo" var="photoHeader" />
			<display:column property="photo" title="${photoHeader}"
				sortable="false" />	
				
		<spring:message code="personalRecord.linkedInUrl" var="linkedInUrlHeader" />
			<display:column property="linkedInUrl" title="${linkedInUrlHeader}"
				sortable="false" />			
		
		<security:authorize access="hasRole('COUNSELOR')">
		<display:column>
		<jstl:if test="${isOwner == true}">
			<a href="curricula/personalRecord/edit.do?personalRecordId=${row.id}"> 
				<spring:message code="record.edit" />
			</a> <br/>
		</jstl:if>
		
		</display:column>
		</security:authorize>
		</display:table>



		<!--  Listing grid -->

		<h1>
			<spring:message code="curricula.educationRecords" />
		</h1>

		<display:table pagesize="5" class="displaytag"
			name="educationRecords" requestURI="${requestURI}" id="row">

			<!-- Attributes -->

			<spring:message code="educationRecord.diplomaTitle" var="diplomaTitleHeader" />
			<display:column property="diplomaTitle" title="${diplomaTitleHeader}"
				sortable="false" />

			<spring:message code="educationRecord.institution" var="institutionHeader" />
			<display:column property="institution" title="${institutionHeader}"
				sortable="false" />
				
			<b><spring:message code="educationRecord.startDate"/></b> <fmt:formatDate pattern = "${format}" value = "${educationRecord.startDate}" /><br>
				
			<spring:message code="educationRecord.endDate" var="endDateHeader" />
			<display:column property="endDate" title="${endDateHeader}"
				sortable="false" />	
				
			<spring:message code="educationRecord.attachment" var="attachmentHeader" />
			<display:column property="attachment" title="${attachmentHeader}"
				sortable="false" />
				
			<spring:message code="educationRecord.comments" var="commentsHeader" />
			<display:column property="comments" title="${commentsHeader}"
				sortable="false" />		

		<security:authorize access="hasRole('COUNSELOR')">
			<jstl:if test="${isOwner == true}">
			<display:column>
				<a href="curricula/educationRecord/delete.do?educationRecordId=${row.id}">
					<spring:message code="record.delete" />
				</a>
			</display:column>	
			<display:column>	
				<a href="curricula/educationRecord/edit.do?educationRecordId=${row.id}">
					<spring:message code="record.edit" />
				</a>
			</display:column>
			</jstl:if>
			
			
		</security:authorize>
		</display:table>



		<!-- ---------------Listing grid -->

		<h1>
			<spring:message code="curricula.professionalRecords" />
		</h1>

		<display:table pagesize="5" class="displaytag"
			name="professionalRecords" requestURI="${requestURI}"
			id="row">

			<!-- Attributes -->
			
			<spring:message code="professionalRecord.companyName" var="companyNameHeader" />
			<display:column property="companyName" title="${companyNameHeader}"
				sortable="false" />	

			<spring:message code="professionalRecord.role" var="roleHeader" />
			<display:column property="role" title="${roleHeader}"
				sortable="false" />	
				
			<spring:message code="professionalRecord.startDate" var="startDateHeader" />
			<display:column property="startDate" title="${startDateHeader}"
				sortable="false" />	
				
			<spring:message code="professionalRecord.endDate" var="endDateHeader" />
			<display:column property="endDate" title="${endDateHeader}"
				sortable="false" />	
				
			<spring:message code="professionalRecord.attachment" var="attachmentHeader" />
			<display:column property="attachment" title="${attachmentHeader}"
				sortable="false" />	
				
			<spring:message code="professionalRecord.comments" var="commentsHeader" />
			<display:column property="comments" title="${commentsHeader}"
				sortable="false" />						

		<security:authorize access="hasRole('COUNSELOR')">
			<jstl:if test="${isOwner == true}">
			<display:column>
				<a href="curricula/professionalRecord/delete.do?professionalRecordId=${row.id}">
					<spring:message code="record.delete" />
				</a>
			</display:column>	
			<display:column>
				<a href="curricula/professionalRecord/edit.do?professionalRecordId=${row.id}">
					<spring:message code="record.edit" />
				</a>
			</display:column>
			</jstl:if>
			
		</security:authorize>	
		</display:table>



		<!-- ---------------Listing grid -->

		<h1>
			<spring:message code="curricula.endorserRecords" />
		</h1>

		<display:table pagesize="5" class="displaytag"
			name="endorserRecords" requestURI="${requestURI}"
			id="row">

			<!-- Attributes -->

			<spring:message code="endorserRecord.endorserName" var="endorserNameHeader" />
			<display:column property="endorserName" title="${endorserNameHeader}"
				sortable="false" />

			<spring:message code="endorserRecord.email" var="emailHeader" />
			<display:column property="email" title="${emailHeader}"
				sortable="false" />

			<spring:message code="endorserRecord.phone" var="phoneHeader" />
			<display:column property="phone" title="${phoneHeader}"
				sortable="false" />

			<spring:message code="endorserRecord.linkedInProfile" var="linkedInProfileHeader" />
			<display:column property="linkedInProfile" title="${linkedInProfileHeader}"
				sortable="false" />

			<spring:message code="endorserRecord.comments" var="commentsHeader" />
			<display:column property="comments" title="${commentsHeader}"
				sortable="false" />

		<security:authorize access="hasRole('COUNSELOR')">
			<jstl:if test="${isOwner == true}">
			<display:column>
				<a href="curricula/endorserRecord/delete.do?endorserRecordId=${row.id}">
					<spring:message code="record.delete" />
				</a>
			</display:column>	
			<display:column>	
				<a href="curricula/endorserRecord/edit.do?endorserRecordId=${row.id}">
					<spring:message code="record.edit" />
				</a>
			</display:column>
			</jstl:if>
			
		</security:authorize>	
		</display:table>


		<!-- ---------------Listing grid -->

		<h1>
			<spring:message code="curricula.miscellaneousRecords" />
		</h1>

		<display:table pagesize="5" class="displaytag"
			name="miscellaneousRecords" requestURI="${requestURI}" id="row">

			<!-- Attributes -->

			<spring:message code="miscellaneousRecord.title" var="titleHeader" />
			<display:column property="title" title="${titleHeader}"
				sortable="false" />

			<spring:message code="miscellaneousRecord.attachment" var="attachmentHeader" />
			<display:column property="attachment" title="${attachmentHeader}"
				sortable="false" />

			<spring:message code="miscellaneousRecord.comments" var="commentsHeader" />
			<display:column property="comments" title="${commentsHeader}"
				sortable="false" />

		<security:authorize access="hasRole('COUNSELOR')">
		<jstl:if test="${isOwner == true}">
			<display:column>
				<a href="curricula/miscellaneousRecord/delete.do?miscellaneousRecordId=${row.id}">
					<spring:message code="record.delete" />
				</a>
			</display:column>	
			<display:column>	
				<a href="curricula/miscellaneousRecord/edit.do?miscellaneousRecordId=${row.id}">
					<spring:message code="record.edit" />
				</a>
			</display:column>
		</jstl:if>
			
		</security:authorize>	
		</display:table><br/>

	<jstl:if test="${isOwner == true}">
		<spring:message code="curricula.educationRecord.new" var="educationRecord" />
		<input type="button" name="educationRecord" value="${educationRecord}" onclick="javascript:relativeRedir('curricula/educationRecord/create.do');" />

		<spring:message code="curricula.professionalRecord.new" var="professionalRecord" />
		<input type="button" name="professionalRecord" value="${professionalRecord}" onclick="javascript:relativeRedir('curricula/professionalRecord/create.do');" />

		<spring:message code="curricula.endorserRecord.new" var="endorserRecord" /> 
		<input type="button" name="endorserRecord" value="${endorserRecord}" onclick="javascript:relativeRedir('curricula/endorserRecord/create.do');" />
			

		<spring:message code="curricula.miscellaneousRecord.new"	var="miscellaneousRecord" />
		<input type="button" name="miscellaneousRecord" value="${miscellaneousRecord}" onclick="javascript:relativeRedir('curricula/miscellaneousRecord/create.do');" />
	</jstl:if>		
					
	</jstl:when>

	<jstl:otherwise>
	
	<jstl:if test="${isOwner == true and hasPersonalRecord == false}">
	<spring:message code="curricula.personalRecord.new" var="personalRecord" />
		<input type="button" name="personalRecord" value="${personalRecord}"
			onclick="javascript:relativeRedir('curricula/personalRecord/create.do');" />
	</jstl:if>
	
	<jstl:if test="${isOwner == false and hasPersonalRecord == false}">
		This counselor doesn't have a curricula yet, come back later.
	</jstl:if>	
	</jstl:otherwise>

	
</jstl:choose>

<input type="button" name="back"
	value="<spring:message code="curricula.show.back" />"
	onclick="javascript: window.location.replace('')" />
<br />
