package services;

import java.util.ArrayList;
import java.util.Collection;
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
import domain.Category;
import domain.HelpRequest;
import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class HelpRequestServiceTest extends AbstractTest {

	//	Coverage: 94.0%
	//	Covered Instructions: 563 
	//	Missed  Instructions: 36
	//	Total   Instructions: 599
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private HelpRequestService helpRequestService;
	
	//	F.R. 33.1: Manage their help requests, which includes listing, showing, creating and updating its status.
	
	// Se comprueba que los users pueden crear help requests.
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
	
	// Se comprueba que los users pueden guardar help requests.
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
	
	// Se comprueba que no se pueden guardar help requests con campos vacios.
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
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
		 
		helpRequest.setStatus("");
		helpRequest.setDescription("");
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
	
	// Se comprueba que no se pueden guardar help requests con fechas incorrectas.
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectDate(){
		
		authenticate("user1");
		
		Date moment = new Date(System.currentTimeMillis() + 1000);
		
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
	
	// Se comprueba que los users pueden actualizar help requests,
	// pero no se actualizan si los campos están vacios.
	@Test
	public void driverUpdateHelpRequest(){
		
		final Object testingData[][] = {{"user1", "OPEN",    "description1", null},
										{"user2", "PENDING", "description2", null},
										{"user3", "SOLVED",  "description3", null},
										{"user1", "", "", ConstraintViolationException.class}};
		
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
	
//	@Test(expected = DataIntegrityViolationException.class)
//	public void testDeleteNotAuthenticated(){
//	
//		authenticate(null);
//		
//		HelpRequest helpRequest = (HelpRequest) helpRequestService.findAll().toArray()[0];
//		
//		helpRequestService.delete(helpRequest);
//		
//		Assert.isTrue(!helpRequestService.findAll().contains(helpRequest));
//		
//		unauthenticate();
//	}
	
	// Se comprueba que los users pueden eliminar las help requests.
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