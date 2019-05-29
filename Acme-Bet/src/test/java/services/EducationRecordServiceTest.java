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

import domain.Counselor;
import domain.Curricula;
import domain.EducationRecord;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class EducationRecordServiceTest extends AbstractTest {
	
	//	Coverage: 91.3%
	//	Covered Instructions: 879
	//	Missed  Instructions: 84
	//	Total   Instructions: 963
	
	@Autowired
	private CounselorService counselorService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private EducationRecordService educationRecordService;

	@Test
	public void driverCreateEducationRecord(){
		
		final Object testingData[][] = {{"counselor1", null},
										{"counselor2", null},
										{"counselor3", null},};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateEducationRecord((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateEducationRecord(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.educationRecordService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testSave(){
		
		authenticate("counselor1");
		
		Date startDate = new Date(System.currentTimeMillis() - 1000);
		Date endDate   = new Date(System.currentTimeMillis() - 1000);
		
		Counselor counselor = counselorService.findByPrincipal();
		EducationRecord educationRecord = educationRecordService.create();
		Curricula curricula = curriculaService.findByCounselor(counselor);
		
		educationRecord.setStartDate(startDate);
		educationRecord.setEndDate(endDate);
		educationRecord.setDiplomaTitle("diploma title");
		educationRecord.setInstitution("institution");
		educationRecord.setAttachment("http://www.link.com");
		educationRecord.setComments("comments");
		
		EducationRecord result = educationRecordService.save(educationRecord, curricula);
		
		Assert.isTrue(educationRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated(){
		
		authenticate(null);
		
		Date startDate = new Date(System.currentTimeMillis() - 1000);
		Date endDate   = new Date(System.currentTimeMillis() - 1000);
		
		Counselor counselor = counselorService.findByPrincipal();
		EducationRecord educationRecord = educationRecordService.create();
		Curricula curricula = curriculaService.findByCounselor(counselor);
		
		educationRecord.setStartDate(startDate);
		educationRecord.setEndDate(endDate);
		educationRecord.setDiplomaTitle("diploma title");
		educationRecord.setInstitution("institution");
		educationRecord.setAttachment("http://www.link.com");
		educationRecord.setComments("comments");
		
		EducationRecord result = educationRecordService.save(educationRecord, curricula);
		
		Assert.isTrue(educationRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectDate(){
		
		authenticate("counselor1");
		
		Date startDate = new Date(System.currentTimeMillis() + 1000);
		Date endDate   = new Date(System.currentTimeMillis() + 1000);
		
		Counselor counselor = counselorService.findByPrincipal();
		EducationRecord educationRecord = educationRecordService.create();
		Curricula curricula = curriculaService.findByCounselor(counselor);
		
		educationRecord.setStartDate(startDate);
		educationRecord.setEndDate(endDate);
		educationRecord.setDiplomaTitle("diploma title");
		educationRecord.setInstitution("institution");
		educationRecord.setAttachment("http://www.link.com");
		educationRecord.setComments("comments");
		
		EducationRecord result = educationRecordService.save(educationRecord, curricula);
		
		Assert.isTrue(educationRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData(){
		
		authenticate("counselor1");
		
		Date startDate = new Date(System.currentTimeMillis() - 1000);
		Date endDate   = new Date(System.currentTimeMillis() - 1000);
		
		Counselor counselor = counselorService.findByPrincipal();
		EducationRecord educationRecord = educationRecordService.create();
		Curricula curricula = curriculaService.findByCounselor(counselor);
		
		educationRecord.setStartDate(startDate);
		educationRecord.setEndDate(endDate);
		educationRecord.setDiplomaTitle("");
		educationRecord.setInstitution("");
		educationRecord.setAttachment("http://www.link.com");
		educationRecord.setComments("comments");
		
		EducationRecord result = educationRecordService.save(educationRecord, curricula);
		
		Assert.isTrue(educationRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testUpdate(){
		
		authenticate("counselor1");
		
		EducationRecord educationRecord = (EducationRecord) educationRecordService.findAll().toArray()[0];
		Curricula curricula = curriculaService.findByEducationRecord(educationRecord);
		
		educationRecord.setDiplomaTitle("updated diploma title");
		educationRecord.setInstitution("updated institution");
		
		EducationRecord result = educationRecordService.save(educationRecord, curricula);
		
		Assert.isTrue(educationRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverUpdateEducationRecord(){
		
		EducationRecord educationRecord = (EducationRecord) educationRecordService.findAll().toArray()[0];
		
		Object testingData[][] = {{"counselor1", "diplomaTitle", "institution", "https://www.attachment.com", "comment", null},
								  {"counselor2", educationRecord.getDiplomaTitle(), "institution", "https://www.attachment.com", "comment", null},
								  {"counselor3", "diplomaTitle", "institution", educationRecord.getAttachment(), "comment", null},
								  {"counselor1", "diplomaTitle", educationRecord.getInstitution(), "https://www.attachment.com", "comment", null},
								  {"counselor2", "diplomaTitle", "institution", "https://www.attachment.com", educationRecord.getComments(), null},
								  {"bookmaker1", "diplomaTitle", "institution", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"bookmaker2", "diplomaTitle", "institution", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"bookmaker3", "diplomaTitle", "institution", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"sponsor1", "diplomaTitle", "institution", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"sponsor2", "diplomaTitle", "institution", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"sponsor3", "diplomaTitle", "institution", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"user1", "diplomaTitle", "institution", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"user2", "diplomaTitle", "institution", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"user3", "diplomaTitle", "institution", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"admin", "diplomaTitle", "institution", "https://www.attachment.com", "comment", IllegalArgumentException.class},};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateEducationRecord((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
											 (String) testingData[i][3], (String) testingData[i][4], (Class<?>)testingData[i][5]);
		}
	}
	
	protected void templateUpdateEducationRecord(String username, String diplomaTitle, String institution, String attachment, String comments, Class<?> expected){
		Class<?> caught = null;
		
		EducationRecord educationRecord = (EducationRecord) educationRecordService.findAll().toArray()[0];
		Counselor counselor = (Counselor) counselorService.findAll().toArray()[0];
		Curricula curricula = (Curricula) curriculaService.findByCounselor(counselor);
		
		try{
			super.authenticate(username);
			educationRecord.setDiplomaTitle(diplomaTitle);
			educationRecord.setInstitution(institution);
			educationRecord.setComments(comments);
			educationRecord.setAttachment(attachment);
			educationRecord = this.educationRecordService.save(educationRecord, curricula);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
//	@Test
//	public void testDelete(){
//		
//		authenticate("bookmaker1");
//		
//		EducationRecord educationRecord = (EducationRecord) educationRecordService.findAll().toArray()[0];
//		
//		educationRecordService.delete(educationRecord);
//		
//		Assert.isTrue(!educationRecordService.findAll().contains(educationRecord));
//		
//		unauthenticate();
//	}
	
	@Test
	public void driverDeleteEducationRecord(){
		
		Object testingData[][] = {{"bookmaker1", null}};
//								  {"bookmaker2", ConstraintViolationException.class},
//								  {"bookmaker3", ConstraintViolationException.class}};
//								  {"user1", DataIntegrityViolationException.class},
//								  {"user2", DataIntegrityViolationException.class},
//								  {"user3", DataIntegrityViolationException.class},
//								  {"admin", DataIntegrityViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeleteEducationRecord((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeleteEducationRecord(String username, Class<?> expected){
		Class<?> caught = null;
		
		EducationRecord educationRecord = (EducationRecord) educationRecordService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			this.educationRecordService.delete(educationRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}