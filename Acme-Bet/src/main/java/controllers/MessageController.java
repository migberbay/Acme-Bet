
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import services.BoxService;
import services.MessageService;
import domain.Box;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("message/")
public class MessageController extends AbstractController {

	//Services

	@Autowired
	private MessageService		messageService;

	@Autowired
	private BoxService			boxService;

	@Autowired
	private UserAccountService	userAccountService;


	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}

	//Listing-----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int boxId) {
		ModelAndView res;

		if (!LoginService.getPrincipal().equals(this.boxService.findOne(boxId).getUserAccount()))
			res = new ModelAndView("error/access");
		else {

			final Collection<Box> boxes = this.boxService.findByUserAccountId(LoginService.getPrincipal().getId());
			final Collection<Message> messages = new ArrayList<>();
			for (final Integer i : this.boxService.findOne(boxId).getMessages())
				messages.add(this.messageService.findOne(i));

			res = new ModelAndView("message/list");
			res.addObject("messages", messages);
			res.addObject("boxes", boxes);
		}
		return res;
	}

	//Create-----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		final MessageForm form = new MessageForm();

		res = this.createEditModelAndView(form, false);
		return res;

	}

	@RequestMapping(value = "/createBroadcast", method = RequestMethod.GET)
	public ModelAndView createBroadcast() {
		ModelAndView res;
		if (!LoginService.hasRole("ADMIN"))
			res = new ModelAndView("error/access");
		else {
			final MessageForm form = new MessageForm();
			String recipients = " ";
			for (final UserAccount ua : this.userAccountService.findAll())
				recipients = recipients + ua.getUsername() + " , ";

			form.setRecipients(recipients);
			res = this.createEditModelAndView(form, true);
		}
		return res;

	}

	//Save-------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final MessageForm form, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			res = this.createEditModelAndView(form, false);
		} else
			try {

				final Message message = this.messageService.reconstruct(form);

				final Message saved = this.messageService.save(message);
				this.messageService.addMesageToBoxes(saved);
				res = new ModelAndView("redirect:/box/list.do");
			} catch (final Throwable e) {
				res = this.createEditModelAndView(form, false, "message.commit.error");
				for (final StackTraceElement st : e.getStackTrace())
					System.out.println(st);

			}
		return res;
	}

	//Delete-----------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView res;
		final Message message = this.messageService.findOne(messageId);
		final Collection<Box> boxes = this.boxService.findByUserAccountId(LoginService.getPrincipal().getId());
		final MessageForm form = new MessageForm();
		boolean hasMessage = false;
		for (final Box b : boxes)
			if (b.getMessages().contains(messageId))
				hasMessage = true;
		if (!hasMessage)
			res = new ModelAndView("error/access");
		else
			try {
				this.messageService.delete(message);
				res = new ModelAndView("redirect:/box/list.do");
			} catch (final Throwable e) {
				res = this.createEditModelAndView(form, false, "message.commit.error");
			}
		return res;
	}

	//Move message ---------------------------------------------------

	@RequestMapping(value = "/moveToBox", method = RequestMethod.GET)
	public ModelAndView moveToBox(@RequestParam final int messageId, @RequestParam final int boxId, @RequestParam final int newBoxId) {
		ModelAndView res;
		final MessageForm form = new MessageForm();
		final Message message = this.messageService.findOne(messageId);
		final Box original = this.boxService.findOne(boxId);
		final Box destination = this.boxService.findOne(newBoxId);
		if (!original.getUserAccount().equals(LoginService.getPrincipal()) || !destination.getUserAccount().equals(LoginService.getPrincipal()))
			res = new ModelAndView("error/access");
		else
			try {
				final Collection<Integer> originalMessages = original.getMessages();
				originalMessages.remove(message.getId());
				original.setMessages(originalMessages);
				this.boxService.save(original);

				final Collection<Integer> newMessages = destination.getMessages();
				newMessages.add(message.getId());
				destination.setMessages(newMessages);
				this.boxService.save(destination);

				res = new ModelAndView("redirect:/box/list.do");
			} catch (final Throwable e) {
				res = this.createEditModelAndView(form, false, "message.commit.error");
			}
		return res;
	}

	//Helper methods---------------------------------------------------
	protected ModelAndView createEditModelAndView(final MessageForm form, final boolean isBroadcast) {
		ModelAndView res;
		res = this.createEditModelAndView(form, isBroadcast, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(final MessageForm form, final boolean isBroadcast, final String messageCode) {
		ModelAndView res;

		//aqui habria que contemplar si el mensaje esta siendo editado o creado
		// y añadir metodos en consecuencia, pero como los mensajes no pueden ser 
		//editados no es necesario

		res = new ModelAndView("message/edit");
		res.addObject("messageForm", form);
		res.addObject("accounts", this.userAccountService.findAll());
		res.addObject("message", messageCode);
		res.addObject("isBroadcast", isBroadcast);

		return res;
	}

}
