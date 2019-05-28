package services;


import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Counselor;
import domain.Review;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ReviewServiceTest extends AbstractTest {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CounselorService counselorService;
	
	@Test
	public void driverCreateReview(){
		
		final Object testingData[][] = {{"user1",  null},
										{"user2",  null},
										{"user3",  null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateReview((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateReview(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.reviewService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testSave() {
		
		authenticate("user1");

		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		Counselor counselor = (Counselor) counselorService.findAll().toArray()[0];
		
		Review review = reviewService.create();
		
		review.setDescription("Review Description");
		review.setMoment(moment);
		review.setIsFinal(false);
		review.setScore(7.0);
		review.setCounselor(counselor);
		
		Review result = reviewService.save(review);
		
		Assert.isTrue(reviewService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated() {
		
		authenticate(null);

		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		Counselor counselor = (Counselor) counselorService.findAll().toArray()[0];
		
		Review review = reviewService.create();
		
		review.setDescription("Review Description");
		review.setMoment(moment);
		review.setIsFinal(false);
		review.setScore(7.0);
		review.setCounselor(counselor);
		
		Review result = reviewService.save(review);
		
		Assert.isTrue(reviewService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testUpdate(){
		
		authenticate("user1");
		
		Review review = (Review) reviewService.findAll().toArray()[0];
		
		review.setDescription("Updated description");
		review.setScore(5.5);
		
		Review result = reviewService.save(review);
		
		Assert.isTrue(!reviewService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testDelete(){
		
		authenticate("user1");
		
		Review review = (Review) reviewService.findAll().toArray()[0];
		
		reviewService.delete(review);
		
		Assert.isTrue(!reviewService.findAll().contains(review));
		
		unauthenticate();
	}
}
