package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Finder;
import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class FinderServiceTest extends AbstractTest {

	//	Coverage: 92.2%
	//	Covered Instructions: 119 
	//	Missed  Instructions: 10
	//	Total   Instructions: 129
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FinderService	finderService;
	
	@Test
	public void testCreate() {
		
		Finder finder = finderService.create();
		
		Assert.isTrue(finder.getBetPools().isEmpty());
		Assert.isNull(finder.getKeyword());
		Assert.isNull(finder.getMinRange());
		Assert.isNull(finder.getMaxRange());
		Assert.isNull(finder.getOpeningDate());
		Assert.isNull(finder.getEndDate());
		Assert.isNull(finder.getUser());
		Assert.isNull(finder.getMoment());
		
	}

	@Test
	public void testSave() {	
		
		authenticate("user1");
		
		Finder finder = finderService.findByPrincipal();
		finder.setKeyword("santa");
		
		Finder saved = this.finderService.save(finder);
		Assert.isTrue(finderService.findAll().contains(saved));
		
		unauthenticate();
	}

	@Test
	public void testDelete() {
		
		authenticate("user3");
		
		Finder finder = (Finder) this.finderService.findAll().toArray()[0];
		
		finderService.delete(finder);
		Assert.isTrue(!finderService.findAll().contains(finder));
		
		unauthenticate();
	}
	
	// Functional testing -----------------------------------------------------------------------
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveOtherUser(){
		
		authenticate("user1");
		
		User user = userService.findOne(getEntityId("user2"));
		Finder finder = finderService.findByUser(user);
		
		finder.setKeyword("Australia");
		
		finderService.save(finder);
		
		unauthenticate();

	}
	
	@Test
	public void testSelfAssigned(){
		
		Finder finder;
		
		authenticate("user1");
		
		finder = finderService.findByPrincipal();
		finder.setKeyword("santa");
		
		finderService.save(finder);
		
		unauthenticate();
		
	}
}
