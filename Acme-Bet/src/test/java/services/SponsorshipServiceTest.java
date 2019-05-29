package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.BetPool;
import domain.Sponsorship;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	//	Coverage: 77.9%
	//	Covered Instructions: 339
	//	Missed  Instructions: 96
	//	Total   Instructions: 435
	
	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private SponsorshipService sponsorshipService;
	
	@Test
	public void testCreate() {
		
		authenticate("sponsor1");
		
		Sponsorship sponsorship = sponsorshipService.create();
		
		Assert.isNull(sponsorship.getBanner());
		Assert.isNull(sponsorship.getLink());
		Assert.notNull(sponsorship.getSponsor());
		Assert.isTrue(sponsorship.getIsActivated().equals(true));
		
		unauthenticate();
	}
	
	@Test
	public void driverCreateSponsorship(){
		
		final Object testingData[][] = {{"sponsor1", null},
										{"sponsor2", null},
										{"sponsor3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateSponsorship((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateSponsorship(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.sponsorshipService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testSave(){
		
		authenticate("sponsor1");
		
		BetPool betPool = (BetPool) betPoolService.findAll().toArray()[0];
		
		Sponsorship sponsorship = sponsorshipService.create();
		
		sponsorship.setLink("http://www.link.com");
		sponsorship.setBanner("http://www.banner.com");
		sponsorship.setBetPool(betPool);
		
		Sponsorship result = sponsorshipService.save(sponsorship);
		Assert.isTrue(sponsorshipService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated(){
		
		authenticate(null);
		
		BetPool betPool = (BetPool) betPoolService.findAll().toArray()[0];
		
		Sponsorship sponsorship = sponsorshipService.create();
		
		sponsorship.setLink("http://www.link.com");
		sponsorship.setBanner("http://www.banner.com");
		sponsorship.setBetPool(betPool);
		
		Sponsorship result = sponsorshipService.save(sponsorship);
		Assert.isTrue(sponsorshipService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("sponsor1");
		
		BetPool betPool = (BetPool) betPoolService.findAll().toArray()[0];
		
		Sponsorship sponsorship = sponsorshipService.create();
		
		sponsorship.setBanner("banner");
		sponsorship.setBetPool(betPool);
		
		Sponsorship result = sponsorshipService.save(sponsorship);
		Assert.isTrue(sponsorshipService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testUpdate(){
		
		authenticate("sponsor1");

		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
		
		sponsorship.setBanner("http://www.bannerUpdated.com");
		
		Sponsorship result = sponsorshipService.save(sponsorship);
		Assert.isTrue(sponsorshipService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUpdateNotAuthenticated() {
		
		authenticate(null);

		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
		
		sponsorship.setBanner("http://www.bannerUpdated.com");
		
		Sponsorship result = sponsorshipService.save(sponsorship);
		Assert.isTrue(sponsorshipService.findAll().contains(result));
		
		unauthenticate();
		
	}
	
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testUpdateIncorrectData() {
		
		authenticate("sponsor1");

		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
		
		sponsorship.setBanner("banner");
		
		Sponsorship result = sponsorshipService.save(sponsorship);
		Assert.isTrue(sponsorshipService.findAll().contains(result));
		
		unauthenticate();
		
	}
	
	@Test
	public void testDelete(){
		
		authenticate("sponsor1");
		
		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
		sponsorshipService.delete(sponsorship);
		
		Assert.isTrue(!sponsorshipService.findAll().contains(sponsorship));
		
		unauthenticate();
	}
	
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testDeleteNotAuthenticated(){
		
		authenticate(null);
		
		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
		sponsorshipService.delete(sponsorship);
		
		Assert.isTrue(!sponsorshipService.findAll().contains(sponsorship));
		
		unauthenticate();
	}
	
	@Test
	public void driverDeleteSponsorship(){
		
		Object testingData[][] = {{"sponsor1", null},
								  {"sponsor2", null},
								  {"sponsor3", null}};

		
		for(int i = 0; i < testingData.length; i++){
			templateDeleteSponsorship((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeleteSponsorship(String username, Class<?> expected){
		Class<?> caught = null;
		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			this.sponsorshipService.delete(sponsorship);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}
