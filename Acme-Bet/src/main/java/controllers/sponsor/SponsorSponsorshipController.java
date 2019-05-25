package controllers.sponsor;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BetPoolService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.BetPool;
import domain.Sponsorship;

@Controller
@RequestMapping("sponsorship/sponsor")
public class SponsorSponsorshipController extends AbstractController {

	// Services ---------------------------------------------------------------
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private BetPoolService betService;
	
	// Attributes ---------------------------------------------------------------
	
	private int id = 0;
	private Boolean create;

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		
		sponsorships = sponsorshipService.findByPrincipal();
		
		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsorship/sponsor/list.do");

		return result;
	}
	
	// Show -----------------------------------------------------------

		@RequestMapping(value = "/show", method = RequestMethod.GET)
		public ModelAndView show(@RequestParam int sponsorshipId) {
			ModelAndView result;
			Sponsorship sponsorship;
			
			sponsorship = sponsorshipService.findOne(sponsorshipId);
			
			result = new ModelAndView("sponsorship/show");
			result.addObject("sponsorship",sponsorship);
			return result;

		}

	// Create-----------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			
			ModelAndView result;
			Sponsorship sponsorship;
			
			this.create = true;
			sponsorship = sponsorshipService.create();
			result = this.createEditModelAndView(sponsorship);
	
			return result;
		}

	// Edit -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int sponsorshipId) {
			
			ModelAndView result;
			Sponsorship sponsorship;
			
			sponsorship = sponsorshipService.findOne(sponsorshipId);
			this.create = false;
			this.id = sponsorshipId;
			
			if (sponsorshipService.findByPrincipal().contains(sponsorship)) {
				result = this.createEditModelAndView(sponsorship);
			}else{
				result = new ModelAndView("error/access");
			}
	
			return result;
		}

	// Save -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@ModelAttribute("sponsorship") Sponsorship sponsorship, BindingResult binding) {
			ModelAndView result;
			Sponsorship res;
			
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(sponsorship);
			} else{
				try {
					res = sponsorshipService.reconstruct(sponsorship,this.id,binding);
					sponsorshipService.save(res);
					result = new ModelAndView("redirect:list.do");
				} catch (ValidationException oops) {
					result = this.createEditModelAndView(sponsorship);
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(sponsorship,"sponsorship.commit.error");
				}
			}
			
			return result;
		}
		
	// Activate/Deactivate -----------------------------------------------------------------

		@RequestMapping(value = "/activate", method = RequestMethod.GET)
		public ModelAndView activate(@RequestParam int sponsorshipId) {
			ModelAndView result;
			Sponsorship sponsorship;
			
			sponsorship = sponsorshipService.findOne(sponsorshipId);
			
			if(sponsorship.getIsActivated()){
				sponsorshipService.activateSponsorship(sponsorship, false);
			}else{
				sponsorshipService.activateSponsorship(sponsorship, true);
			}
		
			result = new ModelAndView("redirect:list.do");
			
			return result;
		}	
	
	// Delete ------------------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int sponsorshipId) {
			ModelAndView res;
			Sponsorship sponsorship;
			
			sponsorship = sponsorshipService.findOne(sponsorshipId);
			
			if (sponsorshipService.findByPrincipal().contains(sponsorship)) {
				try {
					sponsorshipService.delete(sponsorship);
					res = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					res = createEditModelAndView(sponsorship, "sponsorship.commit.error");
				}
			}else{
				res = new ModelAndView("error/access");
			}
	
			return res;
		}

	// Helper methods -----------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(Sponsorship sponsorship) {
		
		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message) {

		ModelAndView result;
		Collection<BetPool> pools;
		
		pools = betService.findFinal();
		result = new ModelAndView("sponsorship/edit");
		
		result.addObject("create",create);
		result.addObject("sponsorship", sponsorship);
		result.addObject("pools", pools);
		result.addObject("message", message);

		return result;
	}
}
