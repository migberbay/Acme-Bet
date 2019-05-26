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
//										{"admin",   java.lang.IllegalArgumentException.class},
//										{"bookmaker1", java.lang.IllegalArgumentException.class},
//										{"bookmaker2", java.lang.IllegalArgumentException.class},
//										{"bookmaker3", java.lang.IllegalArgumentException.class},
//										{"counselor1", java.lang.IllegalArgumentException.class},
//										{"counselor2", java.lang.IllegalArgumentException.class},
//										{"counselor3", java.lang.IllegalArgumentException.class}, 
//										{"user1",   java.lang.IllegalArgumentException.class},
//										{"user2",   java.lang.IllegalArgumentException.class},
//										{"user3",   java.lang.IllegalArgumentException.class}};
		
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
	
//	@Test
//	public void driverSaveSponsorship(){
//		
//		BetPool betPool = (BetPool) betPoolService.findAll().toArray()[0];
//		
//		Object testingData[][] = {{"sponsor1", "http://www.banner.com", "http://www.link.com", betPool, null},
//								  {"sponsor1", "http://www.banner.com", "http://www.link.com", betPool, null},
//								  {"sponsor1", "http://www.banner.com", "http://www.link.com", betPool, null},
//								  {"admin",    "http://www.banner.com", "http://www.link.com", java.lang.IllegalArgumentException.class},
//								  {"bookmaker1", "http://www.banner.com", "http://www.link.com", java.lang.IllegalArgumentException.class},
//								  {"bookmaker2", "http://www.banner.com", "http://www.link.com", java.lang.IllegalArgumentException.class},
//								  {"bookmaker3", "http://www.banner.com", "http://www.link.com", java.lang.IllegalArgumentException.class},
//								  {"counselor1", "http://www.banner.com", "http://www.link.com", java.lang.IllegalArgumentException.class},
//								  {"counselor2", "http://www.banner.com", "http://www.link.com", java.lang.IllegalArgumentException.class},
//								  {"counselor3", "http://www.banner.com", "http://www.link.com", java.lang.IllegalArgumentException.class},
//								  {"user1", "http://www.banner.com", "http://www.link.com", java.lang.IllegalArgumentException.class},
//								  {"user2", "http://www.banner.com", "http://www.link.com", java.lang.IllegalArgumentException.class},
//								  {"user3", "http://www.banner.com", "http://www.link.com", java.lang.IllegalArgumentException.class}};
//		
//		for(int i = 0; i < testingData.length; i++){
//			templateSaveSponsorship((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (BetPool) testingData[i][3], (Class<?>)testingData[i][4]);
//		}
//	}
//	
//	protected void templateSaveSponsorship(String username, String banner, String link, BetPool betPool, Class<?> expected){
//		Class<?> caught = null;
//		Sponsorship sponsorship;
//		
//		try{
//			super.authenticate(username);
//			sponsorship = this.sponsorshipService.create();
//			sponsorship.setBanner(banner);
//			sponsorship.setLink(link);
//			sponsorship.setBetPool(betPool);
//			sponsorship = this.sponsorshipService.save(sponsorship);
//		} catch (Throwable oops){
//			caught = oops.getClass();
//		}
//		
//		this.checkExceptions(expected, caught);
//		super.unauthenticate();
//	}
//	
//	@Test
//	public void testUpdate() {
//		
//		authenticate("sponsor1");
//
//		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
//		
//		sponsorship.setBanner("http://www.bannerUpdated.com");
//		
//		Sponsorship result = sponsorshipService.save(sponsorship);
//		Assert.isTrue(sponsorshipService.findAll().contains(result));
//		
//		unauthenticate();
//		
//	}
	
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
	
//	@Test
//	public void driverUpdateSponsorship(){
//		
//		Object testingData[][] = {{"sponsor1", "http://www.bannerUpdated1.com", "http://www.targetUpdated1.com", null},
//								  {"sponsor2", "http://www.bannerUpdated2.com", "http://www.targetUpdated2.com", org.springframework.dao.DataIntegrityViolationException.class},
//								  {"sponsor3", "http://www.bannerUpdated3.com", "http://www.targetUpdated3.com", org.springframework.dao.DataIntegrityViolationException.class}};
//		
//		for(int i = 0; i < testingData.length; i++){
//			templateUpdateSponsorship((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>)testingData[i][3]);
//		}
//	}
//	
//	protected void templateUpdateSponsorship(String username, String banner, String link, Class<?> expected){
//		Class<?> caught = null;
//		Sponsorship sponsorship = (Sponsorship) sponsorshipService.findAll().toArray()[0];
//		
//		try{
//			super.authenticate(username);
//			sponsorship = this.sponsorshipService.create();
//			sponsorship.setBanner(banner);
//			sponsorship.setLink(link);
//			sponsorship = this.sponsorshipService.save(sponsorship);
//		} catch (Throwable oops){
//			caught = oops.getClass();
//		}
//		
//		this.checkExceptions(expected, caught);
//		super.unauthenticate();
//	}
	
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
