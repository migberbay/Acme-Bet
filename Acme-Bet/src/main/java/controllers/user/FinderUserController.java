package controllers.user;

import controllers.AbstractController;
import domain.Category;
import domain.Finder;
import domain.BetPool;
import domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("finder/user/")
public class FinderUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FinderService finderService;

	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private CategoryService categoryService;

	// filter: change filter parameters and lists pparades -------------------------------------

	@RequestMapping(value="/filter", method= RequestMethod.GET)
	public ModelAndView filter() {
		ModelAndView result;
		result = createEditModelAndView(finderService.findByPrincipal());
		System.out.println("Finder Results:" + finderService.findByPrincipal().getBetPools());
		return result;
	}

	@RequestMapping(value="/filter", method= RequestMethod.POST, params = "filter")
	public ModelAndView filter( Finder finder, final BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(finder);
		} else {
			try {
				System.out.println("filter " + finder.getMinRange());
				Finder updatedFinder = finderService.save(finder);
				System.out.println("Bet pools controller: " + updatedFinder.getBetPools() + " con keyword: ("+ updatedFinder.getKeyword()+")");
				result = createEditModelAndView(updatedFinder);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = createEditModelAndView(finder,
						"finder.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/filter", method= RequestMethod.POST, params = "clear")
	public ModelAndView clear( Finder finder, final BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(finder);
		} else {
			try {
				Finder clearedFinder = finderService.clear(finder);
				result = createEditModelAndView(clearedFinder);
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = createEditModelAndView(finder,
						"finder.commit.error");
			}
		}
		return result;
	}

	//Helper methods---------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Finder finder){
		ModelAndView res;
		res = createEditModelAndView(finder, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(Finder finder, String messageCode){
		ModelAndView res;
		Collection<BetPool> betPools = new HashSet<BetPool>();
		String cachedMessageCode = null;
		Collection<Category> categories = categoryService.getPoolCategories();
		res = new ModelAndView("finder/edit");
		System.out.println("MOdelandview betpools " + finder.getBetPools() + " y kw " + finder.getKeyword());
		if(finderService.findOne(finder.getId()).getMoment() == null
				|| finderService.isVoid(finder)
				|| finderService.isExpired(finder)){
			System.out.println("Todas las betpools");
			betPools.addAll(betPoolService.findAll());
		}else{
			System.out.println("of");
			betPools.addAll(finderService.findOne(finder.getId()).getBetPools());
			cachedMessageCode = "finder.cachedMessage";
		}
		
		String language = "";
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("es")){
			language ="es";
		}
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("en")){
			language ="en";
		}
		
		List<Double> minRanges = new ArrayList<Double>();
		minRanges.add(10d);minRanges.add(50d);minRanges.add(100d);minRanges.add(500d);
		List<Double> maxRanges = new ArrayList<Double>();
		maxRanges.add(10d);maxRanges.add(50d);maxRanges.add(100d);maxRanges.add(10000d);
		res.addObject("cachedMessage", cachedMessageCode);
		res.addObject("finder",finder);
		res.addObject("lan",language);
		res.addObject("betPools", betPools);
		res.addObject("categories",categories);
		res.addObject("minRanges",minRanges);
		res.addObject("maxRanges",maxRanges);
		res.addObject("message", messageCode);

		return res;
	}
}
