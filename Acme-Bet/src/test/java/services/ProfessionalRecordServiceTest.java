package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

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
import domain.ProfessionalRecord;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	//	Coverage: 88.8%
	//	Covered Instructions: 930
	//	Missed  Instructions: 117
	//	Total   Instructions: 1.047
	
	@Autowired
	private CounselorService counselorService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private ProfessionalRecordService professionalRecordService;
	
	@Test
	public void testCreate() {
		
		authenticate("counselor1");
		
		ProfessionalRecord professionalRecord = professionalRecordService.create();
		
		Assert.isNull(professionalRecord.getCompanyName());
		Assert.isNull(professionalRecord.getStartDate());
		Assert.isNull(professionalRecord.getEndDate());
		Assert.isNull(professionalRecord.getComments());
		Assert.isNull(professionalRecord.getAttachment());
		Assert.isNull(professionalRecord.getRole());
		
		unauthenticate();
	}
	
	@Test
	public void driverCreateProfessionalRecord(){
		
		final Object testingData[][] = {{"counselor1", null},
										{"counselor2", null},
										{"counselor3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateProfessionalRecord((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateProfessionalRecord(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.professionalRecordService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testSave() {
		
		authenticate("counselor1");
		
		Counselor counselor = counselorService.findByPrincipal();
		Curricula curricula = (Curricula) curriculaService.findByCounselor(counselor);
		
		ProfessionalRecord professionalRecord = professionalRecordService.create();
		
		Date startDate  = new Date(System.currentTimeMillis() - 10000);
		Date endDate    = new Date(System.currentTimeMillis() - 10000);
		
		professionalRecord.setCompanyName("Title miscellaneous record");
		professionalRecord.setAttachment("https://www.attachment.com");
		professionalRecord.setComments("Comment miscellaneous record");
		professionalRecord.setStartDate(startDate);
		professionalRecord.setEndDate(endDate);
		professionalRecord.setRole("developer");
		
		ProfessionalRecord result = professionalRecordService.save(professionalRecord, curricula);
		
		Assert.isTrue(professionalRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated() {
		
		authenticate(null);
		
		Counselor counselor = counselorService.findByPrincipal();
		Curricula curricula = (Curricula) curriculaService.findByCounselor(counselor);
		
		ProfessionalRecord professionalRecord = professionalRecordService.create();
		
		Date startDate  = new Date(System.currentTimeMillis() - 10000);
		Date endDate    = new Date(System.currentTimeMillis() - 10000);
		
		professionalRecord.setCompanyName("Title miscellaneous record");
		professionalRecord.setAttachment("https://www.attachment.com");
		professionalRecord.setComments("Comment miscellaneous record");
		professionalRecord.setStartDate(startDate);
		professionalRecord.setEndDate(endDate);
		professionalRecord.setRole("developer");
		
		ProfessionalRecord result = professionalRecordService.save(professionalRecord, curricula);
		
		Assert.isTrue(professionalRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectDate() {
		
		authenticate("counselor1");
		
		Counselor counselor = counselorService.findByPrincipal();
		Curricula curricula = (Curricula) curriculaService.findByCounselor(counselor);
		
		ProfessionalRecord professionalRecord = professionalRecordService.create();
		
		Date startDate  = new Date(System.currentTimeMillis() + 10000);
		Date endDate    = new Date(System.currentTimeMillis() + 10000);
		
		professionalRecord.setCompanyName("Title miscellaneous record");
		professionalRecord.setAttachment("https://www.attachment.com");
		professionalRecord.setComments("Comment miscellaneous record");
		professionalRecord.setStartDate(startDate);
		professionalRecord.setEndDate(endDate);
		professionalRecord.setRole("developer");
		
		ProfessionalRecord result = professionalRecordService.save(professionalRecord, curricula);
		
		Assert.isTrue(professionalRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testSaveIncorrectData() {
		
		authenticate("counselor1");
		
		Counselor counselor = counselorService.findByPrincipal();
		Curricula curricula = (Curricula) curriculaService.findByCounselor(counselor);
		
		ProfessionalRecord professionalRecord = professionalRecordService.create();
		
		Date startDate  = new Date(System.currentTimeMillis() - 10000);
		Date endDate    = new Date(System.currentTimeMillis() - 10000);
		
		professionalRecord.setCompanyName("");
		professionalRecord.setAttachment("https://www.attachment.com");
		professionalRecord.setComments("Comment miscellaneous record");
		professionalRecord.setStartDate(startDate);
		professionalRecord.setEndDate(endDate);
		professionalRecord.setRole("");
		
		ProfessionalRecord result = professionalRecordService.save(professionalRecord, curricula);
		
		Assert.isTrue(professionalRecordService.findAll().contains(result));
		
		unauthenticate();
	}
			
	@Test 
	public void testUpdate(){
		
		authenticate("counselor1");
		
		Counselor counselor = counselorService.findByPrincipal();
		Curricula curricula = (Curricula) curriculaService.findByCounselor(counselor);
		
		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
		
		professionalRecord.setCompanyName("Company name professional record updated");
		professionalRecord.setAttachment("https://www.attachmentUpdated.com");
		professionalRecord.setComments("Comment professional record updated");
		
		ProfessionalRecord result = professionalRecordService.save(professionalRecord, curricula);
		
		Assert.isTrue(professionalRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverUpdateProfessionalRecord(){
		
		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
		
		Object testingData[][] = {{"counselor1", "companyName", "developer", "https://www.attachment.com", "comment", null},
								  {"counselor2", professionalRecord.getCompanyName(), "developer", "https://www.attachment.com", "comment", null},
								  {"counselor3", "companyName", "developer", professionalRecord.getAttachment(), "comment", null},
								  {"counselor1", "companyName", professionalRecord.getRole(), "https://www.attachment.com", "comment", null},
								  {"counselor2", "companyName", "developer", "https://www.attachment.com", professionalRecord.getComments(), null},
								  {"bookmaker1", "companyName", "developer", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"bookmaker2", "companyName", "developer", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"bookmaker3", "companyName", "developer", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"sponsor1", "companyName", "developer", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"sponsor2", "companyName", "developer", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"sponsor3", "companyName", "developer", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"user1", "companyName", "developer", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"user2", "companyName", "developer", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"user3", "companyName", "developer", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"admin", "companyName", "developer", "https://www.attachment.com", "comment", IllegalArgumentException.class},};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateProfessionalRecord((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
											 (String) testingData[i][3], (String) testingData[i][4], (Class<?>)testingData[i][5]);
		}
	}
	
	protected void templateUpdateProfessionalRecord(String username, String companyName, String role, String attachment, String comments, Class<?> expected){
		Class<?> caught = null;
		
		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
		Curricula curricula = (Curricula) curriculaService.findByProfessionalRecord(professionalRecord);
		
		try{
			super.authenticate(username);
			professionalRecord.setCompanyName(companyName);
			professionalRecord.setAttachment(attachment);
			professionalRecord.setComments(comments);
			professionalRecord.setRole(role);
			professionalRecord = this.professionalRecordService.save(professionalRecord, curricula);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testDelete(){
		
		authenticate("bookmaker1");
		
		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
		
		professionalRecordService.delete(professionalRecord);
		
		Assert.isTrue(!professionalRecordService.findAll().contains(professionalRecord));
		
		unauthenticate();
	}
	
	@Test
	public void driverDeleteProfessionalRecord(){
		
		Object testingData[][] = {{"counselor1", null},
								  {"counselor2", null},
								  {"counselor3", null}};
		
		for(int i = 0; i < testingData.length; i++)
			try{
				super.startTransaction();
			templateDeleteProfessionalRecord((String) testingData[i][0], (Class<?>) testingData[i][1]);
			} catch (Throwable oops){
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	
	protected void templateDeleteProfessionalRecord(String username, Class<?> expected){
		Class<?> caught = null;
		
		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			professionalRecordService.delete(professionalRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}
