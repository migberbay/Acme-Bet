package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Bet;
import domain.BetPool;
import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class BetServiceTest extends AbstractTest {

	//	Coverage: 99.1%
	//	Covered Instructions: 463 
	//	Missed  Instructions: 4
	//	Total   Instructions: 467
	
	@Autowired
	private BetService betService;
	
	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void driverCreateBet(){
		
		final Object testingData[][] = {{"user1", null},
										{"user2", null},
										{"user3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateBet((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateBet(String username, Class<?> expected){
		Class<?> caught = null;

		User user = (User) userService.findAll().toArray()[0];
		BetPool betPool = (BetPool) betPoolService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			this.betService.create(betPool, user);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void driverSaveBet(){
		
		final Object testingData[][] = {{"user1", "winner", 120.5, false, null},
										{"user2", "winner", 120.5, true, null},
										{"user3", "winner", 120.5, false, null},
										{"user1", "", 120.0, true, ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateSaveBet((String) testingData[i][0], (String) testingData[i][1], (Double) testingData[i][2], (Boolean) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateSaveBet(String username, String winner, Double amount, Boolean isAccepted, Class<?> expected){
		Class<?> caught = null;

		User user = (User) userService.findAll().toArray()[0];
		BetPool betPool = (BetPool) betPoolService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			Bet bet = betService.create(betPool, user);
			bet.setWinner(winner);
			bet.setAmount(amount);
			bet.setIsAccepted(isAccepted);
			betService.save(bet);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void driverUpdateBet(){
		
		final Object testingData[][] = {{"user1", "winner", 120.5, false, null},
										{"user2", "winner", 120.5, false, null},
										{"user3", "winner", 120.5, false, null},
										{"user1", "", 120.0, true, ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateBet((String) testingData[i][0], (String) testingData[i][1], (Double) testingData[i][2], (Boolean) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateUpdateBet(String username, String winner, Double amount, Boolean isAccepted, Class<?> expected){
		Class<?> caught = null;

		Bet bet = (Bet) betService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			bet.setWinner(winner);
			bet.setAmount(amount);
			bet.setIsAccepted(isAccepted);
			betService.save(bet);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
//	@Test
//	public void driverDeleteBet(){
//		
//		final Object testingData[][] = {{"user1", null},
//										{"user2", null},
//										{"user3", null}};
//		
//		for(int i = 0; i < testingData.length; i++){
//			templateDeleteBet((String) testingData[i][0], (Class<?>)testingData[i][1]);
//		}
//	}
//	
//	protected void templateDeleteBet(String username, Class<?> expected){
//		Class<?> caught = null;
//
//		Bet bet = (Bet) betService.findAll().toArray()[0];
//		
//		try{
//			super.authenticate(username);
//			this.betService.delete(bet);
//		} catch (Throwable oops){
//			caught = oops.getClass();
//		}
//		
//		this.checkExceptions(expected, caught);
//		super.unauthenticate();
//	}
	
}