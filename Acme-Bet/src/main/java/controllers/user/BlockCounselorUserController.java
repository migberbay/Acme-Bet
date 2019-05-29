package controllers.user;

import java.util.ArrayList;
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
import services.CounselorService;
import services.HelpRequestService;
import services.UserService;
import controllers.AbstractController;

import domain.Bet;
import domain.BetPool;
import domain.Counselor;
import domain.HelpRequest;
import domain.User;


@Controller
@RequestMapping("block/user/")
public class BlockCounselorUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private HelpRequestService helpRequestService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private CategoryService categoryService;
		
	@Autowired
	private CounselorService counselorService;
	// List -------------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		User user = userService.findByPrincipal();
		Collection<Counselor> counselors = counselorService.getAnswerCounselorsByUser(user.getId());

		result = new ModelAndView("block/list");
		result.addObject("counselors",counselors);
		result.addObject("user",user);
		
		return result;
	}

	
	// Block --------------------------------------------------------------------
	
		@RequestMapping(value = "/block", method = RequestMethod.GET)
		public ModelAndView block(@RequestParam Integer counselorId) {

			ModelAndView result;
			Counselor counselor;
			counselor = counselorService.findOne(counselorId);	
			User user = userService.findByPrincipal();
			user.getBlockedCounselors().add(counselor);
			userService.save(user);
			
			result = new ModelAndView("redirect:list.do");
			return result;
		}

		// Unblock --------------------------------------------------------------------
		
		@RequestMapping(value = "/unblock", method = RequestMethod.GET)
		public ModelAndView unblock(@RequestParam Integer counselorId) {

			ModelAndView result;
			Counselor counselor;
			counselor = counselorService.findOne(counselorId);	
			User user = userService.findByPrincipal();
			user.getBlockedCounselors().remove(counselor);
			userService.save(user);
			
			result = new ModelAndView("redirect:list.do");
			return result;
		}
	
	// Delete -----------------------------------------------------------------
/*
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int helpRequestId) {
		ModelAndView result;
		HelpRequest helpRequest;
		helpRequest = helpRequestService.findOne(helpRequestId);
		if(helpRequest.getUser().equals(userService.findByPrincipal())){
			helpRequestService.delete(helpRequest);
			result = new ModelAndView("redirect:list.do");
		}else{
			result = new ModelAndView("error/access");
		}
		
		return result;
	}
		*/
		
	// Solve -----------------------------------------------------------------

	@RequestMapping(value = "/solve", method = RequestMethod.GET)
	public ModelAndView solve(@RequestParam int helpRequestId) {
		ModelAndView result;
		HelpRequest helpRequest;
		helpRequest = helpRequestService.findOne(helpRequestId);
		helpRequest.setStatus("SOLVED");
		Counselor c = helpRequest.getCounselor();
		User u = helpRequest.getUser();
		c.setFunds(c.getFunds()+c.getFare());
		u.setFunds(u.getFunds()-c.getFare());
		counselorService.save(c);
		userService.save(u);
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
