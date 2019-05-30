package services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Counselor;
import domain.Review;
import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ReviewServiceTest extends AbstractTest {

	//	Coverage: 91.9%
	//	Covered Instructions: 545
	//	Missed  Instructions: 48
	//	Total   Instructions: 593
	
	@Autowired
	private UserService userService;
	
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
		
		User user = (User) userService.findAll().toArray()[0];
		Counselor counselor = (Counselor) counselorService.findAll().toArray()[0];
		
		Review review = reviewService.create();
		
		review.setDescription("Review Description");
		review.setMoment(moment);
		review.setScore(7.0);
		review.setUser(user);
		review.setCounselor(counselor);
		
		Review result = reviewService.save(review);
		
		Assert.isTrue(reviewService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveNotAuthenticated() {
		
		authenticate(null);

		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		Counselor counselor = (Counselor) counselorService.findAll().toArray()[0];
		
		Review review = reviewService.create();
		
		review.setDescription("Review Description");
		review.setMoment(moment);
		review.setScore(7.0);
		review.setCounselor(counselor);
		
		Review result = reviewService.save(review);
		
		Assert.isTrue(reviewService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData() {
		
		authenticate("user1");

		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		User user = (User) userService.findAll().toArray()[0];
		Counselor counselor = (Counselor) counselorService.findAll().toArray()[0];
		
		Review review = reviewService.create();
		
		review.setDescription("");
		review.setMoment(moment);
		review.setScore(7.0);
		review.setUser(user);
		review.setCounselor(counselor);
		
		Review result = reviewService.save(review);
		
		Assert.isTrue(reviewService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectDate() {
		
		authenticate("user1");

		Date moment = new Date(System.currentTimeMillis() + 1000);
		
		User user = (User) userService.findAll().toArray()[0];
		Counselor counselor = (Counselor) counselorService.findAll().toArray()[0];
		
		Review review = reviewService.create();
		
		review.setDescription("Review Description");
		review.setMoment(moment);
		review.setScore(7.0);
		review.setUser(user);
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
		
		Assert.isTrue(reviewService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void driverUpdateReview(){
		
		Collection<String> empty    = new ArrayList<String>();
		Collection<String> attachments = new ArrayList<String>();
		String picture1 = "picture1";
		String picture2 = "picture2";
		String picture3 = "picture3";
		attachments.add(picture1);
		attachments.add(picture2);
		attachments.add(picture3);
		
		final Object testingData[][] = {{"user1", "description1", 5.5, false, attachments, null},
										{"user2", "description2", 6.5, true,  attachments, null},
										{"user3", "description3", 7.5, false, empty,       null},
										{"user2", "description4", 20.0, true,  attachments, ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateReview((String) testingData[i][0], (String) testingData[i][1], (Double) testingData[i][2], (Boolean) testingData[i][3], 
								(Collection<String>) testingData[i][4], (Class<?>)testingData[i][5]);
		}
	}
	
	protected void templateUpdateReview(String username, String description, Double score, Boolean isFinal, Collection<String> attachments, Class<?> expected){
		Class<?> caught = null;

		Review review = (Review) reviewService.findAll().toArray()[0];
		try{
			super.authenticate(username);
			review.setDescription(description);
			review.setScore(score);
			review.setAttachments(attachments);
			this.reviewService.save(review);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testDelete(){
		
		authenticate("user1");
		
		Review review = (Review) reviewService.findAll().toArray()[0];
		
		reviewService.delete(review);
		
		Assert.isTrue(!reviewService.findAll().contains(review));
		
		unauthenticate();
	}
	
//	@Test
//	public void driverDeleteReview(){
//		
//		final Object testingData[][] = {{"user1",  null},
//										{"user2",  null},
//										{"user3",  null}};
//		
//		for(int i = 0; i < testingData.length; i++){
//			templateDeleteReview((String) testingData[i][0], (Class<?>)testingData[i][1]);
//		}
//	}
//	
//	protected void templateDeleteReview(String username, Class<?> expected){
//		Class<?> caught = null;
//
//		Review review = (Review) reviewService.findAll().toArray()[0];
//		try{
//			super.authenticate(username);
//			this.reviewService.delete(review);
//		} catch (Throwable oops){
//			caught = oops.getClass();
//		}
//		
//		this.checkExceptions(expected, caught);
//		super.unauthenticate();
//	}
}
