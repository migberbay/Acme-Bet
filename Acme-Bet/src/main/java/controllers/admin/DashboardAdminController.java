package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BetPoolService;
import services.BetService;
import services.BookmakerService;
import services.FinderService;
import services.HelpRequestService;
import services.UserService;
import controllers.AbstractController;


@Controller
@RequestMapping("/admin/")
public class DashboardAdminController extends AbstractController {
	
	
	@Autowired
	BetPoolService betPoolService;
	
	@Autowired
	BetService betService;
	
	@Autowired
	BookmakerService bookmakerService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	FinderService finderService;
	
	@Autowired
	HelpRequestService helpRequestService;

	//DASHBOARD--------------------------------------------------------
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public ModelAndView dashboard(){
		ModelAndView res;
	
		res = new ModelAndView("admin/dashboard");

		res.addObject("avgBetsPerBetPool", Math.round(betService.getAvgBetsPerBetPool()*100.0d)/100.0d);
		res.addObject("minBetsPerBetPool", betService.getMinBetsPerBetPool());
		res.addObject("maxBetsPerBetPool", betService.getMaxBetsPerBetPool());
		res.addObject("stdevBetsPerBetPool", Math.round(betService.getStdevBetsPerBetPool()*100.0d)/100.0d);

		res.addObject("avgBetsPerUser", Math.round(betService.getAvgBetsPerUser()*100.0d)/100.0d);
		res.addObject("minBetsPerUser", betService.getMinBetsPerUser());
		res.addObject("maxBetsPerUser", betService.getMaxBetsPerUser());
		res.addObject("stdevBetsPerUser", Math.round(betService.getStdevBetsPerUser()*100.0d)/100.0d);

		res.addObject("avgResultsPerFinder", Math.round(finderService.getAvgResultsPerFinder()*100.0d/100.0d));
		res.addObject("minResultsPerFinder", finderService.getMinResultsPerFinder());
		res.addObject("maxResultsPerFinder", finderService.getMaxResultsPerFinder());
		res.addObject("stdevResultsPerFinder", Math.round(finderService.getStdevResultsPerFinder()*100.0d/100.0d));

		res.addObject("avgHelpRequestsPerUser", Math.round(helpRequestService.getAvgHelpRequestsPerUser()*100.0d/100.0d));
		res.addObject("minHelpRequestsPerUser", helpRequestService.getMinHelpRequestsPerUser());
		res.addObject("maxHelpRequestsPerUser", helpRequestService.getMaxHelpRequestsPerUser());
		res.addObject("stdevHelpRequestsPerUser", Math.round(helpRequestService.getStdevHelpRequestsPerUser()*100.0d/100.0d));
		
		res.addObject("maxBetPoolsBookmakers", bookmakerService.getBookmakersWMoreBetPools());
		res.addObject("maxBetsUsers", userService.getUsersWMoreBets());
		res.addObject("maxRequestsUsers", userService.getUsersWMoreRequests());

		return res;
	}
	
	
}