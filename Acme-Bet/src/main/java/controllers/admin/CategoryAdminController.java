package controllers.admin;

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

import services.AdminService;
import services.CategoryService;
import controllers.AbstractController;
import domain.Admin;
import domain.Category;

@Controller
@RequestMapping("category/admin/")
public class CategoryAdminController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private AdminService adminService;
		
	// List -------------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Category> categories = categoryService.findAll();
		String language = "";
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("es")){
			language ="es";
		}
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("en")){
			language ="en";
		}
		
		result = new ModelAndView("category/list");
		result.addObject("categories",categories);
		result.addObject("lan",language);
		
		return result;
	}
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Category category;
		
		category = categoryService.create();		
		
		result = this.createEditModelAndView(category);
		
		return result;
	}
	
	// Show --------------------------------------------------------------------
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int categoryId) {

		ModelAndView result;
		Category category;
		
		category = categoryService.findOne(categoryId);
		
		result = new ModelAndView("category/show");
		result.addObject("category", category);
		result.addObject("requestURI", "category/admin/show.do");

		return result;
	}
	
	// Edit --------------------------------------------------------------------
	
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int categoryId) {

			ModelAndView result;
			Category category;
			
			category = categoryService.findOne(categoryId);	
			result = this.createEditModelAndView(category);

			return result;
		}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(Category category, BindingResult bindingResult) {
		ModelAndView result;
			
		try {
			categoryService.save(category);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(category,"category.commit.error");
		}
		
		return result;
	}
	
	// Delete -----------------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam int categoryId) {
			ModelAndView result;
			Category category;
			
			category = categoryService.findOne(categoryId);
			categoryService.delete(category);
			result = new ModelAndView("redirect:list.do");

			
			return result;
		}
	
	//Helper methods --------------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Category category){
		ModelAndView res;
		res = createEditModelAndView(category, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(Category category, String messageCode){
		
		ModelAndView res;

		res = new ModelAndView("category/edit");
		res.addObject("category", category);
		res.addObject("message", messageCode);

		return res;
	}
	
}
