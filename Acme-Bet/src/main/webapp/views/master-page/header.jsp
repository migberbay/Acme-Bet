<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="images/logo.png" alt="Sample Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>	
					<li><a href="message/createBroadcast.do"><spring:message code="master.page.broadcast" /></a></li>
					<li><a href="actor/register.do?type=ADMIN"><spring:message code="master.page.registerAdmin" /></a></li>
								
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('BOOKMAKER')">
			<li><a class="fNiv"><spring:message	code="master.page.bookmaker" /></a>
				<ul>
					<li class="arrow"></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('COUNSELOR')">
			<li><a class="fNiv"><spring:message	code="master.page.counselor" /></a>
				<ul>
					<li class="arrow"></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register"/></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/register.do?type=USER"><spring:message code="master.page.registerUser" /></a></li>
					<li><a href="actor/register.do?type=SPONSOR"><spring:message code="master.page.registerSponsor" /></a></li>
					<li><a href="actor/register.do?type=COUNSELOR"><spring:message code="master.page.registerCounselor" /></a></li>
					<li><a href="actor/register.do?type=BOOKMAKER"><spring:message code="master.page.registerBookmaker" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" />(<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/list.do"><spring:message code="master.page.mailbox" /></a></li>			
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

