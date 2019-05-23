package controllers.admin;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;
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
import services.BetPoolService;
import services.CategoryService;
import services.HelpRequestService;
import controllers.AbstractController;
import domain.Admin;
import domain.BetPool;
import domain.Category;
import domain.HelpRequest;

@Controller
@RequestMapping("category/admin/")
public class CategoryAdminController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private HelpRequestService helpRequestService;
		
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
		Collection<BetPool> pools = betPoolService.getPoolsByCategory(category);
		Collection<HelpRequest> requests = helpRequestService.getRequestsByCategory(category);
		
		result = new ModelAndView("category/show");
		result.addObject("category", category);
		result.addObject("pools",pools);
		result.addObject("requests",requests);
		result.addObject("requestURI", "category/admin/show.do");

		return result;
	}
	
	// Edit --------------------------------------------------------------------
	
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int categoryId) {

			ModelAndView result;
			Category category;
			category = categoryService.findOne(categoryId);	
			Collection<BetPool> pools = betPoolService.getPoolsByCategory(category);
			Collection<HelpRequest> requests = helpRequestService.getRequestsByCategory(category);
			
			if(pools.isEmpty() && requests.isEmpty()){
				result = this.createEditModelAndView(category);
			}else{
				result = new ModelAndView("error/access");	
			}
			return result;
		}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(@Valid Category category, BindingResult bindingResult) {
		ModelAndView result;
			
		try {
			categoryService.save(category);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			oops.printStackTrace();
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

			Collection<BetPool> pools = betPoolService.getPoolsByCategory(category);
			Collection<HelpRequest> requests = helpRequestService.getRequestsByCategory(category);
			
			if(pools.isEmpty() && requests.isEmpty()){
				categoryService.delete(category);
				result = new ModelAndView("redirect:list.do");
			}else{
				result = new ModelAndView("error/access");	
			}

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
		Collection<String> types = new ArrayList<String>();
		types.add("POOL");types.add("REQUEST");
		res.addObject("category", category);
		res.addObject("types", types);
		res.addObject("message", messageCode);

		return res;
	}
	
}
