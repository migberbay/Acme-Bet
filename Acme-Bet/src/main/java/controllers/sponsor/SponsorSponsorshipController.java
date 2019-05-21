package controllers.sponsor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorshipService;
import controllers.AbstractController;
import domain.Sponsorship;

@Controller
@RequestMapping("sponsor/sponsorship")
public class SponsorSponsorshipController extends AbstractController {

	@Autowired
	private SponsorshipService sponsorshipService;

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		
		sponsorships = sponsorshipService.findByPrincipal();
		
		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsor/sponsorship/list.do");

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
		
		if (sponsorshipService.findByPrincipal().contains(sponsorship)) {
			result = this.createEditModelAndView(sponsorship);
		}else{
			result = new ModelAndView("error/access");
		}

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Sponsorship sponsorship, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sponsorship);
		} else{
			try {
				sponsorshipService.save(sponsorship);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsorship,"sponsorship.commit.error");
			}
		}
		
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
				res = new ModelAndView("redirect:/list.do");
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

		result = new ModelAndView("sponsorship/edit");

		result.addObject("sponsorship", sponsorship);
		result.addObject("message", message);

		return result;
	}
}
