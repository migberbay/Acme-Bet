package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.BetPoolService;
import services.SponsorshipService;
import domain.BetPool;
import domain.Sponsorship;

@Controller
@RequestMapping("betPool/")
public class BetPoolController extends AbstractController {

	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private SponsorshipService sponsorshipService;

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Boolean isUser = false;
		Authority userAuthority = new Authority();
		userAuthority.setAuthority("USER");
		try {
			isUser = LoginService.getPrincipal().getAuthorities().contains(userAuthority);
		} catch (Exception e) {
			isUser = false;
		}
		

		result = new ModelAndView("betPool/list");
		result.addObject("betPools", betPoolService.findFinal());
		result.addObject("requestURI","betPool/list.do");
		result.addObject("isUser", isUser);

		return result;
	}

	// Show -----------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int betPoolId) {
		ModelAndView result;
		Double fare = configurationService.find().getSponsorshipFare();
		
		BetPool pool = betPoolService.findOne(betPoolId);
		List<Sponsorship> sponsorships = new ArrayList<>(sponsorshipService.findByBetPool(pool));
		
		for (Sponsorship sponsorship : sponsorships) {
			
		}
		
		Random rand = new Random(); 
	   	Sponsorship sponsorship = sponsorships.get(rand.nextInt(sponsorships.size()));
		
		
	    
	   	

		result = new ModelAndView("betPool/show");
		result.addObject("betPool",pool);
		
		return result;

	}
}
