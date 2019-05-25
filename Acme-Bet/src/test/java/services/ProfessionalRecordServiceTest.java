package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.ProfessionalRecord;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

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
	
	//	En el test de create, comprobamos que los miscellaneous records
	//	solo los pueden crear las hermandades.
	@Test
	public void driverCreateProfessionalRecord(){
		
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
		
		ProfessionalRecord professionalRecord = professionalRecordService.create();
		
		professionalRecord.setCompanyName("Title miscellaneous record");
		professionalRecord.setAttachment("https://www.attachment.com");
		professionalRecord.setComments("Comment miscellaneous record");
		
		ProfessionalRecord result = professionalRecordService.save(professionalRecord);
		
		Assert.isTrue(professionalRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	En el test de save, comprobamos que los miscellaneous records
	//	solo los pueden guardar las hermandades y también probamos,
	//	que se los datos deben estar rellenados y no vacíos.
	@Test
	public void driverSaveProfessionalRecord(){
		
		Object testingData[][] = {{"counselor1", "title", "description", null},
								  {"counselor2", "title", "description", null},
								  {"counselor3", "title", "description", null},
								  {null, "title", "description", IllegalArgumentException.class},
								  {"sponsor1", "title", "description", IllegalArgumentException.class},
								  {"sponsor2", "title", "description", IllegalArgumentException.class},
								  {"sponsor3", "title", "description", IllegalArgumentException.class},
								  {"user1", "title", "description", IllegalArgumentException.class},
								  {"user2", "title", "description", IllegalArgumentException.class},
								  {"user3", "title", "description", IllegalArgumentException.class},
								  {"admin", "title", "description", IllegalArgumentException.class},
								  {"counselor1", "", "", javax.validation.ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateSaveProfessionalRecord((String) testingData[i][0], (String) testingData[i][1],
									(String) testingData[i][2], (String) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateSaveProfessionalRecord(String username, String companyName, String attachment, String comments, Class<?> expected){
		Class<?> caught = null;
		ProfessionalRecord professionalRecord;
		
		try{
			super.authenticate(username);
			professionalRecord = this.professionalRecordService.create();
			professionalRecord.setCompanyName(companyName);
			professionalRecord.setAttachment(attachment);
			professionalRecord.setComments(comments);
			professionalRecord = this.professionalRecordService.save(professionalRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test 
	public void testUpdate(){
		
		authenticate("counselor1");
		
		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
		
		professionalRecord.setCompanyName("Company name professional record updated");
		professionalRecord.setAttachment("https://www.attachmentUpdated.com");
		professionalRecord.setComments("Comment professional record updated");
		
		ProfessionalRecord result = professionalRecordService.save(professionalRecord);
		
		Assert.isTrue(professionalRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	En el test de update, se comprueba que los miscellaneous records, 
	//	solo los pueden editar las hermandades y que podemos
	//	modificar todos o algunos datos, algunos de los cuales,
	//	al ser incorrectos o vacíos de lugar a error.
	@Test
	public void driverUpdateProfessionalRecord(){
		
		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
		
		Object testingData[][] = {{"counselor1", "title", "https://www.attachment.com", null},
								  {"counselor2", professionalRecord.getCompanyName(), "https://www.attachment.com", null},
								  {"counselor3", "title", professionalRecord.getAttachment(), null},
								  {"sponsor1", "title", "description", IllegalArgumentException.class},
								  {"sponsor2", "title", "description", IllegalArgumentException.class},
								  {"sponsor3", "title", "description", IllegalArgumentException.class},
								  {"user1", "title", "description", IllegalArgumentException.class},
								  {"user2", "title", "description", IllegalArgumentException.class},
								  {"user3", "title", "description", IllegalArgumentException.class},
								  {"admin", "title", "description", IllegalArgumentException.class},
								  {"counselor1", "", "", javax.validation.ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateProfessionalRecord((String) testingData[i][0], (String) testingData[i][1],
											  (String) testingData[i][2], (String) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateUpdateProfessionalRecord(String username, String companyName, String attachment, String comments, Class<?> expected){
		Class<?> caught = null;
		
		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			professionalRecord.setCompanyName(companyName);
			professionalRecord.setAttachment(attachment);
			professionalRecord.setComments(comments);
			professionalRecord = this.professionalRecordService.save(professionalRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testDelete(){
		authenticate("counselor1");
		
		ProfessionalRecord professionalRecord = (ProfessionalRecord) professionalRecordService.findAll().toArray()[0];
		
		professionalRecordService.delete(professionalRecord);
		
		Assert.isTrue(!professionalRecordService.findAll().contains(professionalRecord));
	}
	
	//	En el test delete, se comprueba que solo los counselor 
	//	pueden borrar los miscellaneous records.
	@Test
	public void driverDeleteMiscellaneousRecord(){
		
		Object testingData[][] = {{"counselor1", null},
								  {"counselor2", null},
								  {"counselor3", null}};
//								  {null, IllegalArgumentException.class},
//								  {"sponsor1", IllegalArgumentException.class},
//								  {"sponsor2", IllegalArgumentException.class},
//								  {"sponsor3", IllegalArgumentException.class},
//								  {"bookmaker1", IllegalArgumentException.class},
//								  {"bookmaker2", IllegalArgumentException.class},
//								  {"bookmaker3", IllegalArgumentException.class},
//								  {"user1", IllegalArgumentException.class},
//								  {"user2", IllegalArgumentException.class},
//								  {"user3", IllegalArgumentException.class},
//								  {"admin", IllegalArgumentException.class}};
		
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
