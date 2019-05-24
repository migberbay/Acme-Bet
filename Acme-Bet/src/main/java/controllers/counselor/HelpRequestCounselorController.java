package controllers.counselor;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import services.BetPoolService;
import services.CategoryService;
import services.HelpRequestService;
import services.CounselorService;
import services.MessageService;
import controllers.AbstractController;

import domain.Bet;
import domain.BetPool;
import domain.HelpRequest;
import domain.Counselor;
import domain.Message;


@Controller
@RequestMapping("helpRequest/counselor/")
public class HelpRequestCounselorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private HelpRequestService helpRequestService;
	
	@Autowired
	private CounselorService counselorService;
	
	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private MessageService messageService;
	
	private HelpRequest request;

	// List open -------------------------------------------------------------------
	
	@RequestMapping(value = "/listOpen", method = RequestMethod.GET)
	public ModelAndView listOpen() {
		ModelAndView result;
		
		Collection<HelpRequest> helpRequests = helpRequestService.getOpenRequests();
		String language = "";
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("es")){
			language ="es";
		}
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("en")){
			language ="en";
		}
		
		result = new ModelAndView("helpRequest/list");
		result.addObject("helpRequests",helpRequests);
		result.addObject("lan",language);
		
		return result;
	}
	
	// List assigned -------------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<HelpRequest> helpRequests = helpRequestService.findRequestsByCounselor();
		String language = "";
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("es")){
			language ="es";
		}
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("en")){
			language ="en";
		}
		
		result = new ModelAndView("helpRequest/list");
		result.addObject("helpRequests",helpRequests);
		result.addObject("lan",language);
		
		return result;
	}
	
	// Show --------------------------------------------------------------------
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int helpRequestId) {

		ModelAndView result;
		HelpRequest request;
		
		request = helpRequestService.findOne(helpRequestId);
		String language = "";
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("es")){
			language ="es";
		}
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("en")){
			language ="en";
		}
		

		result = new ModelAndView("helpRequest/show");
		result.addObject("helpRequest", request);
		result.addObject("lan",language);
		result.addObject("requestURI", "helpRequest/counselor/show.do");

		return result;
	}
	
	// Answer --------------------------------------------------------------------
	
		@RequestMapping(value = "/answer", method = RequestMethod.GET)
		public ModelAndView answer(@RequestParam int helpRequestId) {

			ModelAndView result;
			HelpRequest helpRequest;
			helpRequest = helpRequestService.findOne(helpRequestId);
			this.request=helpRequest;
			result = new ModelAndView("helpRequest/answer");
			Message message = new Message();
			message.setSubject("ANSWER HELP REQUEST " + helpRequest.getTicker());
			result.addObject("m",message);
			return result;
		}

	// Save answer -----------------------------------------------------------------

	@RequestMapping(value = "/answer", params = "save", method = RequestMethod.POST)
	public ModelAndView answer(Message m, BindingResult bindingResult) {
		ModelAndView result;
			
		try {
			Message savedM = helpRequestService.reconstructMessage(this.request,m, bindingResult);
			messageService.save(savedM);
			this.request.setStatus("PENDING");
			this.request.setCounselor(counselorService.findByPrincipal());
			helpRequestService.save(this.request);
			result = new ModelAndView("redirect:list.do");
		} catch (ValidationException oops) {
			result = new ModelAndView("helpRequest/answer");
			result.addObject("m",m);
		} catch (Throwable e) {
			result = new ModelAndView("helpRequest/answer");
			result.addObject("m",m);
			result.addObject("errorMessage", "helpRequest.commit.error");
		}
		
		return result;
	}
	
	// Delete -----------------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam int helpRequestId) {
			ModelAndView result;
			HelpRequest helpRequest;
			helpRequest = helpRequestService.findOne(helpRequestId);
			
			helpRequestService.delete(helpRequest);
			result = new ModelAndView("redirect:list.do");


			return result;
		}
	
	//Helper methods --------------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(HelpRequest helpRequest){
		ModelAndView res;
		res = createEditModelAndView(helpRequest, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(HelpRequest helpRequest, String messageCode){
		
		ModelAndView res;
		Counselor counselor = counselorService.findByPrincipal();
		res = new ModelAndView("helpRequest/edit");
		
		String language = "";
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("es")){
			language ="es";
		}
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("en")){
			language ="en";
		}
		
		res.addObject("helpRequest", helpRequest);
		res.addObject("pools", betPoolService.findFinal());
		res.addObject("categories",categoryService.getRequestCategories());
		res.addObject("lan",language);
		res.addObject("message", messageCode);

		return res;
	}
	
}
