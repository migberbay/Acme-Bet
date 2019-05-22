package controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BetPoolService;
import services.BetService;
import services.UserService;
import controllers.AbstractController;
import domain.Bet;
import domain.BetPool;
import forms.BettingForm;

@Controller
@RequestMapping("bet/user")
public class BetUserController extends AbstractController {

	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private BetService betService;
	
	@Autowired
	private UserService userService;


	//Create -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int betPoolId) {
		ModelAndView result;

		BettingForm form = new BettingForm();
		form.setBetPoolId(betPoolId);
		result = this.createEditModelAndView(form);

		return result;

	}
	
	//Save ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(BettingForm form, BindingResult binding) {
		ModelAndView result;
		
		Bet res = betService.reconstruct(form, binding);
		
		if(binding.hasErrors()) {
			result = this.createEditModelAndView(form);
			System.out.println(binding);
		} else {
			try {
				System.out.println(res.getTicker());
				Bet saved = betService.save(res);
				BetPool pool = res.getBetPool();
				pool.getBets().add(saved);
				
				result = new ModelAndView("redirect:/betPool/show.do?betPoolId="+pool.getId());
			} catch (Throwable oops) {
				result = this.createEditModelAndView(form, "commit.error");
				oops.printStackTrace();
			}
		}
		return result;
	}
	
	//Ancillary -----------
	protected ModelAndView createEditModelAndView(BettingForm form) {
		ModelAndView res;
		res = createEditModelAndView(form, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(BettingForm form, String messageCode) {
		ModelAndView res;
		res = new ModelAndView("bet/edit");
		
		res.addObject("participants",betPoolService.findOne(form.getBetPoolId()).getParticipants());
		res.addObject("form", form);
		res.addObject("message", messageCode);

		return res;
	}
}
