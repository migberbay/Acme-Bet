package controllers.admin;

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

import services.WarrantyService;
import controllers.AbstractController;
import domain.Warranty;

@Controller
@RequestMapping("warranty/admin")
public class AdminWarrantyController extends AbstractController {
	
	// Services ----------------------------------------------------------------
	
	@Autowired
	private WarrantyService warrantyService;
	
	// Atributes ---------------------------------------------------------------
	
	private int id = 0;
	private Boolean create;
	
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
			
			warranty = warrantyService.findOne(1439);
			
			result = new ModelAndView("warranty/show");
			result.addObject("warranty",warranty);
			return result;

		}

	// Create-----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		
		ModelAndView result;
		Warranty warranty;
		
		this.create = true;
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
		this.id = warranty.getId();
		this.create = false;
		result = this.createEditModelAndView(warranty);
	
		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("warranty") Warranty warranty, BindingResult binding){
		ModelAndView result;
		Warranty res;
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(warranty);
		} else{
			try {
				res = warrantyService.reconstruct(warranty,this.id,binding);
				warrantyService.save(res);
				result = new ModelAndView("redirect:list.do");
			} catch (ValidationException oops) {
				result = this.createEditModelAndView(warranty);
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
		
		result.addObject("create",create);
		result.addObject("warranty", warranty);
		result.addObject("message", message);

		return result;
	}
}
