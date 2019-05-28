package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.HelpRequest;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class HelpRequestServiceTest extends AbstractTest {

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
	
//	@Test
//	public void testSave(){
//		
//		authenticate("user1");
//		
//		HelpRequest helpRequest = helpRequestService.create();
//		
//		helpRequest.setStatus(status);
//		helpRequest.setDescription(description);
//		
//		unauthenticate();
//	}
	
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
