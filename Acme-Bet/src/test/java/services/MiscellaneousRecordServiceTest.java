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
import domain.MiscellaneousRecord;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	//	Coverage: 97.7%
	//	Covered Instructions: 1.073
	//	Missed  Instructions: 25
	//	Total   Instructions: 1.098
	
	@Autowired
	private CounselorService counselorService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;
	
	//	F.R. 32.3: Manage the records of their curricula, which includes listing, showing, creating, updating and deleting them.
	
	//	Se comprueba que los counselors pueden crear records.
	@Test
	public void testCreate() {
		
		authenticate("counselor1");
		
		MiscellaneousRecord miscellaneousRecord = miscellaneousRecordService.create();
		
		Assert.isNull(miscellaneousRecord.getTitle());
		Assert.isNull(miscellaneousRecord.getAttachment());
		Assert.isNull(miscellaneousRecord.getComments());
		
		unauthenticate();
	}
	
	@Test
	public void driverCreateMiscellaneousRecord(){
		
		final Object testingData[][] = {{"counselor1", null},
										{"counselor2", null},
										{"counselor3", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateMiscellaneousRecord((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateMiscellaneousRecord(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.miscellaneousRecordService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	//	Se comprueba que se pueden guardar records, pero no se guardan si hay algun campo vacio,
	//	algún campo incorrecto o se trata de un actor distinto a los counselors.
	@Test
	public void testSave() {
		
		authenticate("counselor1");
		
		Counselor counselor = counselorService.findByPrincipal();
		Curricula curricula = (Curricula) curriculaService.findByCounselor(counselor);
		
		MiscellaneousRecord miscellaneousRecord = miscellaneousRecordService.create();
		
		miscellaneousRecord.setTitle("Title miscellaneous record");
		miscellaneousRecord.setAttachment("https://www.attachment.com");
		miscellaneousRecord.setComments("Comment miscellaneous record");
		
		MiscellaneousRecord result = miscellaneousRecordService.save(miscellaneousRecord, curricula);
		
		Assert.isTrue(miscellaneousRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverSaveMiscellaneousRecord(){
		
		Object testingData[][] = {{"counselor1", "title", "https://www.attachment.com", "comment", null},
								  {"counselor2", "title", "https://www.attachment.com", "comment", null},
								  {"counselor3", "title", "https://www.attachment.com", "comment", null},
								  {null, "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"sponsor1", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"sponsor2", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"sponsor3", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"user1", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"user2", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"user3", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"admin", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
								  {"counselor1", "", "", "", javax.validation.ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateSaveMiscellaneousRecord((String) testingData[i][0], (String) testingData[i][1],
									(String) testingData[i][2], (String) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateSaveMiscellaneousRecord(String username, String title, String attachment, String comments, Class<?> expected){
		Class<?> caught = null;
		
		Counselor counselor = (Counselor) counselorService.findAll().toArray()[0];
		Curricula curricula = (Curricula) curriculaService.findByCounselor(counselor);
		
		try{
			super.authenticate(username);
			MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
			miscellaneousRecord.setTitle(title);
			miscellaneousRecord.setAttachment(attachment);
			miscellaneousRecord.setComments(comments);
			miscellaneousRecord = this.miscellaneousRecordService.save(miscellaneousRecord, curricula);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	//	Se comprueba que se pueden actualizar records, pero no se actualizan si hay algun campo vacio,
	//	algún campo incorrecto o se trata de un actor distinto a los counselors.
	@Test 
	public void testUpdate(){
		
		authenticate("counselor1");
		
		Counselor counselor = counselorService.findByPrincipal();
		Curricula curricula = (Curricula) curriculaService.findByCounselor(counselor);
		
		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
		
		miscellaneousRecord.setTitle("Title miscellaneous record updated");
		miscellaneousRecord.setAttachment("https://www.attachmentUpdated.com");
		miscellaneousRecord.setComments("Comment miscellaneous record updated");
		
		MiscellaneousRecord result = miscellaneousRecordService.save(miscellaneousRecord, curricula);
		
		Assert.isTrue(miscellaneousRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverUpdateMiscellaneousRecord(){
		
		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
		
		Object testingData[][] = {{"counselor1", "title", "https://www.attachment.com", "comment", null},
				  				  {"counselor2", "title", "https://www.attachment.com", "comment", null},
				  				  {"counselor3", "title", "https://www.attachment.com", "comment", null},
				  				  {"counselor1", miscellaneousRecord.getTitle(), "https://www.attachment.com", "comment", null},
				  				  {"counselor2", "title", miscellaneousRecord.getAttachment(), "comment", null},
				  				  {"counselor3", "title", "https://www.attachment.com", miscellaneousRecord.getComments(), null},
				  				  {null, "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
				  				  {"sponsor1", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
				  				  {"sponsor2", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
				  				  {"sponsor3", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
				  				  {"user1", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
				  				  {"user2", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
				  				  {"user3", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class},
				  				  {"admin", "title", "https://www.attachment.com", "comment", IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateMiscellaneousRecord((String) testingData[i][0], (String) testingData[i][1],
											  (String) testingData[i][2], (String) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateUpdateMiscellaneousRecord(String username, String title, String attachment, String comments, Class<?> expected){
		Class<?> caught = null;
		
		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
		Curricula curricula = (Curricula) curriculaService.findByMiscellaneousRecord(miscellaneousRecord);
		
		try{
			super.authenticate(username);
			miscellaneousRecord.setTitle(title);
			miscellaneousRecord.setAttachment(attachment);
			miscellaneousRecord.setComments(comments);
			miscellaneousRecord = this.miscellaneousRecordService.save(miscellaneousRecord, curricula);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testDeleteNotAuthenticated(){
		authenticate(null);
		
		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
		
		miscellaneousRecordService.delete(miscellaneousRecord);
		
		Assert.isTrue(!miscellaneousRecordService.findAll().contains(miscellaneousRecord));
	}
	
	//	Se comprueba que se pueden eliminar records, pero no se  puede eliminar si se trata de un actor distinto a los counselors.
	@Test
	public void driverDeleteMiscellaneousRecord(){
		
		Object testingData[][] = {{"counselor1", null},
								  {"counselor2", null},
								  {"counselor3", null}};
		
		for(int i = 0; i < testingData.length; i++)
			try{
				super.startTransaction();
			templateDeleteMiscellaneousRecord((String) testingData[i][0], (Class<?>) testingData[i][1]);
			} catch (Throwable oops){
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	
	protected void templateDeleteMiscellaneousRecord(String username, Class<?> expected){
		Class<?> caught = null;
		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			miscellaneousRecordService.delete(miscellaneousRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}