package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.BetPool;
import domain.Bookmaker;
import domain.Category;
import domain.Warranty;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class BetPoolServiceTest extends AbstractTest {

	//	Coverage: 93.6%
	//	Covered Instructions: 
	//	Missed  Instructions: 11
	//	Total   Instructions: 189
	
	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private BookmakerService bookmakerService;
	
	@Autowired
	private WarrantyService warrantyService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Test
	public void driverCreateBetPool(){
		
		final Object testingData[][] = {{"bookmaker1", null},
										{"bookmaker2", null},
										{"bookmaker3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateBetPool((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateBetPool(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.betPoolService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testSave(){
		
		authenticate("bookmaker1");
		
		Category category = (Category) categoryService.findAll().toArray()[0];
		Warranty warranty = (Warranty) warrantyService.findAll().toArray()[0];
		Bookmaker bookmaker = (Bookmaker) bookmakerService.findAll().toArray()[0];
		
		Date moment     = new Date(System.currentTimeMillis() - 1000);
		Date startDate  = new Date(System.currentTimeMillis() + 10000);
		Date endDate    = new Date(System.currentTimeMillis() + 10000);
		Date resultDate = new Date(System.currentTimeMillis() + 10000);
		
		BetPool betPool = betPoolService.create();
		
		betPool.setTitle("Title betPool");
		betPool.setDescription("Description betPool");
		betPool.setMaxRange(10000.0);
		betPool.setMinRange(10.0);
		betPool.setCategory(category);
		betPool.setWarranty(warranty);
		betPool.setMoment(moment);
		betPool.setResultDate(resultDate);
		betPool.setStartDate(startDate);
		betPool.setEndDate(endDate);
		betPool.setBookmaker(bookmaker);
		
		BetPool result = betPoolService.save(betPool);
		
		Assert.isTrue(betPoolService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveIncorrectData(){
		
		authenticate("bookamker1");
		
		Category category = (Category) categoryService.findAll().toArray()[0];
		Warranty warranty = (Warranty) warrantyService.findAll().toArray()[0];
		Bookmaker bookmaker = (Bookmaker) bookmakerService.findAll().toArray()[0];
		
		Date moment     = new Date(System.currentTimeMillis() - 1000);
		Date startDate  = new Date(System.currentTimeMillis() + 10000);
		Date endDate    = new Date(System.currentTimeMillis() + 10000);
		Date resultDate = new Date(System.currentTimeMillis() + 10000);
		
		BetPool betPool = betPoolService.create();
		
		betPool.setTitle("");
		betPool.setDescription("");
		betPool.setMaxRange(10000.0);
		betPool.setMinRange(10.0);
		betPool.setCategory(category);
		betPool.setWarranty(warranty);
		betPool.setMoment(moment);
		betPool.setResultDate(resultDate);
		betPool.setStartDate(startDate);
		betPool.setEndDate(endDate);
		betPool.setBookmaker(bookmaker);
		
		BetPool result = betPoolService.save(betPool);
		
		Assert.isTrue(betPoolService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectDate(){
		
		authenticate("bookmaker1");
		
		Category category = (Category) categoryService.findAll().toArray()[0];
		Warranty warranty = (Warranty) warrantyService.findAll().toArray()[0];
		Bookmaker bookmaker = (Bookmaker) bookmakerService.findAll().toArray()[0];
		
		Date moment     = new Date(System.currentTimeMillis() + 1000);
		Date startDate  = new Date(System.currentTimeMillis() - 10000);
		Date endDate    = new Date(System.currentTimeMillis() - 10000);
		Date resultDate = new Date(System.currentTimeMillis() - 10000);
		
		BetPool betPool = betPoolService.create();
		
		betPool.setTitle("Title betPool");
		betPool.setDescription("Description betPool");
		betPool.setMaxRange(10000.0);
		betPool.setMinRange(10.0);
		betPool.setCategory(category);
		betPool.setWarranty(warranty);
		betPool.setMoment(moment);
		betPool.setResultDate(resultDate);
		betPool.setStartDate(startDate);
		betPool.setEndDate(endDate);
		betPool.setBookmaker(bookmaker);
		
		BetPool result = betPoolService.save(betPool);
		
		Assert.isTrue(betPoolService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testUpdate(){
		
		authenticate("bookmaker1");
		
		BetPool betPool = (BetPool) betPoolService.findAll().toArray()[0];
		
		betPool.setTitle("Updated title betPool");
		betPool.setDescription("Updated description betPool");
		
		BetPool result = betPoolService.save(betPool);
		
		Assert.isTrue(betPoolService.findAll().contains(result));
		
		unauthenticate();
	}
	

	@Test
	public void driverDeleteBetPool(){
		
		Object testingData[][] = {{"bookmaker1", null},
								  {"bookmaker2", null},
								  {"bookmaker3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeleteBetPool((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeleteBetPool(String username, Class<?> expected){
		Class<?> caught = null;
		
		BetPool betPool = (BetPool) betPoolService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			this.betPoolService.delete(betPool);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}
