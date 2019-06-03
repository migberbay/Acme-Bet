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
import domain.PersonalRecord;


import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	//	Coverage: 97.5%
	//	Covered Instructions: 1.072
	//	Missed  Instructions: 27
	//	Total   Instructions: 1.099
	
	@Autowired
	private CounselorService counselorService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private PersonalRecordService personalRecordService;

	//	F.R. 32.3: Manage the records of their curricula, which includes listing, showing, creating, updating and deleting them.
	
	//	Se comprueba que los counselors pueden crear records.
	@Test
	public void driverCreatePersonalRecord(){
		
		final Object testingData[][] = {{"counselor1", null},
										{"counselor2", null},
										{"counselor3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreatePersonalRecord((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreatePersonalRecord(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.personalRecordService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		} 
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	//	Se comprueba que se pueden guardar records, pero no se guardan si hay algun campo vacio,
	//	algún campo incorrecto o se trata de un actor distinto a los counselors.
	@Test
	public void testSave(){
		
		authenticate("counselor1");
		
		Counselor counselor = (Counselor) counselorService.findByPrincipal();
		Curricula curricula = curriculaService.findByCounselor(counselor);
		
		PersonalRecord personalRecord = this.personalRecordService.create();
		personalRecord.setFullName("name");
		personalRecord.setPhoto("http://www.photo.com");
		personalRecord.setEmail("endorser@email.com");
		personalRecord.setPhone("698756345");
		personalRecord.setLinkedInUrl("http://www.link.com");
		
		PersonalRecord result = personalRecordService.save(personalRecord);
		
		curricula.setPersonalRecord(result);
		curriculaService.save(curricula);
		
		Assert.isTrue(personalRecordService.findAll().contains(result));
		Assert.isTrue(curriculaService.findAll().contains(curricula));
		
		unauthenticate();
	}
	
	@Test
	public void driverSavePersonalRecord(){
		
		Object testingData[][] = {
//								  {"counselor1", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", null},
//								  {"counselor2", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", null},
//								  {"counselor3", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", null},
								  {null, "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"sponsor1", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"sponsor2", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"sponsor3", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"user1", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"user2", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"user3", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"admin", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"counselor1", "", "", "", "", "", javax.validation.ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateSavePersonalRecord((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
					                   (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>)testingData[i][6]);
		}
	}
	
	protected void templateSavePersonalRecord(String username, String fullName, String photo, String email, String phone, String linkedInUrl, Class<?> expected){
		Class<?> caught = null;
		
		try{
			super.authenticate(username);
			PersonalRecord personalRecord = this.personalRecordService.create();
			personalRecord.setFullName(fullName);
			personalRecord.setPhoto(photo);
			personalRecord.setEmail(email);
			personalRecord.setPhone(phone);
			personalRecord.setLinkedInUrl(linkedInUrl);
			personalRecord = this.personalRecordService.save(personalRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	//	Se comprueba que se pueden actualizar records, pero no se actualizan si hay algun campo vacio,
	//	algún campo incorrecto o se trata de un actor distinto a los counselors.
	@Test
	public void driverUpdatePersonalRecord(){
		
		Object testingData[][] = {{"counselor1", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", null},
								  {"counselor2", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", null},
								  {"counselor3", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", null},
								  {null, "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"sponsor1", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"sponsor2", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"sponsor3", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"user1", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"user2", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"user3", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"admin", "fullName", "http://www.photo.com", "endorser@email.com", "698756345", "http://www.link.com", IllegalArgumentException.class},
								  {"counselor1", "", "", "", "", "", javax.validation.ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdatePersonalRecord((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
					                   (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>)testingData[i][6]);
		}
	}
	
	protected void templateUpdatePersonalRecord(String username, String fullName, String photo, String email, String phone, String linkedInUrl, Class<?> expected){
		Class<?> caught = null;
		
		try{
			super.authenticate(username);
			PersonalRecord personalRecord = (PersonalRecord) this.personalRecordService.findAll().toArray()[0];
			personalRecord.setFullName(fullName);
			personalRecord.setPhoto(photo);
			personalRecord.setEmail(email);
			personalRecord.setPhone(phone);
			personalRecord.setLinkedInUrl(linkedInUrl);
			personalRecord = this.personalRecordService.save(personalRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testDeleteNotAuthenticated(){
		
		authenticate(null);
		
		PersonalRecord personalRecord = (PersonalRecord) personalRecordService.findAll().toArray()[0];
		
		personalRecordService.delete(personalRecord);
		
		Assert.isTrue(!personalRecordService.findAll().contains(personalRecord));
		
		unauthenticate();
	}
	
	//	Se comprueba que se pueden eliminar records, pero no se  puede eliminar si se trata de un actor distinto a los counselors.
	@Test
	public void driverDeletePersonalRecord(){
		
		Object testingData[][] = {{"bookmaker1", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeletePersonalRecord((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeletePersonalRecord(String username, Class<?> expected){
		Class<?> caught = null;
		
		PersonalRecord personalRecord = (PersonalRecord) personalRecordService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			this.personalRecordService.delete(personalRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}
