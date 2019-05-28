package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.EducationRecord;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class EducationRecordServiceTest extends AbstractTest {
	
	@Autowired
	private EducationRecordService educationRecordService;

	@Test
	public void driverCreateEducationRecord(){
		
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
		
		EducationRecord educationRecord = educationRecordService.create();
		
		educationRecord.setStartDate(startDate);
		educationRecord.setEndDate(endDate);
		educationRecord.setDiplomaTitle("diploma title");
		educationRecord.setInstitution("institution");
		educationRecord.setAttachment("http://www.link.com");
		educationRecord.setComments("comments");
		
		EducationRecord result = educationRecordService.save(educationRecord);
		
		Assert.isTrue(educationRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testUpdate(){
		
		authenticate("counselor1");
		
		EducationRecord educationRecord = (EducationRecord) educationRecordService.findAll().toArray()[0];
		
		educationRecord.setDiplomaTitle("updated diploma title");
		educationRecord.setInstitution("updated institution");
		
		EducationRecord result = educationRecordService.save(educationRecord);
		
		Assert.isTrue(educationRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverDeleteEducationRecord(){
		
		Object testingData[][] = {{"counselor1", null},
								  {"counselor2", null},
								  {"counselor3", null}};
		
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