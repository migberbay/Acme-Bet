package controllers;

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
import services.ReviewService;
import services.UserService;
import controllers.AbstractController;

import domain.Counselor;
import domain.Review;
import domain.User;


@Controller
@RequestMapping("review/")
public class ReviewController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ReviewService reviewService;
	
	// Show --------------------------------------------------------------------
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int reviewId) {

		ModelAndView result;
		Review review;
		
		review = reviewService.findOne(reviewId);
		

		result = new ModelAndView("review/show");
		result.addObject("review", review);
		result.addObject("requestURI", "review/show.do");

		return result;
	}
	
	
}
