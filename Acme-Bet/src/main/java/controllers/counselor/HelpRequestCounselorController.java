package controllers.counselor;

import java.util.Collection;

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

import domain.HelpRequest;
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
			System.out.println("STATUS " + helpRequest.getStatus());
			if(helpRequest.getStatus().equals("OPEN")){
				Message message = new Message();
				message.setSubject("ANSWER HELP REQUEST " + helpRequest.getTicker());
				result = this.createEditModelAndView(message);
				result.addObject("m",message);
			}else{
				result = new ModelAndView("error/access");
			}
			return result;
		}

	// Save answer -----------------------------------------------------------------

	@RequestMapping(value = "/answer", params = "save", method = RequestMethod.POST)
	public ModelAndView answer(Message m, BindingResult bindingResult) {
		ModelAndView result;
		try {
			System.out.println("uwu");
			Message savedM = helpRequestService.reconstructMessage(this.request,m, bindingResult);
			messageService.save(savedM);
			this.request.setStatus("PENDING");
			this.request.setCounselor(counselorService.findByPrincipal());
			System.out.println("qu�");
			helpRequestService.save(this.request);
			System.out.println("no");
			result = new ModelAndView("redirect:list.do");
		} catch (ValidationException oops) {
			System.out.println("11");
			oops.printStackTrace();
			result = this.createEditModelAndView(m);
		} catch (Throwable e) {
			System.out.println("12");
			result = this.createEditModelAndView(m);
		}
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Message m){
		ModelAndView res;
		res = createEditModelAndView(m, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(Message m, String messageCode){
		
		ModelAndView res;
		res = new ModelAndView("helpRequest/answer");

		res.addObject("m", m);

		res.addObject("message", messageCode);

		return res;
	}
	
}
