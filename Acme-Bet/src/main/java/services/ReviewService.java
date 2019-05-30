package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReviewRepository;
import domain.Actor;
import domain.HelpRequest;
import domain.Review;
import forms.ReviewForm;


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
	
	public Collection<Review> findReviewsByCounselor(int counselorId){
		return this.reviewRepository.findReviewsByCounselor(counselorId);
	}
	
	public Review reconstruct(ReviewForm review, BindingResult bindingResult) {
		Review res;
		res = this.create();
		res.setDescription(review.getDescription());
		res.setAttachments(review.getAttachments());
		res.setScore(review.getScore());
		res.setCounselor(review.getHelpRequest().getCounselor());
		res.setMoment(new Date(System.currentTimeMillis()-1000));
		res.setUser(userService.findByPrincipal());
		
		validator.validate(res, bindingResult);
		if(bindingResult.hasErrors()){
			System.out.println("uwu");
			System.out.println(bindingResult.getFieldErrors());
			throw new ValidationException();
		}
		return res;
	}
	
	//Other business methods -----
	
	public Double getAvgReviewsPerUser(){
		Double res = this.reviewRepository.getAvgReviewsPerUser();
		if(res==null)res=0d;
		return res;
	}

	public Integer getMinReviewsPerUser(){
		Integer res = this.reviewRepository.getMinReviewsPerUser();
		if(res==null)res=0;
		return res;
	}
	
	public Integer getMaxReviewsPerUser(){
		Integer res = this.reviewRepository.getMaxReviewsPerUser();
		if(res==null)res=0;
		return res;
	}
	
	public Double getStdevReviewsPerUser(){
		Double res = this.reviewRepository.getStdevReviewsPerUser();
		if(res==null)res=0d;
		return res;
	}
	

}