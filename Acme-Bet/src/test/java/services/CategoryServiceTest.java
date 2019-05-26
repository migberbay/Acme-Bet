package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Category;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	@Autowired
	private CategoryService categoryService;
	
	@Test
	public void driverCreateCategory(){
		
		final Object testingData[][] = {{"admin", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateCreateCategory((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateCreateCategory(String username, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			this.categoryService.create();
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void testCreateNotAuthenticated(){
//		
//		authenticate(null);
//		
//		Category category = categoryService.create();
//		
//		Assert.isNull(category.getSpanishName());
//		Assert.isNull(category.getEnglishName());
//		Assert.isNull(category.getType());
//
//		unauthenticate();
//	}
	
	@Test
	public void driverSaveCategory(){
		
		final Object testingData[][] = {{"admin", "spanishName", "englishName", "REQUEST", null},
										{"admin", "", "", "", ConstraintViolationException.class},
										{"admin", "spanishName", "", "", ConstraintViolationException.class},
										{"admin", "", "englishName", "", ConstraintViolationException.class},
										{"admin", "spanishName", "englishName", "example", ConstraintViolationException.class},
										{"user1", "spanishName", "englishName", "REQUEST", ConstraintViolationException.class},
										{"user2", "spanishName", "englishName", "REQUEST", ConstraintViolationException.class},
										{"user3", "spanishName", "englishName", "REQUEST", ConstraintViolationException.class}, 
										{"bookmaker1", "spanishName", "englishName", "POOL", ConstraintViolationException.class},
										{"bookmaker2", "spanishName", "englishName", "POOL", ConstraintViolationException.class},
										{"counselor1", "spanishName", "englishName", "POOL", ConstraintViolationException.class},
										{"counselor2", "spanishName", "englishName", "POOL", ConstraintViolationException.class}};
		
		for(int i = 0; i < testingData.length; i++){
			templateSaveCategory((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateSaveCategory(String username, String spanishName, String englishName, String type, Class<?> expected){
		Class<?> caught = null;

		try{
			super.authenticate(username);
			Category category = categoryService.create();
			category.setSpanishName(spanishName);
			category.setEnglishName(englishName);
			category.setType(type);
			categoryService.save(category);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void driverUpdateCategory(){
		
		Category category = (Category) categoryService.findAll().toArray()[0];
		
		final Object testingData[][] = {{"admin", "spanishName", "englishName", "POOL", null},
										{"admin", category.getSpanishName(), "englishName", "POOL", null},
										{"admin", "spanishName", category.getEnglishName(), "POOL", null},
										{"admin", "spanishName", "englishName", category.getType(), null},
										{"admin", category.getSpanishName(), "englishName", category.getType(), null},
										{"admin", "spanishName", category.getEnglishName(), category.getType(), null},
										{"admin", category.getSpanishName(), category.getEnglishName(), "REQUEST", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateUpdateCategory((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>)testingData[i][4]);
		}
	}
	
	protected void templateUpdateCategory(String username, String spanishName, String englishName, String type, Class<?> expected){
		Class<?> caught = null;
		
		Category category = (Category) categoryService.findAll().toArray()[0];

		try{
			super.authenticate(username);
			category.setSpanishName(spanishName);
			category.setEnglishName(englishName);
			category.setType(type);
			categoryService.save(category);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test
	public void driverDeleteCategory(){
		
		final Object testingData[][] = {{"admin", null}};
		
		for(int i = 0; i < testingData.length; i++){
			templateDeleteCategory((String) testingData[i][0], (Class<?>)testingData[i][1]);
		}
	}
	
	protected void templateDeleteCategory(String username, Class<?> expected){
		Class<?> caught = null;

		Category category = (Category) categoryService.findAll().toArray()[5];
		
		try{
			super.authenticate(username);
			this.categoryService.delete(category);
		} catch (Throwable oops){
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testDeleteCategoryOnBet(){
		
		authenticate("admin");
		
		Category category = (Category) categoryService.findAll().toArray()[0];
		
		categoryService.delete(category);
		
		Assert.isTrue(!categoryService.findAll().contains(category));
		
		unauthenticate();
	}
}
