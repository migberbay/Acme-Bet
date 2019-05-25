package controllers.bookmaker;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BetPoolService;
import services.BetService;
import services.CategoryService;
import services.UserService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Bet;
import domain.BetPool;
import forms.BetPoolForm;
import forms.BettingForm;

@Controller
@RequestMapping("betPool/bookmaker")
public class BetPoolBookmakerController extends AbstractController {

	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private BetService betService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private WarrantyService warrantyService;

	//List -----------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result = new ModelAndView("betPool/list");
			result.addObject("betPools", betPoolService.getPoolsByPrincipal());
			result.addObject("isOwner", true);
			result.addObject("date", new Date());
			result.addObject("requestURI","betPool/bookmaker/list.do");
			return result;
		}
	
	
	//Create -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		BetPoolForm form = new BetPoolForm();
		form.setId(0);
		result = this.createEditModelAndView(form);

		return result;

	}
	
	//Edit -----------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(Integer betPoolId) {
			ModelAndView result;

			BetPoolForm form = betPoolService.build(betPoolService.findOne(betPoolId));
			result = this.createEditModelAndView(form);
			
			return result;

		}
	
	//Delete -----------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(Integer betPoolId) {
			BetPool pool = betPoolService.findOne(betPoolId);
			betPoolService.delete(pool);
			
			return list();
		}
	
	//Save ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("form") BetPoolForm form, BindingResult binding) {
		ModelAndView res;
			
		System.out.println(form.getDescription() + "\n"+form.getEndDate() + "\n"+form.getParticipants() + "\n"+form.getResultDate() +
		"\n"+form.getStartDate() + "\n"+form.getTitle() + "\n"+form.getCategory() + "\n"+form.getWarranty()+ "\n"+form.getIsFinal());
		
		BetPool pool = betPoolService.reconstruct(form,binding);
		
			if (binding.hasErrors()) {
				System.out.println(binding);
				res = createEditModelAndView(form);
			}else {
				try {
				if (pool.getIsFinal()) {
					Double[] ranges = {10.0, 50.0, 100.0, 500.0, 10000.0};
					for (int i = 0; i < 4; i++) {
						BetPool aux = betPoolService.copy(pool);
						aux.setTitle(pool.getTitle() + " ("+ ranges[i] +"-"+ranges[i+1]+")");
						aux.setMinRange(ranges[i]);
						aux.setMaxRange(ranges[i+1]-0.01);
						aux.setTicker(betPoolService.generateTicker());
						if(i==3){aux.setMaxRange(ranges[i+1]);}
						betPoolService.save(aux);
					}
					betPoolService.delete(pool);
					
				}else{
					betPoolService.save(pool);
				}
				
				res = new ModelAndView("redirect:/betPool/bookmaker/list.do");
				} catch (Exception e) {
					e.printStackTrace();
					res = createEditModelAndView(form);
				}
			}	

		return res;
	}
	
	//Ancillary -----------
	protected ModelAndView createEditModelAndView(BetPoolForm pool) {
		ModelAndView res;
		res = createEditModelAndView(pool, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(BetPoolForm pool, String messageCode) {
		ModelAndView res;
		res = new ModelAndView("betPool/edit");
		
		res.addObject("categories", categoryService.findAll());
		res.addObject("warranties", warrantyService.findAll());
		res.addObject("form", pool);
		res.addObject("message", messageCode);

		return res;
	}
}
