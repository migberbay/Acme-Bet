package controllers.admin;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.WarrantyService;
import controllers.AbstractController;
import domain.Warranty;

@Controller
@RequestMapping("warranty/admin")
public class AdminWarrantyController extends AbstractController {

	@Autowired
	private WarrantyService warrantyService;

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Warranty> warranties;
		
		warranties = warrantyService.findAll();
		
		result = new ModelAndView("warranty/list");
		result.addObject("warranties", warranties);
		result.addObject("requestURI", "warranty/admin/list.do");

		return result;
	}
	
	// Show -----------------------------------------------------------

		@RequestMapping(value = "/show", method = RequestMethod.GET)
		public ModelAndView show(@RequestParam int warrantyId) {
			ModelAndView result;
			Warranty warranty;
			
			warranty = warrantyService.findOne(warrantyId);
			
			result = new ModelAndView("warranty/show");
			result.addObject("warranty",warranty);
			return result;

		}

	// Create-----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		
		ModelAndView result;
		Warranty warranty;
		
		warranty = warrantyService.create();
		result = this.createEditModelAndView(warranty);

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int warrantyId) {
		
		ModelAndView result;
		Warranty warranty;
		
		warranty = warrantyService.findOne(warrantyId);
		result = this.createEditModelAndView(warranty);
	
		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Warranty warranty, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(warranty);
		} else{
			try {
				warrantyService.save(warranty);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(warranty,"warranty.commit.error");
			}
		}
		
		return result;
	}

	// Delete ------------------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int warrantyId) {
			ModelAndView res;
			Warranty warranty;
			
			warranty = warrantyService.findOne(warrantyId);
			
			try {
				warrantyService.delete(warranty);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(warranty, "warranty.commit.error");
			}
	
			return res;
		}

	// Helper methods -----------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(Warranty warranty) {
		
		ModelAndView result;

		result = this.createEditModelAndView(warranty, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Warranty warranty, final String message) {

		ModelAndView result;

		result = new ModelAndView("warranty/edit");

		result.addObject("warranty", warranty);
		result.addObject("message", message);

		return result;
	}
}
