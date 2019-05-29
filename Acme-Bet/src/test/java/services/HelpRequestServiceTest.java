package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.BetPool;
import domain.Category;
import domain.HelpRequest;
import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class HelpRequestServiceTest extends AbstractTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private HelpRequestService helpRequestService;
	
	@Test
	public void driverCreateHelpRequest(){
		
		final Object testingData[][] = {{"user1", null},
										{"user2", null},
										{"user3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateHelpRequest((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateHelpRequest(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.helpRequestService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testSave(){
		
		authenticate("user1");
		
		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		User user = (User) userService.findAll().toArray()[0];
		BetPool betPool = (BetPool) betPoolService.findAll().toArray()[0];
		Category category = (Category) categoryService.findAll().toArray()[0];
		
		Collection<String> attachments = new ArrayList<String>();
		String attachment1 = "http://www.link1.com";
		String attachment2 = "http://www.link2.com";
		String attachment3 = "http://www.link3.com";
		attachments.add(attachment1);
		attachments.add(attachment2);
		attachments.add(attachment3);
		
		HelpRequest helpRequest = helpRequestService.create();
		 
		helpRequest.setStatus("PENDING");
		helpRequest.setDescription("description");
		helpRequest.setMoment(moment);
		helpRequest.setAttachments(attachments);
		helpRequest.setTicker("190606-67GIJE");
		helpRequest.setUser(user);
		helpRequest.setBetPool(betPool);
		helpRequest.setCategory(category);
		
		HelpRequest result = helpRequestService.save(helpRequest);
		
		Assert.isTrue(helpRequestService.findAll().contains(result));
		
		unauthenticate();
	}
	
	
	@Test
	public void driverUpdateHelpRequest(){
		
		final Object testingData[][] = {{"user1", "OPEN",    "description1", null},
										{"user2", "PENDING", "description2", null},
										{"user3", "SOLVED",  "description3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateHelpRequest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>)testingData[i][3]);
		}
	}
	
	protected void templateUpdateHelpRequest(String username, String status, String description, Class<?> expected){
		Class<?> caught = null;

		HelpRequest helpRequest = (HelpRequest) helpRequestService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			helpRequest.setStatus(status);
			helpRequest.setDescription(description);
			this.helpRequestService.save(helpRequest);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void driverDeleteHelpRequest(){
		
		final Object testingData[][] = {{"user1", null},
										{"user2", null},
										{"user3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeleteHelpRequest((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeleteHelpRequest(String username, Class<?> expected){
		Class<?> caught = null;

		HelpRequest helpRequest = (HelpRequest) helpRequestService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			this.helpRequestService.delete(helpRequest);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
}
