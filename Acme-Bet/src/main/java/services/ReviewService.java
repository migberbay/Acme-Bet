package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ReviewRepository;
import domain.Actor;
import domain.Review;


@Service
@Transactional
public class ReviewService {

	//Managed Repository -----
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Review create(){
		Review res = new Review();
		
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
	
	//Other business methods -----

}