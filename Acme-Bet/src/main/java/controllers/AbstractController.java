/*
 * AbstractController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import services.MessageService;
import domain.Actor;
import domain.Message;

@Controller
public class AbstractController {

	@Autowired
	ConfigurationService configurationService;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	ActorService actorService;
	
	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

	@ModelAttribute("banner")
	public String getBanner() {
		return configurationService.find().getBanner();
	}

	@ModelAttribute("systemName")
	public String getSystemName() {
		return configurationService.find().getSystemName();
	}
	
	@ModelAttribute("unseenMesagges")
	public Integer getUnseenMessages() {
		Actor actor;
		try {
			actor = actorService.getByUserAccount(LoginService.getPrincipal());
		} catch (Exception e) {
			actor = null;
		}
		
		if (actor == null) {
			return null;
		}else{
			Collection<Message> messages = messageService.findByRecipientAndMoment(actor, actor.getMessagesLastSeen());		
			System.out.println(messages);
			return messages.size();
		}
	}
}
