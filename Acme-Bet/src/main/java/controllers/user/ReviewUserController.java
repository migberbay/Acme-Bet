package controllers.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CounselorService;
import services.HelpRequestService;
import services.ReviewService;
import services.UserService;
import controllers.AbstractController;

import domain.Counselor;
import domain.HelpRequest;
import domain.Review;
import domain.User;
import forms.ReviewForm;


@Controller
@RequestMapping("review/user/")
public class ReviewUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private HelpRequestService requestService;
	// List -------------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Review> reviews = reviewService.findReviewsByPrincipal();
		User user = userService.findByPrincipal();
		Collection<HelpRequest> requests = requestService.getUnreviewedRequests(user.getId());

		result = new ModelAndView("review/list");
		result.addObject("moreReviews", !requests.isEmpty());
		result.addObject("reviews",reviews);
		
		return result;
	}
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		ReviewForm review = new ReviewForm();	
		
		User user = userService.findByPrincipal();
		Collection<HelpRequest> requests = requestService.getUnreviewedRequests(user.getId());
		
		if(!requests.isEmpty()){
			result = this.createEditModelAndView(review);
		}else{
			System.out.println("list uwu");
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}
	/*
	@RequestMapping(value = "/createByList", method = RequestMethod.GET)
	public ModelAndView createByList(@RequestParam int counselorId) {
		ModelAndView result;
		
		Review review;
		
		review = reviewService.create();	
		this.counselorId = counselorId;
		User user = userService.findByPrincipal();
		
		Collection<Counselor> counselors = counselorService.getSolvedCounselorsByUser(user.getId());
			
		if(!user.getReviews().isEmpty()){
			for(Review r: user.getReviews()){
				if(counselors.contains(r.getCounselor())){
					counselors.remove(r.getCounselor());
				}
			}
		}
		
		if(!counselors.isEmpty()){
			result = this.createEditModelAndView(review);
		}else{
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}
	

	
	// Edit --------------------------------------------------------------------
	
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int reviewId) {

			ModelAndView result;
			Review review;
			review = reviewService.findOne(reviewId);	
			
			if(review.getUser().equals(userService.findByPrincipal())){
				result = this.createEditModelAndView(review);
			}else{
				result = new ModelAndView("error/access");
			}

			return result;
		}*/

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(ReviewForm review, BindingResult bindingResult) {
		ModelAndView result;
		try {
			Review saved = reviewService.reconstruct(review, bindingResult);
			reviewService.save(saved);
			result = new ModelAndView("redirect:list.do");
		} catch (ValidationException oops) {
			result = this.createEditModelAndView(review);
		} catch (Throwable e) {
			result = this.createEditModelAndView(review);
			result.addObject("errorMessage", "Review.commit.error");
		}
		
		return result;
	}
	
	// Delete -----------------------------------------------------------------
/*
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int reviewId) {
		ModelAndView result;
		Review review;
		review = reviewService.findOne(reviewId);
		if(review.getIsFinal().equals(false) && review.getUser().equals(userService.findByPrincipal())){
			reviewService.delete(review);
			result = new ModelAndView("redirect:list.do");
		}else{
			result = new ModelAndView("error/access");
		}
		
		return result;
	}
*/
	
	//Helper methods --------------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(ReviewForm review){
		ModelAndView res;
		res = createEditModelAndView(review, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(ReviewForm review, String messageCode){
		
		ModelAndView res;
		res = new ModelAndView("review/edit");
		List<Integer> scores = new ArrayList<Integer>();
		User user = userService.findByPrincipal();
		scores.add(0);scores.add(1);scores.add(2);scores.add(3);scores.add(4);scores.add(5);scores.add(6);scores.add(7);scores.add(8);scores.add(9);scores.add(10);
		Collection<HelpRequest> requests = requestService.getUnreviewedRequests(user.getId());

		res.addObject("review", review);
		res.addObject("scores",scores);
		res.addObject("requests",requests);
		res.addObject("message", messageCode);

		return res;
	}
	
}
