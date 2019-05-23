package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import services.HelpRequestService;
import controllers.AbstractController;

import domain.HelpRequest;


@Controller
@RequestMapping("helpRequest/user/")
public class HelpRequestUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private HelpRequestService helpRequestService;
		
	// List -------------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<HelpRequest> helpRequests = helpRequestService.findRequestsByPrincipal();
		
		result = new ModelAndView("helpRequest/list");
		result.addObject("helpRequests",helpRequests);
		
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

		result = new ModelAndView("helpRequest/show");
		result.addObject("helpRequest", request);

		result.addObject("requestURI", "helpRequest/user/show.do");

		return result;
	}
	
	// Edit --------------------------------------------------------------------
	
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int helpRequestId) {

			ModelAndView result;
			HelpRequest helpRequest;
			helpRequest = helpRequestService.findOne(helpRequestId);	
			
			result = this.createEditModelAndView(helpRequest);

			return result;
		}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(@Valid HelpRequest helpRequest, BindingResult bindingResult) {
		ModelAndView result;
			
		try {
			helpRequestService.save(helpRequest);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(helpRequest,"helpRequest.commit.error");
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

		res = new ModelAndView("helpRequest/edit");
		res.addObject("helpRequest", helpRequest);
		res.addObject("message", messageCode);

		return res;
	}
	
}
