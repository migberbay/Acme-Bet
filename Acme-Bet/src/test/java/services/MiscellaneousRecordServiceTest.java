package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.MiscellaneousRecord;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;
	
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
	
	@Test
	public void testSave() {
		
		authenticate("counselor1");
		
		MiscellaneousRecord miscellaneousRecord = miscellaneousRecordService.create();
		
		miscellaneousRecord.setTitle("Title miscellaneous record");
		miscellaneousRecord.setAttachment("https://www.attachment.com");
		miscellaneousRecord.setComments("Comment miscellaneous record");
		
		MiscellaneousRecord result = miscellaneousRecordService.save(miscellaneousRecord);
		
		Assert.isTrue(miscellaneousRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverSaveMiscellaneousRecord(){
		
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
			templateSaveMiscellaneousRecord((String) testingData[i][0], (String) testingData[i][1],
									(String) testingData[i][2], (String) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateSaveMiscellaneousRecord(String username, String title, String attachment, String comments, Class<?> expected){
		Class<?> caught = null;
		
		try{
			super.authenticate(username);
			MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
			miscellaneousRecord.setTitle(title);
			miscellaneousRecord.setAttachment(attachment);
			miscellaneousRecord.setComments(comments);
			miscellaneousRecord = this.miscellaneousRecordService.save(miscellaneousRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test 
	public void testUpdate(){
		
		authenticate("counselor1");
		
		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
		
		miscellaneousRecord.setTitle("Title miscellaneous record updated");
		miscellaneousRecord.setAttachment("https://www.attachmentUpdated.com");
		miscellaneousRecord.setComments("Comment miscellaneous record updated");
		
		MiscellaneousRecord result = miscellaneousRecordService.save(miscellaneousRecord);
		
		Assert.isTrue(miscellaneousRecordService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void driverUpdateMiscellaneousRecord(){
		
		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
		
		Object testingData[][] = {{"counselor1", "title", "https://www.attachment.com", null},
								  {"counselor2", miscellaneousRecord.getTitle(), "https://www.attachment.com", null},
								  {"counselor3", "title", miscellaneousRecord.getAttachment(), null},
								  {"sponsor1", "title", "description", IllegalArgumentException.class},
								  {"sponsor2", "title", "description", IllegalArgumentException.class},
								  {"sponsor3", "title", "description", IllegalArgumentException.class},
								  {"user1", "title", "description", IllegalArgumentException.class},
								  {"user2", "title", "description", IllegalArgumentException.class},
								  {"user3", "title", "description", IllegalArgumentException.class},
								  {"admin", "title", "description", IllegalArgumentException.class},
								  {"counselor1", "", "", javax.validation.ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateMiscellaneousRecord((String) testingData[i][0], (String) testingData[i][1],
											  (String) testingData[i][2], (String) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateUpdateMiscellaneousRecord(String username, String title, String attachment, String comments, Class<?> expected){
		Class<?> caught = null;
		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
		
		try{
			super.authenticate(username);
			miscellaneousRecord.setTitle(title);
			miscellaneousRecord.setAttachment(attachment);
			miscellaneousRecord.setComments(comments);
			miscellaneousRecord = this.miscellaneousRecordService.save(miscellaneousRecord);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testDelete(){
		authenticate("counselor1");
		
		MiscellaneousRecord miscellaneousRecord = (MiscellaneousRecord) miscellaneousRecordService.findAll().toArray()[0];
		
		miscellaneousRecordService.delete(miscellaneousRecord);
		
		Assert.isTrue(!miscellaneousRecordService.findAll().contains(miscellaneousRecord));
	}
	
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