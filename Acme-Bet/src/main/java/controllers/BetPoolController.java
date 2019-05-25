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
import services.SponsorService;
import services.SponsorshipService;
import domain.BetPool;
import domain.Sponsor;
import domain.Sponsorship;

@Controller
@RequestMapping("betPool/")
public class BetPoolController extends AbstractController {

	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private SponsorService sponsorService;

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
		ModelAndView result = new ModelAndView("betPool/show");
		BetPool pool = betPoolService.findOne(betPoolId);
		List<Sponsorship> sponsorships = new ArrayList<>(sponsorshipService.findByBetPool(pool));
		if (!sponsorships.isEmpty()) {
			
			Double fare = configurationService.find().getSponsorshipFare();
			Boolean isPossible = false;
			for (Sponsorship s : sponsorships) {
				if(s.getSponsor().getFunds() >= fare && s.getIsActivated()){
					isPossible = true;
					break;
				}
			}
			
			Random rand = new Random(); 
		   	Sponsorship sponsorship = sponsorships.get(rand.nextInt(sponsorships.size()));
		   	
			while ((sponsorship.getSponsor().getFunds() < fare || !sponsorship.getIsActivated()) && isPossible) {
				rand = new Random(); 
			   	sponsorship = sponsorships.get(rand.nextInt(sponsorships.size()));
			   	System.out.println("funds: "+ sponsorship.getSponsor().getFunds()+", fare: "+ fare + ", is activated: " + sponsorship.getIsActivated());
			}
			
			if(isPossible){
				result.addObject("sponsorship",sponsorship);
				Sponsor sponsor = sponsorship.getSponsor();
				sponsor.setFunds(sponsor.getFunds()-fare);
				sponsorService.save(sponsor);
			}
		}
		
		result.addObject("betPool",pool);
		return result;
	}
}
