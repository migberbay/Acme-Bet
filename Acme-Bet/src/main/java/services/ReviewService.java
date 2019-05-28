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