package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReviewRepository;
import domain.Actor;
import domain.HelpRequest;
import domain.Review;


@Service
@Transactional
public class ReviewService {

	//Managed Repository -----
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	//Supporting Services -----
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Validator validator;
	
	//Simple CRUD methods -----
	
	public Review create(){
		Review res = new Review();
		res.setAttachments(new ArrayList<String>());
		return res;
	}
	
	public Collection<Review> findAll(){
		return reviewRepository.findAll();
	}
	
	public Review findOne(int Id){
		return reviewRepository.findOne(Id);
	}
	
	public Review save(Review a){
		
		Review saved = reviewRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Review a){
		reviewRepository.delete(a);
	}

	public Collection<Review> findReviewsByPrincipal() {
		return reviewRepository.findReviewsByUser(userService.findByPrincipal().getId());
	}
	
	public Review reconstruct(Review review, BindingResult bindingResult) {
		Review res;
		if(review.getId()==0){
			res = this.create();
			res = review;
			res.setIsFinal(false);
			res.setMoment(new Date());
			res.setUser(userService.findByPrincipal());
		}else{
			res = reviewRepository.findOne(review.getId());
			res.setAttachments(review.getAttachments());
			res.setCounselor(review.getCounselor());
			res.setDescription(review.getDescription());
			res.setIsFinal(review.getIsFinal());
			res.setScore(review.getScore());
		}
		
		validator.validate(res, bindingResult);
		if(bindingResult.hasErrors()){
			System.out.println("uwu");
			System.out.println(bindingResult.getFieldErrors());
			throw new ValidationException();
		}
		return res;
	}
	
	//Other business methods -----

}