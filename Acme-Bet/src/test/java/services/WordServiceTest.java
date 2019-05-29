package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Word;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class WordServiceTest extends AbstractTest {

	//	Coverage: 94.2%
	//	Covered Instructions: 178
	//	Missed  Instructions: 11
	//	Total   Instructions: 189
	
	@Autowired
	private WordService wordService;

	@Test
	public void testCreate() {

		super.authenticate("admin");

		Word word = wordService.create();

		Assert.isNull(word.getEnglishName());
		Assert.isNull(word.getSpanishName());

		super.authenticate(null);
	}

	@Test
	public void testSave() {

		super.authenticate("admin");

		Word word = wordService.create();

		word.setEnglishName("Asshole");
		word.setSpanishName("Gilipollas");

		Word result = wordService.save(word);

		Assert.isTrue(wordService.findAll().contains(result));

		super.authenticate(null);
	}

	@Test
	public void testDelete() {

		super.authenticate("admin");

		Word word = (Word) wordService.findAll().toArray()[0];

		wordService.delete(word);

		Assert.isTrue(!wordService.findAll().contains(word));

		super.authenticate(null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testWrongContent() {

		super.authenticate("admin");

		Word word = wordService.create();

		word.setEnglishName("");
		word.setSpanishName("Gilipollas");

		wordService.save(word);

		super.authenticate(null);

	}

	@Test
	public void driver() {
		Object testingData[][] = { { "admin", null },
				{ "bookmaker1", IllegalArgumentException.class },
				{ null, IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			template((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	protected void template(String username, Class<?> expected) {
		Class<?> caught = null;
		try {
			authenticate(username);
			Word word = (Word) wordService.findAll().toArray()[0];
			word.setEnglishName("owo");
			wordService.save(word);
			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
