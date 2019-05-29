package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BetPoolService;
import services.BetService;
import services.BookmakerService;
import services.CounselorService;
import services.FinderService;
import services.HelpRequestService;
import services.ReviewService;
import services.SponsorService;
import services.SponsorshipService;
import services.UserService;
import controllers.AbstractController;


@Controller
@RequestMapping("/admin/")
public class DashboardAdminController extends AbstractController {
	
	
	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private BetService betService;
	
	@Autowired
	private BookmakerService bookmakerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FinderService finderService;
	
	@Autowired
	private HelpRequestService helpRequestService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Autowired
	private CounselorService counselorService;

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

		res.addObject("avgHelpRequestsPerUser", Math.round(helpRequestService.getAvgHelpRequestsPerUser()*100.0d/100.0d));
		res.addObject("minHelpRequestsPerUser", helpRequestService.getMinHelpRequestsPerUser());
		res.addObject("maxHelpRequestsPerUser", helpRequestService.getMaxHelpRequestsPerUser());
		res.addObject("stdevHelpRequestsPerUser", Math.round(helpRequestService.getStdevHelpRequestsPerUser()*100.0d/100.0d));

		res.addObject("avgReviewsPerUser", Math.round(reviewService.getAvgReviewsPerUser()*100.0d/100.0d));
		res.addObject("minReviewsPerUser", reviewService.getMinReviewsPerUser());
		res.addObject("maxReviewsPerUser", reviewService.getMaxReviewsPerUser());
		res.addObject("stdevReviewsPerUser", Math.round(reviewService.getStdevReviewsPerUser()*100.0d/100.0d));

		res.addObject("avgSponsorshipsPerSponsor", Math.round(sponsorshipService.getAvgSponsorshipsPerSponsor()*100.0d/100.0d));
		res.addObject("minSponsorshipsPerSponsor", sponsorshipService.getMinSponsorshipsPerSponsor());
		res.addObject("maxSponsorshipsPerSponsor", sponsorshipService.getMaxSponsorshipsPerSponsor());
		res.addObject("stdevSponsorshipsPerSponsor", Math.round(sponsorshipService.getStdevSponsorshipsPerSponsor()*100.0d/100.0d));
		
		res.addObject("maxBetPoolsBookmakers", bookmakerService.getBookmakersWMoreBetPools());
		res.addObject("maxBetsUsers", userService.getUsersWMoreBets());
		res.addObject("maxRequestsUsers", userService.getUsersWMoreRequests());
		
		res.addObject("highestAvgScoreCounselor", counselorService.getHighestAvgScoreCounselor());
		res.addObject("topInActivatedSponsorships", sponsorService.topInActivatedSponsorships());

		return res;
	}
	
	
}