package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Review;
import domain.SocialProfile;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ReviewServiceTest extends AbstractTest {

	@Autowired
	private ReviewService reviewService;
	
	@Test
	public void driverCreateReview(){
		
		final Object testingData[][] = {{"user1",  null},
										{"user2",  null},
										{"user3",  null}, 
										{"bookmaker1", null},
										{"bookmaker2", null},
										{"bookmaker3", null},
										{"counselor1",  null},
										{"counselor2",  null},
										{"counselor3",  null},
										{"admin",    null},
										{"sponsor1", null},
										{"sponsor2", null},
										{"sponsor3", null}};
		
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
	
//	@Test
//	public void testSave() {
//		
//		authenticate("user");
//
//		Review result;
//		Review review = reviewService.create();
//		
//		review.setDescription("");
//		review.setMoment(moment);
//		review.
//		review.setLink("https://www.linksocial.com");
//		
//		result = socialProfileService.save(socialProfile);
//		Assert.isTrue(socialProfileService.findAll().contains(result));
//		
//		unauthenticate();
//	}
//	
//	@Test(expected = IllegalArgumentException.class)
//	public void testSaveNotAuthenticated(){
//		authenticate(null);
//
//		SocialProfile socialProfile = socialProfileService.create();
//		
//		socialProfile.setNick("Nick");
//		socialProfile.setSocialNetwork("Twitter");
//		socialProfile.setLink("https://www.linksocial.com");
//		
//		SocialProfile result = socialProfileService.save(socialProfile);
//		Assert.isTrue(socialProfileService.findAll().contains(result));
//		
//		unauthenticate();
//	}
}
