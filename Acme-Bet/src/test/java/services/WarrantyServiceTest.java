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

import domain.Warranty;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class WarrantyServiceTest extends AbstractTest {

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
	public void testUpdate(){
		
		super.authenticate("admin");
		
		Warranty warranty = (Warranty) warrantyService.findAll().toArray()[1];
		
		warranty.setTitle("Updated title warranty");
		warranty.setTerms("Updated terms warranty");
		
		Warranty result = warrantyService.save(warranty);

		Assert.isTrue(warrantyService.findAll().contains(result));
		super.unauthenticate();
		
	}

	@Test
	public void testDelete() {

		super.authenticate("admin");

		Warranty warranty = (Warranty) warrantyService.findAll().toArray()[1];

		warrantyService.delete(warranty);

		Assert.isTrue(!warrantyService.findAll().contains(warranty));

		super.unauthenticate();
	}
}
