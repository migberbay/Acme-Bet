package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Admin;
import domain.Bookmaker;
import domain.Counselor;
import domain.CreditCard;
import domain.Sponsor;
import domain.User;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
public class ActorServiceTest extends AbstractTest {

	//	Coverage: 91.7%
	//	Covered Instructions: 903 
	//	Missed  Instructions: 82
	//	Total   Instructions: 985
	
	@Autowired
	private BookmakerService bookmakerService;
	
	@Autowired
	private CounselorService counselorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ActorService actorService;
	
	
	//	F.R. 12.1: Create user accounts for new administrators.
	@Test
	public void testRegisterAdmin(){
		
		authenticate("admin");
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("admin10");
		userAccount.setPassword("admin10");		
		Admin admin = adminService.create(userAccount);
		
		CreditCard credit = creditCardService.create();
		credit.setCVV(123);
		credit.setHolder("admin");
		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
		credit.setMake("AMEX");
		credit.setNumber("2534746553427456");
		
		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		admin.setName("adminName");
		admin.setSurnames("adminSurname1 adminSurname2");
		admin.setEmail("admin@email.com");
		admin.setAddress("adminAddress");
		admin.setPhoto("http://www.photo.com");
		admin.setPhone("612123456");
		admin.setCreditCard(credit);
		admin.setMessagesLastSeen(moment);
		
		Admin result = actorService.registerAdmin(admin);
		
		Assert.isTrue(adminService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	F.R. 12.1: Create user accounts for new bookmakers.
	@Test
	public void testRegisterBookmaker(){
		
		authenticate("admin");
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.BOOKMAKER);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("bookmaker10");
		userAccount.setPassword("bookmaker10");		
		Bookmaker bookmaker = bookmakerService.create(userAccount);
		
		CreditCard credit = creditCardService.create();
		credit.setCVV(123);
		credit.setHolder("bookmaker");
		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
		credit.setMake("AMEX");
		credit.setNumber("2534746553427456");
		
		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		bookmaker.setName("bookmakerName");
		bookmaker.setSurnames("bookmakerSurname1 bookmakerSurname2");
		bookmaker.setEmail("bookmaker@email.com");
		bookmaker.setAddress("bookmakerAddress");
		bookmaker.setPhoto("http://www.photo.com");
		bookmaker.setPhone("612123456");
		bookmaker.setCreditCard(credit);
		bookmaker.setMessagesLastSeen(moment);
		
		Bookmaker result = actorService.registerBookmaker(bookmaker);
		
		Assert.isTrue(bookmakerService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testIncorrectRegisterBookmaker(){
		
		authenticate("admin");
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.BOOKMAKER);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("bookmaker10");
		userAccount.setPassword("bookmaker10");		
		Bookmaker bookmaker = bookmakerService.create(userAccount);
		
//		CreditCard credit = creditCardService.create();
//		credit.setCVV(123);
//		credit.setHolder("bookmaker");
//		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
//		credit.setMake("AMEX");
//		credit.setNumber("2534746553427456");
		
		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		bookmaker.setName("bookmakerName");
		bookmaker.setSurnames("bookmakerSurname1 bookmakerSurname2");
		bookmaker.setEmail("bookmaker@email.com");
		bookmaker.setAddress("bookmakerAddress");
		bookmaker.setPhoto("http://www.photo.com");
		bookmaker.setPhone("612123456");
//		bookmaker.setCreditCard(credit);
		bookmaker.setMessagesLastSeen(moment);
		
		Bookmaker result = actorService.registerBookmaker(bookmaker);
		
		Assert.isTrue(bookmakerService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	F.R. 8.1: Register to the system as a user.
	@Test
	public void testRegisterUser(){
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.USER);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("user10");
		userAccount.setPassword("user10");		
		User user = userService.create(userAccount);
		
		CreditCard credit = creditCardService.create();
		credit.setCVV(123);
		credit.setHolder("user");
		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
		credit.setMake("AMEX");
		credit.setNumber("2534746553427456");
//		creditCardService.save(credit);
		
		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		user.setName("userName");
		user.setSurnames("userSurname1 userSurname2");
		user.setEmail("user@email.com");
		user.setAddress("userAddress");
		user.setPhoto("http://www.photo.com");
		user.setPhone("612123456");
		user.setCreditCard(credit);
		user.setFunds(0.0);
		user.setLuckScore(0.0);
//		userService.save(user);
		user.setMessagesLastSeen(moment);
		
		User result = actorService.registerUser(user);
		
		Assert.isTrue(userService.findAll().contains(result));
		
	}
	
	//	F.R. 30.1: Register to the system as counselor.
	@Test
	public void testRegisterCounselor(){
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.COUNSELOR);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("counselor10");
		userAccount.setPassword("counselor10");		
		Counselor counselor = counselorService.create(userAccount);
		
		CreditCard credit = creditCardService.create();
		credit.setCVV(123);
		credit.setHolder("counselor");
		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
		credit.setMake("AMEX");
		credit.setNumber("2534746553427456");
		
		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		counselor.setName("counselorName");
		counselor.setSurnames("counselorSurname1 counselorSurname2");
		counselor.setEmail("counselor@email.com");
		counselor.setAddress("counselorAddress");
		counselor.setPhoto("http://www.photo.com");
		counselor.setPhone("612123456");
		counselor.setCreditCard(credit);
		counselor.setMessagesLastSeen(moment);
		
		Counselor result = actorService.registerCounselor(counselor);
		
		Assert.isTrue(counselorService.findAll().contains(result));
		
	}
	
	//	F.R. 41.2: Register to the system as a sponsor.
	@Test
	public void testRegisterSponsor(){
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("sponsor10");
		userAccount.setPassword("sponsor10");		
		Sponsor sponsor = sponsorService.create(userAccount);
		
		CreditCard credit = creditCardService.create();
		credit.setCVV(123);
		credit.setHolder("sponsor");
		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
		credit.setMake("AMEX");
		credit.setNumber("2534746553427456");
		
		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		sponsor.setName("sponsorName");
		sponsor.setSurnames("sponsorSurname1 sponsorSurname2");
		sponsor.setEmail("sponsor@email.com");
		sponsor.setAddress("sponsorAddress");
		sponsor.setPhoto("http://www.photo.com");
		sponsor.setPhone("612123456");
		sponsor.setCreditCard(credit);
		sponsor.setMessagesLastSeen(moment);
		
		Sponsor result = actorService.registerSponsor(sponsor);
		
		Assert.isTrue(sponsorService.findAll().contains(result));
		
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectRegisterSponsor(){
		
		UserAccount userAccount = userAccountService.create();
		Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		userAccount.getAuthorities().add(authority);
		userAccount.setUsername("sponsor10");
		userAccount.setPassword("sponsor10");		
		Sponsor sponsor = sponsorService.create(userAccount);
		
		CreditCard credit = creditCardService.create();
		credit.setCVV(123);
		credit.setHolder("sponsor");
		credit.setExpirationDate(new Date(System.currentTimeMillis()+623415234));
		credit.setMake("AMEX");
		credit.setNumber("2534746553427456");
		
		Date moment = new Date(System.currentTimeMillis() - 1000);
		
		sponsor.setName("sponsorName");
		sponsor.setSurnames("sponsorSurname1 sponsorSurname2");
		sponsor.setEmail("sponsoremail.com");
		sponsor.setAddress("sponsorAddress");
		sponsor.setPhoto("photo.com");
		sponsor.setPhone("612123456");
		sponsor.setCreditCard(credit);
		sponsor.setMessagesLastSeen(moment);
		
		Sponsor result = actorService.registerSponsor(sponsor);
		
		Assert.isTrue(sponsorService.findAll().contains(result));
		
	}
	
	//	F.R. 9.2: Edit his or her personal data.
	@Test
	public void testEditAdmin(){
		
		authenticate("admin");
		
		Admin admin = adminService.findByPrincipal();
		
		admin.setName("adminUpdatedName");
		admin.setSurnames("adminUpdatedSurname1 adminUpdatedSurname2");
		admin.setEmail("adminUpdated@email.com");
		admin.setAddress("adminUpdatedAddress");
		admin.setPhoto("http://www.photoUpdated.com");
		
		Admin result = adminService.save(admin);
		
		Assert.isTrue(adminService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectEditAdmin(){
		
		authenticate("admin");
		
		Admin admin = adminService.findByPrincipal();
		
		admin.setName("adminUpdatedName");
		admin.setSurnames("adminUpdatedSurname");
		admin.setEmail("adminUpdated.email.com");
		admin.setAddress("adminUpdatedAddress");
		admin.setPhoto("photoUpdated.com");
		
		Admin result = adminService.save(admin);
		
		Assert.isTrue(adminService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	F.R. 9.2: Edit his or her personal data.
	@Test
	public void testEditUser(){
		
		authenticate("user1");
		
		User user = userService.findByPrincipal();
		
		user.setName("userUpdatedName");
		user.setSurnames("userUpdatedSurname1 userUpdatedSurname2");
		user.setEmail("userUpdated@email.com");
		user.setAddress("userUpdatedAddress");
		user.setPhoto("http://www.photoUpdated.com");
		
		User result = userService.save(user);
		
		Assert.isTrue(userService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	F.R. 10.2: Add funds to his account.
	@Test
	public void testAddFundsUser(){
		
		authenticate("user1");
		
		User user = userService.findByPrincipal();
		
		user.setFunds(225.50);		
		
		User result = userService.save(user);
		
		Assert.isTrue(userService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	F.R. 9.2: Edit his or her personal data.
	@Test
	public void testEditCounselor(){
		
		authenticate("counselor1");
		
		Counselor counselor = counselorService.findByPrincipal();
		
		counselor.setName("counselorUpdatedName");
		counselor.setSurnames("counselorUpdatedSurname1 counselorUpdatedSurname2");
		counselor.setEmail("counselorUpdated@email.com");
		counselor.setAddress("counselorUpdatedAddress");
		counselor.setPhoto("http://www.photoUpdated.com");
		
		Counselor result = counselorService.save(counselor);
		
		Assert.isTrue(counselorService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectEditCounselor(){
		
		authenticate("counselor1");
		
		Counselor counselor = counselorService.findByPrincipal();
		
		counselor.setName("");
		counselor.setSurnames("");
		
		Counselor result = counselorService.save(counselor);
		
		Assert.isTrue(counselorService.findAll().contains(result));
		
		unauthenticate();
	}
	
	//	F.R. 9.2: Edit his or her personal data.
	@Test
	public void testEditBookmaker(){
		
		authenticate("bookmaker1");
		
		Bookmaker bookmaker = bookmakerService.findByPrincipal();
		
		bookmaker.setName("bookmakerUpdatedName");
		bookmaker.setSurnames("bookmakerUpdatedSurname1 bookmakerUpdatedSurname2");
		bookmaker.setEmail("bookmakerUpdated@email.com");
		bookmaker.setAddress("bookmakerUpdatedAddress");
		bookmaker.setPhoto("http://www.photoUpdated.com");
		
		Bookmaker result = bookmakerService.save(bookmaker);
		
		Assert.isTrue(bookmakerService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectEditBookmaker(){
		
		authenticate("bookmaker1");
		
		Bookmaker bookmaker = bookmakerService.findByPrincipal();
		
		bookmaker.setName("");
		bookmaker.setSurnames("");
		
		Bookmaker result = bookmakerService.save(bookmaker);
		
		Assert.isTrue(bookmakerService.findAll().contains(result));
		
		unauthenticate();
	}
	
	
	//	F.R. 9.2: Edit his or her personal data.
	@Test
	public void testEditSponsor(){
		
		authenticate("sponsor1");
		
		Sponsor sponsor = sponsorService.findByPrincipal();
		
		sponsor.setName("sponsorUpdatedName");
		sponsor.setSurnames("sponsorUpdatedSurname1 sponsorUpdatedSurname2");
		sponsor.setEmail("sponsorUpdated@email.com");
		sponsor.setAddress("sponsorUpdatedAddress");
		sponsor.setPhoto("http://www.photoUpdated.com");
		
		Sponsor result = sponsorService.save(sponsor);
		
		Assert.isTrue(sponsorService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void testIncorrectEditSponsor(){
		
		authenticate("sponsor1");
		
		Sponsor sponsor = sponsorService.findByPrincipal();
		
		sponsor.setName("");
		sponsor.setSurnames("");
		
		Sponsor result = sponsorService.save(sponsor);
		
		Assert.isTrue(sponsorService.findAll().contains(result));
		
		unauthenticate();
	}
}
