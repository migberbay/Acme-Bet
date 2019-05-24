package controllers.user;

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
import services.UserService;
import controllers.AbstractController;

import domain.Bet;
import domain.BetPool;
import domain.HelpRequest;
import domain.User;


@Controller
@RequestMapping("helpRequest/user/")
public class HelpRequestUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private HelpRequestService helpRequestService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private CategoryService categoryService;
		
	// List -------------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<HelpRequest> helpRequests = helpRequestService.findRequestsByPrincipal();
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
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		HelpRequest request;
		
		request = helpRequestService.create();		
		
		result = this.createEditModelAndView(request);
		
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
		result.addObject("requestURI", "helpRequest/user/show.do");

		return result;
	}
	
	// Edit --------------------------------------------------------------------
	
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int helpRequestId) {

			ModelAndView result;
			HelpRequest helpRequest;
			helpRequest = helpRequestService.findOne(helpRequestId);	
			
			if(helpRequest.getCounselor()==null){
				result = this.createEditModelAndView(helpRequest);
			}else{
				result = new ModelAndView("error/access");
			}

			return result;
		}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(HelpRequest helpRequest, BindingResult bindingResult) {
		ModelAndView result;
			
		try {
			HelpRequest saved = helpRequestService.reconstruct(helpRequest, bindingResult);
			helpRequestService.save(saved);
			result = new ModelAndView("redirect:list.do");
		} catch (ValidationException oops) {
			result = new ModelAndView("helpRequest/edit");
			result.addObject("helpRequest",helpRequest);
		} catch (Throwable e) {
			result = new ModelAndView("helpRequest/edit");
			result.addObject("helpRequest",helpRequest);
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
		
		
	// Solve -----------------------------------------------------------------

			@RequestMapping(value = "/solve", method = RequestMethod.GET)
			public ModelAndView solve(@RequestParam int helpRequestId) {
				ModelAndView result;
				HelpRequest helpRequest;
				helpRequest = helpRequestService.findOne(helpRequestId);
				helpRequest.setStatus("SOLVED");
				helpRequestService.save(helpRequest);
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
		User user = userService.findByPrincipal();
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
