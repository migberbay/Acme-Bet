package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Warranty;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	//	Coverage: 97.7%
	//	Covered Instructions: 904
	//	Missed  Instructions: 21
	//	Total   Instructions: 925
	
	@Autowired
	private WarrantyService warrantyService;

	@Test
	public void testCreate() {

		Warranty warranty = warrantyService.create();

		Assert.notNull(warranty.getLaws());
		Assert.isNull(warranty.getTitle());
		Assert.isNull(warranty.getTerms());
		Assert.isTrue(warranty.getIsFinal().equals(false));
	}
	
	@Test
	public void driverCreateWarranty(){
		
		final Object testingData[][] = {{"admin", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateWarranty((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateWarranty(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.warrantyService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}

	@Test
	public void testSave() {

		super.authenticate("admin");

		Warranty warranty = warrantyService.create();

		Collection<String> laws = new ArrayList<String>();
		String law1 = "law1";
		String law2 = "law2";
		String law3 = "law3";
		laws.add(law1);
		laws.add(law2);
		laws.add(law3);

		warranty.setTitle("title warranty");
		warranty.setTerms("terms warranty");
		warranty.setLaws(laws);

		Warranty result = warrantyService.save(warranty);

		Assert.isTrue(warrantyService.findAll().contains(result));
		super.unauthenticate();
	}
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void driverSaveWarranty(){
		
		Collection<String> empty = new ArrayList<String>();
		Collection<String> laws = new ArrayList<String>();
		String law1 = "law1";
		String law2 = "law2";
		String law3 = "law3";
		laws.add(law1);
		laws.add(law2);
		laws.add(law3);
		
		final Object testingData[][] = {{"admin", "title", "terms", laws, false, null},
										{"admin", "title", "terms", laws, true,  null},
										{"admin", "", "terms", laws, false, ConstraintViolationException.class},
										{"admin", "title", "", laws, false, ConstraintViolationException.class},
										{"admin", "title", "terms", empty, false, ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateSaveWarranty((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
					  (Collection<String>) testingData[i][3], (Boolean) testingData[i][4], (Class<?>)testingData[i][5]);
		}
	}
	
	protected void templateSaveWarranty(String username, String title, String terms, Collection<String> laws, Boolean isFinal, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			Warranty warranty = warrantyService.create();
			warranty.setTitle(title);
			warranty.setTerms(terms);
			warranty.setLaws(laws);
			warranty.setIsFinal(isFinal);
			warrantyService.save(warranty);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void testUpdate(){
		
		super.authenticate("admin");
		
		Warranty warranty = (Warranty) warrantyService.findAll().toArray()[0];
		
		warranty.setTitle("Updated title warranty");
		warranty.setTerms("Updated terms warranty");
		
		Warranty result = warrantyService.save(warranty);

		Assert.isTrue(warrantyService.findAll().contains(result));
		super.unauthenticate();
		
	}
	
	@Test
	public void driverUpdateWarranty(){
		
		Warranty warranty = (Warranty) warrantyService.findAll().toArray()[1];
		
		final Object testingData[][] = {{"admin", "title", "terms", false, null},
										{"admin", warranty.getTitle(), "terms", false, null},
										{"admin", "title", warranty.getTerms(), false, null},
										{null, "title", "terms", false, IllegalArgumentException.class},
										{"bookmaker1", "title", "terms", false, IllegalArgumentException.class},
										{"bookmaker2", "title", "terms", false, IllegalArgumentException.class},
										{"bookmaker3", "title", "terms", false, IllegalArgumentException.class},
										{"counselor1", "title", "terms", false, IllegalArgumentException.class},
										{"counselor2", "title", "terms", false, IllegalArgumentException.class},
										{"counselor3", "title", "terms", false, IllegalArgumentException.class},
										{"user1", "title", "terms", false, IllegalArgumentException.class},
										{"user2", "title", "terms", false, IllegalArgumentException.class},
										{"user3", "title", "terms", false, IllegalArgumentException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateWarranty((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Boolean) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateUpdateWarranty(String username, String title, String terms, Boolean isFinal, Class<?> expected){
		Class<?> caught = null;

		Warranty warranty = (Warranty) warrantyService.findAll().toArray()[1];
		
		try{
			super.authenticate(username);
			warranty.setTitle(title);
			warranty.setTerms(terms);
			warranty.setIsFinal(isFinal);
			warrantyService.save(warranty);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testDelete() {

		super.authenticate("admin");

		Warranty warranty = (Warranty) warrantyService.findAll().toArray()[0];

		warrantyService.delete(warranty);

		Assert.isTrue(!warrantyService.findAll().contains(warranty));

		super.unauthenticate();
	}
	
	@Test
	public void driverDeleteWarranty(){
		
		final Object testingData[][] = {{"admin", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeleteWarranty((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeleteWarranty(String username, Class<?> expected){
		Class<?> caught = null;

		Warranty warranty = (Warranty) warrantyService.findAll().toArray()[1];
		
		try{
			super.authenticate(username);
			this.warrantyService.delete(warranty);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}
