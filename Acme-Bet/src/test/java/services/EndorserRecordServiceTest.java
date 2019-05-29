package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Counselor;
import domain.Curricula;
import domain.EndorserRecord;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	//	Coverage: 98.4%
	//	Covered Instructions: 1.263 
	//	Missed  Instructions: 21
	//	Total   Instructions: 1.284
	
	@Autowired
	private CounselorService counselorService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private EndorserRecordService endorserRecordService;

	@Test
	public void driverCreateEndorserRecord(){
		
		final Object testingData[][] = {{"counselor1", null},
										{"counselor2", null},
										{"counselor3", null}};
		
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
								  {"counselor1", "endorserName", "email", "698756345", "http://www.link.com", "comment", javax.validation.ConstraintViolationException.class},
								  {"counselor2", "endorserName", "endorser@email.com", "698756345", "link", "comment",   javax.validation.ConstraintViolationException.class},
								  {"counselor3", "endorserName", "endorser@email.com", "69345", "http://www.link.com", "comment", javax.validation.ConstraintViolationException.class},
								  {null, "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"sponsor1", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"sponsor2", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"sponsor3", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"user1", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"user2", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"user3", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"admin", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"counselor1", "", "", "", "", "", javax.validation.ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateSaveEndorserRecord((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
					                   (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>)testingData[i][6]);
		}
	}
	
	protected void templateSaveEndorserRecord(String username, String endorserName, String email, String phone, String linkedInProfile, String comments, Class<?> expected){
		Class<?> caught = null;
		
		Counselor counselor = (Counselor) counselorService.findAll().toArray()[0];
		Curricula curricula = curriculaService.findByCounselor(counselor);
		
		try{
			super.authenticate(username);
			EndorserRecord endorserRecord = this.endorserRecordService.create();
			endorserRecord.setEndorserName(endorserName);
			endorserRecord.setEmail(email);
			endorserRecord.setPhone(phone);
			endorserRecord.setComments(comments);
			endorserRecord.setLinkedInProfile(linkedInProfile);
			endorserRecord = this.endorserRecordService.save(endorserRecord, curricula);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void driverUpdateEndorserRecord(){
		
		EndorserRecord endorserRecord = (EndorserRecord) this.endorserRecordService.findAll().toArray()[0];
		
		Object testingData[][] = {{"counselor1", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", null},
								  {"counselor2", endorserRecord.getEndorserName(), "endorser@email.com", "698756345", "http://www.link.com", "comment",  null},
								  {"counselor3", "endorserName", endorserRecord.getEmail(), endorserRecord.getPhone(), "http://www.link.com", "comment", null},
								  {"counselor1", "endorserName", "endorser@email.com", "698756345", endorserRecord.getLinkedInProfile(), "comment", null},
								  {"counselor2", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", endorserRecord.getComments(), null},
								  {null, "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"sponsor1", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"sponsor2", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"sponsor3", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"user1", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"user2", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"user3", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},
								  {"admin", "endorserName", "endorser@email.com", "698756345", "http://www.link.com", "comment", IllegalArgumentException.class},};
		
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
			Curricula curricula = curriculaService.findByEndorserRecord(endorserRecord);
			endorserRecord.setEndorserName(endorserName);
			endorserRecord.setEmail(email);
			endorserRecord.setPhone(phone);
			endorserRecord.setComments(comments);
			endorserRecord.setLinkedInProfile(linkedInProfile);
			endorserRecord = this.endorserRecordService.save(endorserRecord, curricula);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testDeleteNotAuthenticated(){
		
		authenticate(null);
		
		EndorserRecord endorserRecord = (EndorserRecord) endorserRecordService.findAll().toArray()[0];
		
		endorserRecordService.delete(endorserRecord);
		
		Assert.isTrue(!endorserRecordService.findAll().contains(endorserRecord));
		
		unauthenticate();
	}
	
	@Test
	public void driverDeleteEndorserRecord(){
		
		Object testingData[][] = {{"bookmaker1", null}};
		
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