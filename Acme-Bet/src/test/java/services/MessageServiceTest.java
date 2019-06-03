package services;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Message;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class MessageServiceTest extends AbstractTest {

	//	Coverage: 96.6%
	//	Covered Instructions: 229
	//	Missed  Instructions: 8
	//	Total   Instructions: 237
	
	@Autowired
	private MessageService messageService;

	@Autowired
	private ActorService actorService;

	//	F.R. 9.4: Manage his or her messages.
	
	//	Comprobar que se puedes crear y guardar mensajes.
	@Test
	public void testCreateAndSave() {
		authenticate("bookmaker1");
		Actor actor = actorService.findOne(getEntityId("bookmaker1"));

		Message m = messageService.create(actor);
		m.setBody("body");
		m.setMoment(new Date(System.currentTimeMillis() - 1000));
		m.setRecipient(actor);
		m.setSender(actor);
		m.setSubject("hello");
		m.setTags(new ArrayList<String>());
		Message saved = messageService.save(m);
		Assert.isTrue(saved.getId() != 0);

		unauthenticate();

	}

	//	Comprobar que se pueden eliminar mensajes.
	@Test
	public void testDelete() {
		authenticate("bookmaker1");
		Actor actor = actorService.findOne(getEntityId("bookmaker1"));

		Message m = messageService.create(actor);
		m.setBody("body");
		m.setMoment(new Date(System.currentTimeMillis() - 1000));
		m.setRecipient(actor);
		m.setSender(actor);
		m.setSubject("hello");
		m.setTags(new ArrayList<String>());
		Message saved = messageService.save(m);
		Assert.isTrue(saved.getId() != 0);

		messageService.delete(saved);
		Message me = messageService.findOne(saved.getId());
		Assert.isTrue(me.getTags().contains("DELETED"));

		messageService.delete(me);
		Assert.isNull(messageService.findOne(saved.getId()));
		unauthenticate();

	}

	@Test
	public void driverMessage() {
		Object testingData[][] = { { "bookmaker1", null }, { null, null } };
		for (int i = 0; i < testingData.length; i++) {
			templateMessage((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
		}
	}

	protected void templateMessage(String o, Class<?> expected) {
		Class<?> caught = null;
		try {
			authenticate(o);

			Actor actor = actorService.findOne(getEntityId("bookmaker1"));

			Message m = messageService.create(actor);
			m.setBody("body");
			m.setMoment(new Date(System.currentTimeMillis() - 1000));
			m.setRecipient(actor);
			m.setSender(actor);
			m.setSubject("hello");
			m.setTags(new ArrayList<String>());

			messageService.save(m);

		} catch (Throwable oops) {
			oops.printStackTrace();
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
