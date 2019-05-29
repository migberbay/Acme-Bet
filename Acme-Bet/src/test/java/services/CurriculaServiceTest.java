package services;

import java.util.ArrayList;
import java.util.Collection;

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
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	@Autowired
	private CounselorService counselorService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private PersonalRecordService personalRecordService;
	
	@Autowired
	private EndorserRecordService endorserRecordService;
	
	@Autowired
	private EducationRecordService educationRecordService;
	
	@Autowired
	private ProfessionalRecordService professionalRecordService;
	
	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;
	
	@Test
	public void driverCreateHistory() {
		Object testingData[][] = {{"counselor1", null},
								  {"counselor2", null},
				                  {"counselor3", null}};
		
		for (int i = 0; i < testingData.length; i++) {
			
			this.templateCreateHistory((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}
	
	protected void templateCreateHistory(String username, Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(username);
			this.curriculaService.create();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testSave(){
		authenticate("counselor1");
		
		Curricula curricula = curriculaService.create();
		
		Collection<EndorserRecord> endorserRecords = new ArrayList<>();
		Collection<EducationRecord> educationRecords = new ArrayList<>();
		Collection<ProfessionalRecord> professionalRecords = new ArrayList<>();
		Collection<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>();
		
		Counselor counselor = (Counselor) counselorService.findAll().toArray()[0];
		 
		PersonalRecord personalRecord = (PersonalRecord) personalRecordService.findAll().toArray()[0];
		EndorserRecord endorserRecord = (EndorserRecord) endorserRecordService.findAll().toArray()[0];
		EducationRecord educationRecord = (EducationRecord) educationRecordService.findAll().toArray()[0];
		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
		
		endorserRecords.add(endorserRecord);
		educationRecords.add(educationRecord);
		professionalRecords.add(professionalRecord);
		miscellaneousRecords.add(miscellaneousRecord);
		
		curricula.setPersonalRecord(personalRecord);
		curricula.setEndorserRecords(endorserRecords);
		curricula.setEducationRecords(educationRecords);
		curricula.setProfessionalRecords(professionalRecords);
		curricula.setMiscellaneousRecords(miscellaneousRecords);
		
		Curricula result = curriculaService.save(curricula);
		
		Assert.isTrue(curriculaService.findAll().contains(result));
		
		unauthenticate();
	}
	
//	@Test
//	public void driverSaveHistory(){
//		
//		Object testingData[][] = {{"counselor1", null},
//								  {"counselor2", null},
//								  {"counselor3", null},};
//								  {"user1", org.springframework.dao.DataIntegrityViolationException.class},
//				                  {"user2", org.springframework.dao.DataIntegrityViolationException.class},
//				                  {"user3", org.springframework.dao.DataIntegrityViolationException.class},};
//								  {"sponsor1", org.springframework.dao.DataIntegrityViolationException.class},
//								  {"sponsor2", org.springframework.dao.DataIntegrityViolationException.class},
//								  {"sponsor3", org.springframework.dao.DataIntegrityViolationException.class}};
//		
//		for(int i = 0; i < testingData.length; i++){
//			templateSaveHistory((String) testingData[i][0], (Class<?>)testingData[i][1]);
//		}
//	}
//	
//	protected void templateSaveHistory(String username,  Class<?> expected){
//		Class<?> caught = null;
//		
//		Counselor counselor = (Counselor) counselorService.findAll().toArray()[0];
//		PersonalRecord personalRecord = (PersonalRecord) personalRecordService.findAll().toArray()[0];
//		EndorserRecord endorserRecord = (EndorserRecord) endorserRecordService.findAll().toArray()[0];
//		EducationRecord educationRecord = (EducationRecord) educationRecordService.findAll().toArray()[0];
//		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
//		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
//	
//		try{
//			super.authenticate(username);
//			Curricula curricula = curriculaService.create();
//			curricula.setPersonalRecord(personalRecord);
//			curricula.getEndorserRecords().add(endorserRecord);
//			curricula.getEducationRecords().add(educationRecord);
//			curricula.getProfessionalRecords().add(professionalRecord);
//			curricula.getMiscellaneousRecords().add(miscellaneousRecord);
//			curriculaService.save(curricula);
//			unauthenticate();
//		} catch (Throwable oops){
//			caught = oops.getClass();
//		}
//		
//		this.checkExceptions(expected, caught);
//		super.unauthenticate();
//	}
	
//	@Test
//	public void driverUpdateHistory(){
//		
//		Object testingData[][] = {{"counselor1", null},
//								  {"counselor2", null},
//								  {"counselor3", null},};
//								  {"user1", org.springframework.dao.DataIntegrityViolationException.class},
//				                  {"user2", org.springframework.dao.DataIntegrityViolationException.class},
//				                  {"user3", org.springframework.dao.DataIntegrityViolationException.class},};
//								  {"sponsor1", org.springframework.dao.DataIntegrityViolationException.class},
//								  {"sponsor2", org.springframework.dao.DataIntegrityViolationException.class},
//								  {"sponsor3", org.springframework.dao.DataIntegrityViolationException.class}};
//		
//		for(int i = 0; i < testingData.length; i++){
//			templateUpdateHistory((String) testingData[i][0], (Class<?>)testingData[i][1]);
//		}
//	}
//	
//	protected void templateUpdateHistory(String username,  Class<?> expected){
//		Class<?> caught = null;
//		
//		Counselor counselor = (Counselor) counselorService.findAll().toArray()[0];
//		Curricula curricula = (Curricula) curriculaService.findAll().toArray()[0];
//		EndorserRecord endorserRecord = (EndorserRecord) endorserRecordService.findAll().toArray()[0];
//		EducationRecord educationRecord = (EducationRecord) educationRecordService.findAll().toArray()[0];
//		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
//		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
//	
//		try{
//			super.authenticate(username);
//			curricula.setCounselor(counselor);
//			curricula.getEndorserRecords().add(endorserRecord);
//			curricula.getEducationRecords().add(educationRecord);
//			curricula.getProfessionalRecords().add(professionalRecord);
//			curricula.getMiscellaneousRecords().add(miscellaneousRecord);
//			curriculaService.save(curricula);
//			unauthenticate();
//		} catch (Throwable oops){
//			caught = oops.getClass();
//		}
//		
//		this.checkExceptions(expected, caught);
//		super.unauthenticate();
//	}
//	
}
