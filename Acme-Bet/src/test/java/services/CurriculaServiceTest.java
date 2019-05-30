package services;

//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Counselor;
import domain.Curricula;
//import domain.EducationRecord;
//import domain.EndorserRecord;
//import domain.MiscellaneousRecord;
import domain.PersonalRecord;
//import domain.ProfessionalRecord;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	//	Coverage: 72.2%
	//	Covered Instructions: 205
	//	Missed  Instructions: 79
	//	Total   Instructions: 284
	
	@Autowired
	private CounselorService counselorService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private PersonalRecordService personalRecordService;
	
//	@Autowired
//	private EndorserRecordService endorserRecordService;
	
//	@Autowired
//	private EducationRecordService educationRecordService;
	
//	@Autowired
//	private ProfessionalRecordService professionalRecordService;
	
//	@Autowired
//	private MiscellaneousRecordService miscellaneousRecordService;
	
	@Test
	public void driverCreateCurricula() {
		Object testingData[][] = {{"counselor1", null},
								  {"counselor2", null},
				                  {"counselor3", null}};
		
		for (int i = 0; i < testingData.length; i++) {
			
			this.templateCreateCurricula((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}
	
	protected void templateCreateCurricula(String username, Class<?> expected) {
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
		
//		Collection<EndorserRecord> endorserRecords = new ArrayList<>();
//		Collection<EducationRecord> educationRecords = new ArrayList<>();
//		Collection<ProfessionalRecord> professionalRecords = new ArrayList<>();
//		Collection<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>();
		
//		Counselor counselor = (Counselor) counselorService.findByPrincipal();
		 
//		PersonalRecord personalRecord = (PersonalRecord) personalRecordService.findAll().toArray()[0];
//		EndorserRecord endorserRecord = (EndorserRecord) endorserRecordService.findAll().toArray()[0];
//		EducationRecord educationRecord = (EducationRecord) educationRecordService.findAll().toArray()[0];
//		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
//		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
		
//		endorserRecords.add(endorserRecord);
//		educationRecords.add(educationRecord);
//		professionalRecords.add(professionalRecord);
//		miscellaneousRecords.add(miscellaneousRecord);
		
//		curricula.setPersonalRecord(personalRecord);
//		curricula.setEndorserRecords(endorserRecords);
//		curricula.setEducationRecords(educationRecords);
//		curricula.setProfessionalRecords(professionalRecords);
//		curricula.setMiscellaneousRecords(miscellaneousRecords);
//		curricula.setCounselor(counselor);
		
		PersonalRecord personalRecord = this.personalRecordService.create();
		personalRecord.setFullName("name");
		personalRecord.setPhoto("http://www.photo.com");
		personalRecord.setEmail("endorser@email.com");
		personalRecord.setPhone("698756345");
		personalRecord.setLinkedInUrl("http://www.link.com");
		
		PersonalRecord saved = personalRecordService.save(personalRecord);
		
		curricula.setPersonalRecord(saved);
		
		Curricula result = curriculaService.save(curricula);
		
		Assert.isTrue(curriculaService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveNotAuthenticated(){
		
		authenticate(null);
		
		Curricula curricula = curriculaService.create();
		
		PersonalRecord personalRecord = this.personalRecordService.create();
		personalRecord.setFullName("name");
		personalRecord.setPhoto("http://www.photo.com");
		personalRecord.setEmail("endorser@email.com");
		personalRecord.setPhone("698756345");
		personalRecord.setLinkedInUrl("http://www.link.com");
		
		PersonalRecord saved = personalRecordService.save(personalRecord);
		
		curricula.setPersonalRecord(saved);
		
		Curricula result = curriculaService.save(curricula);
		
		Assert.isTrue(curriculaService.findAll().contains(result));
		
		unauthenticate();
	}
	
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNotAuthenticated(){
    	
    	authenticate(null);
    	
    	Counselor counselor = counselorService.findByPrincipal();
    	Curricula curricula = curriculaService.findByCounselor(counselor);
    	
    	curriculaService.delete(curricula);
    	
    	Assert.isTrue(!curriculaService.findAll().contains(curricula));
    	
    	unauthenticate();
    }
	
    @Test
	public void driverDeleteCurricula() {
		Object testingData[][] = {{"counselor1", null},
								  {"counselor2", null},
				                  {"counselor3", null}};
		
		for (int i = 0; i < testingData.length; i++) {
			
			this.templateDeleteCurricula((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}
	
	protected void templateDeleteCurricula(String username, Class<?> expected) {
		Class<?> caught = null;

		Curricula curricula = (Curricula) curriculaService.findAll().toArray()[0];
		try {
			super.authenticate(username);
			this.curriculaService.delete(curricula);
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}	
}
