package controllers.user;

import java.util.Collection;

import javax.validation.ValidationException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CounselorService;
import services.ReviewService;
import services.UserService;
import controllers.AbstractController;

import domain.Review;


@Controller
@RequestMapping("review/user/")
public class ReviewUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CounselorService counselorService;
	// List -------------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Review> reviews = reviewService.findReviewsByPrincipal();
		
		result = new ModelAndView("review/list");
		result.addObject("reviews",reviews);
		
		return result;
	}
	
	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Review review;
		
		review = reviewService.create();		
		
		result = this.createEditModelAndView(review);
		
		return result;
	}
	
	// Show --------------------------------------------------------------------
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int reviewId) {

		ModelAndView result;
		Review review;
		
		review = reviewService.findOne(reviewId);
		

		result = new ModelAndView("review/show");
		result.addObject("review", review);
		result.addObject("requestURI", "review/user/show.do");

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
		}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(Review review, BindingResult bindingResult) {
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

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int reviewId) {
		ModelAndView result;
		Review review;
		review = reviewService.findOne(reviewId);
		if(review.getUser().equals(userService.findByPrincipal())){
			reviewService.delete(review);
			result = new ModelAndView("redirect:list.do");
		}else{
			result = new ModelAndView("error/access");
		}
		
		return result;
	}

	
	//Helper methods --------------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Review review){
		ModelAndView res;
		res = createEditModelAndView(review, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(Review review, String messageCode){
		
		ModelAndView res;
		res = new ModelAndView("review/edit");

		res.addObject("review", review);
		res.addObject("message", messageCode);

		return res;
	}
	
}
