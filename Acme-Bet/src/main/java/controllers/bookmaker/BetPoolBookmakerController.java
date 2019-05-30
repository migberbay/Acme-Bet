package controllers.bookmaker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BetPoolService;
import services.BetService;
import services.BookmakerService;
import services.CategoryService;
import services.UserService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Bet;
import domain.BetPool;
import domain.User;
import forms.BetPoolForm;
import forms.WinnersForm;

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
	private BookmakerService bookmakerService;
	
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
			BetPool pool = betPoolService.findOne(betPoolId);
			if (pool.getBookmaker().equals(bookmakerService.findByPrincipal())){
				BetPoolForm form = betPoolService.build(pool);
				result = this.createEditModelAndView(form);
			}else{result = new ModelAndView("error/access");}
			return result;
		}
	
	//Delete -----------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(Integer betPoolId) {
			BetPool pool = betPoolService.findOne(betPoolId);
			if (pool.getBookmaker().equals(bookmakerService.findByPrincipal())){
				betPoolService.delete(pool);
				
				return list();
			}else{return new ModelAndView("error/access");}
			
		}
	
	//Winners -----------------------------------------------------------
		
		@RequestMapping(value = "/selectWinners", method = RequestMethod.GET)
		public ModelAndView selectWinners(Integer betPoolId) {
			
			BetPool pool = betPoolService.findOne(betPoolId);
			ModelAndView res;
			if (pool.getBookmaker().equals(bookmakerService.findByPrincipal())|| pool.getWinner() == null) {
				res = new ModelAndView("betPool/selectWinners");
				WinnersForm form = new WinnersForm();
				form.setBetPoolId(betPoolId);
				Collection<String> possibles = new ArrayList<>();
				
				possibles.add("TIE");
				possibles.add("NO CONTEST");
				possibles.addAll(pool.getParticipants());
				
				res.addObject("possibles", possibles);
				res.addObject("form",form);
			}else{
				res = new ModelAndView("error/access");
			}
			
			return res;
		}
		
		@RequestMapping(value = "/selectWinners", method = RequestMethod.POST, params = "save")
		public ModelAndView saveWinners(@ModelAttribute("form") WinnersForm form, BindingResult binding) {
			ModelAndView res;
			
			BetPool betPool = betPoolService.findOne(form.getBetPoolId());
			System.out.println(betPool.getGroupCode());
			Collection<BetPool> pools = betPoolService.getPoolsByCode(betPool.getGroupCode());
			System.out.println(pools);
			Date date = new Date(System.currentTimeMillis()+5000);
			
			System.out.println(date);
			for (BetPool pool : pools) {
				pool.setStartDate(date);
				pool.setEndDate(date);
				pool.setResultDate(date);
			}

			// verificamos que las apuestas estan aceptadas (eliminamos las no aceptadas de las pools de high stakes)
			for (BetPool pool : pools) {
				Collection<Bet> bets = new ArrayList<>();
				for (Bet bet : pool.getBets()) {
					if(bet.getIsAccepted()){bets.add(bet);}
				}
				pool.setBets(bets);
			}
			
			String winner = form.getWinner();
			
			if (winner == null) {
				res = selectWinners(form.getBetPoolId());
				res.addObject("isEmpty", true);
			}else{
				res = new ModelAndView("redirect:/betPool/show.do?betPoolId="+form.getBetPoolId());
				
			if (winner.equals("NO CONTEST")){
				for (BetPool pool : pools) {
					for (Bet bet : pool.getBets()) {
						User user = bet.getUser();
						user.setFunds(bet.getAmount()+user.getFunds());
						userService.save(user);
					}
				pool.setWinner("NO CONTEST");
				betPoolService.save(pool);
				}
			}else if (winner.equals("TIE")) {
				Double totalbets = 0.0;
				for (BetPool pool : pools) {
					for (Bet bet : pool.getBets()) {
						totalbets=bet.getAmount()+totalbets;
					}
					
					for (Bet bet : pool.getBets()) {
						User user = bet.getUser();
						Double size = new Double(pool.getBets().size());
						user.setFunds(user.getFunds()+ (totalbets/size));
						userService.save(user);
					}
					pool.setWinner("TIE");
					betPoolService.save(pool);
				}
				
			}else{
				for (BetPool pool : pools) {
					double totaluserwinners = 0.0;
					double totalbetted = 0.0;
						for (Bet bet : pool.getBets()) {
							if(bet.getWinner().equals(winner)){totaluserwinners++;}
							totalbetted = totalbetted + bet.getAmount();
						}
						for (Bet bet : pool.getBets()) {
							if(bet.getWinner().equals(winner)){
								User user = bet.getUser();
								user.setFunds(user.getFunds()+(totalbetted/totaluserwinners));
								userService.save(user);
						}
					}
					pool.setWinner(winner);
					betPoolService.save(pool);
				}	
			}	
		}
			return res;
		}
	//Save ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("form") BetPoolForm form, BindingResult binding) {
		ModelAndView res;
			
		System.out.println(form.getId()+"\n"+form.getDescription() + "\n"+form.getEndDate() + "\n"+form.getParticipants() + "\n"+form.getResultDate() +
		"\n"+form.getStartDate() + "\n"+form.getTitle() + "\n"+form.getCategory() + "\n"+form.getWarranty()+ "\n"+form.getIsFinal());
		
		BetPool pool = betPoolService.reconstruct(form,binding);
		Boolean dateIncorrectOrder = true;
		Boolean minimumParticipants = true;
		
		System.out.println("1");
		if(pool.getEndDate() != null && pool.getStartDate() != null &&  pool.getResultDate() != null){
			System.out.println("1.1");
			if (pool.getEndDate().after(pool.getStartDate()) && 
					pool.getEndDate().before(pool.getResultDate())){dateIncorrectOrder = false;}
		}
		System.out.println("2");
		if (pool.getParticipants() != null) {
			System.out.println("2.1");
			if (pool.getParticipants().size() >= 2) {
				minimumParticipants = false;
			}
		}
		
		System.out.println(binding.hasErrors() +" "+ dateIncorrectOrder +" "+ minimumParticipants);
			if (binding.hasErrors() || dateIncorrectOrder || minimumParticipants) {
				System.out.println(binding);
				res = createEditModelAndView(form);
				res.addObject("dateIncorrectOrder", dateIncorrectOrder);
				res.addObject("minimumParticipants", minimumParticipants);
			}else {
				try {
				if (pool.getIsFinal()) {
					String[] tickers ={betPoolService.generateTicker(),betPoolService.generateTicker(),
							betPoolService.generateTicker(),betPoolService.generateTicker()};
					String groupCode = tickers[0].split("-")[0].trim();//cogemos solo la primera parte de todos los tickers.
					groupCode = groupCode + "-";
					for (int i = 0; i < tickers.length; i++) {
						groupCode = groupCode + tickers[i].split("-")[1].trim();
					}
					
					Double[] ranges = {10.0, 50.0, 100.0, 500.0, 10000.0};
					for (int i = 0; i < 4; i++) {
						BetPool aux = betPoolService.copy(pool);
						aux.setTitle(pool.getTitle() + " ("+ ranges[i] +"-"+ranges[i+1]+")");
						aux.setMinRange(ranges[i]);
						aux.setMaxRange(ranges[i+1]-0.01);
						aux.setTicker(tickers[i]);
						aux.setGroupCode(groupCode);
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
