package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.EndorserRecord;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	@Autowired
	private EndorserRecordService endorserRecordService;

	@Test
	public void driverCreateEndorserRecord(){
		
		final Object testingData[][] = {{"counselor1", null},
										{"counselor2", null},
										{"counselor3", null},
										{null, IllegalArgumentException.class},
										{"sponsor1", IllegalArgumentException.class},
										{"sponsor2", IllegalArgumentException.class},
										{"sponsor3", IllegalArgumentException.class},
										{"bookmaker1", IllegalArgumentException.class},
										{"bookmaker2", IllegalArgumentException.class},
										{"bookmaker3", IllegalArgumentException.class},
										{"user1", IllegalArgumentException.class},
										{"user2", IllegalArgumentException.class},
										{"user3", IllegalArgumentException.class},
										{"admin", IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateEndorserRecord((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateEndorserRecord(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.endorserRecordService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void driverSaveEndorserRecord(){
		
		Object testingData[][] = {{"counselor1", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", null},
								  {"counselor2", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", null},
								  {"counselor3", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", null},
								  {null, "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"sponsor1", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"sponsor2", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"sponsor3", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"user1", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"user2", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"user3", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"admin", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"counselor1", "", "", "", "", "", javax.validation.ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateSaveEndorserRecord((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
					                   (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>)testingData[i][6]);
		}
	}
	
	protected void templateSaveEndorserRecord(String username, String endorserName, String email, String phone, String linkedInProfile, String comments, Class<?> expected){
		Class<?> caught = null;
		
		try{
			super.authenticate(username);
			EndorserRecord endorserRecord = this.endorserRecordService.create();
			endorserRecord.setEndorserName(endorserName);
			endorserRecord.setEmail(email);
			endorserRecord.setPhone(phone);
			endorserRecord.setComments(comments);
			endorserRecord.setLinkedInProfile(linkedInProfile);
			endorserRecord = this.endorserRecordService.save(endorserRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void driverUpdateEndorserRecord(){
		
		Object testingData[][] = {{"counselor1", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", null},
								  {"counselor2", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", null},
								  {"counselor3", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", null},
								  {null, "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"sponsor1", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"sponsor2", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"sponsor3", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"user1", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"user2", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"user3", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"admin", "title", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"counselor1", "", "", "", "", "", javax.validation.ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateEndorserRecord((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
					                   (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>)testingData[i][6]);
		}
	}
	
	protected void templateUpdateEndorserRecord(String username, String endorserName, String email, String phone, String linkedInProfile, String comments, Class<?> expected){
		Class<?> caught = null;
		
		try{
			super.authenticate(username);
			EndorserRecord endorserRecord = (EndorserRecord) this.endorserRecordService.findAll().toArray()[0];
			endorserRecord.setEndorserName(endorserName);
			endorserRecord.setEmail(email);
			endorserRecord.setPhone(phone);
			endorserRecord.setComments(comments);
			endorserRecord.setLinkedInProfile(linkedInProfile);
			endorserRecord = this.endorserRecordService.save(endorserRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void driverDeleteEndorserRecord(){
		
		Object testingData[][] = {{"counselor1", null},
								  {"counselor2", null},
								  {"counselor3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeleteEndorserRecord((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeleteEndorserRecord(String username, Class<?> expected){
		Class<?> caught = null;
		
		EndorserRecord endorserRecord = (EndorserRecord) endorserRecordService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			this.endorserRecordService.delete(endorserRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}