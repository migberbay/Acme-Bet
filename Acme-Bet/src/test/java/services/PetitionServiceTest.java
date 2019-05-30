package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Bet;
import domain.BetPool;
import domain.Petition;
import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class PetitionServiceTest extends AbstractTest {

	//	Coverage: 95.1%
	//	Covered Instructions: 425 
	//	Missed  Instructions: 22
	//	Total   Instructions: 447
	
	@Autowired
	private PetitionService petitionService;
	
	@Autowired
	private BetService betService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BetPoolService betPoolService;
	
	@Test
	public void driverCreatePetition(){
		
		final Object testingData[][] = {{"bookmaker1", null}, 
										{"bookmaker2", null},
										{"bookmaker3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreatePetition((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreatePetition(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.petitionService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testSave(){
		
		authenticate("bookmaker1");
		
		User user = (User) userService.findAll().toArray()[0];
		BetPool betPool = (BetPool) betPoolService.findAll().toArray()[0];
		
		Bet bet = betService.create(betPool, user);
		
		bet.setAmount(120.5);
		bet.setWinner("winner");
		bet.setIsAccepted(true);
		
		Bet saved = betService.save(bet);
		
		Petition petition = petitionService.create();
		
		petition.setStatus("PENDING");
		petition.setRejectReason("");
		petition.setBet(saved);
		petition.setUser(user);
		
		Petition result = petitionService.save(petition);
		
		Assert.isTrue(petitionService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("bookmaker2");
		
		User user = (User) userService.findAll().toArray()[0];
		BetPool betPool = (BetPool) betPoolService.findAll().toArray()[0];
		
		Bet bet = betService.create(betPool, user);
		
		bet.setAmount(120.5);
		bet.setWinner("winner");
		bet.setIsAccepted(true);
		
		Bet saved = betService.save(bet);
		
		Petition petition = petitionService.create();
		
		petition.setStatus("");
		petition.setRejectReason("");
		petition.setBet(saved);
		petition.setUser(user);
		
		Petition result = petitionService.save(petition);
		
		Assert.isTrue(petitionService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverUpdatePetition(){
		
		final Object testingData[][] = {{"bookmaker1", "PENDING", "", null},
										{"bookmaker2", "ACCEPTED", "", null},
										{"bookmaker3", "REJECTED", "rejectReason", null},
										{"bookmaker1", "", "", ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdatePetition((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>)testingData[i][3]);
		}
	}
	
	protected void templateUpdatePetition(String username, String status, String rejectReason, Class<?> expected){
		Class<?> caught = null;

		Petition petition = (Petition) petitionService.findAll().toArray()[0];
		try{
			super.authenticate(username);
			petition.setStatus(status);
			petition.setRejectReason(rejectReason);
			petitionService.save(petition);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
//	@Test(expected = DataIntegrityViolationException.class)
//	public void testDeletenotAuthenticated(){
//		
//		authenticate(null);
//		
//		Petition petition = (Petition) petitionService.findAll().toArray()[0];
//		
//		petitionService.delete(petition);
//		
//		Assert.isTrue(!petitionService.findAll().contains(petition));
//		
//		unauthenticate();
//	}
	
	@Test
	public void driverDeletePetition(){
		
		final Object testingData[][] = {{"bookmaker1", null},
										{"bookmaker2", null},
										{"bookmaker3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeletePetition((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeletePetition(String username, Class<?> expected){
		Class<?> caught = null;

		Petition petition = (Petition) petitionService.findAll().toArray()[0];
		try{
			super.authenticate(username);
			petitionService.delete(petition);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}
