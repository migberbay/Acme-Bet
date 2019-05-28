package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Actor;
import domain.Admin;
import domain.BetPool;
import domain.Bookmaker;
import domain.Counselor;
import domain.CreditCard;
import domain.SocialProfile;
import domain.Sponsor;
import domain.Sponsorship;
import domain.User;
import forms.ProfileForm;
import forms.RegisterForm;


@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CounselorService counselorService;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private BookmakerService bookmakerService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private Validator validator;

	// Simple CRUD methods -----
	public Actor create (UserAccount ua){
		Actor res = new Actor();
		res.setUserAccount(ua);
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setIsBanned(false);
		res.setIsSuspicious(false);
		res.setMessagesLastSeen(new Date(System.currentTimeMillis()-1000));
		
		return res;
	}
	
	public Collection<Actor> findAll() {
		return actorRepository.findAll();
	}

	public Actor findOne(int Id) {
		return actorRepository.findOne(Id);
	}

	public Actor save(Actor actor) {

		Actor result;

		result = actorRepository.saveAndFlush(actor);
		return result;
	}

	public void delete(Actor actor) {
		
		actorRepository.delete(actor);
	}

	// Other business methods -----

	public Actor getByUserAccount(UserAccount ua) {
		return actorRepository.findByUserAccount(ua);
	}

	public User registerUser(Actor actor) {
		
		User res = userService.create(actor.getUserAccount());
		
		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		CreditCard savedCredit = creditCardService.save(actor.getCreditCard());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		res.setUserAccount(savedua);
		res.setCreditCard(savedCredit);
		
		res.setAddress(actor.getAddress());
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		res.setSurnames(actor.getSurnames());

		User saved = userService.save(res);
		
		return saved;
	}
	
	public Bookmaker registerBookmaker(Actor actor) {

		Bookmaker res = bookmakerService.create(actor.getUserAccount());
		
		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		CreditCard savedCredit = creditCardService.save(actor.getCreditCard());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		res.setUserAccount(savedua);
		res.setCreditCard(savedCredit);
		
		res.setBetPools(new ArrayList<BetPool>());
		
		res.setAddress(actor.getAddress());
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		res.setSurnames(actor.getSurnames());
		
		Bookmaker saved = bookmakerService.save(res);
	
		return saved;
	}
	
	public Sponsor registerSponsor(Actor actor) {

		Sponsor res = sponsorService.create(actor.getUserAccount());
		
		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		CreditCard savedCredit = creditCardService.save(actor.getCreditCard());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		res.setUserAccount(savedua);
		res.setCreditCard(savedCredit);
		
		res.setFunds(0.0);
		res.setSponsorships(new ArrayList<Sponsorship>());
		
		res.setAddress(actor.getAddress());
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		res.setSurnames(actor.getSurnames());
		
		Sponsor saved = sponsorService.save(res);
		
		return saved;
	}
	
	public Admin registerAdmin(Actor actor) {
		
		Admin res = adminService.create(actor.getUserAccount());
		
		Authority adminauth = new Authority();
		adminauth.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(adminauth));

		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		CreditCard savedCredit = creditCardService.save(actor.getCreditCard());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		res.setUserAccount(savedua);
		res.setCreditCard(savedCredit);
		
		res.setAddress(actor.getAddress());
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		res.setSurnames(actor.getSurnames());
		
		Admin saved = adminService.save(res);
		
		return saved;
	}
	

	public Counselor registerCounselor(Actor actor) {
		
		Counselor res = counselorService.create(actor.getUserAccount());
		
		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		CreditCard savedCredit = creditCardService.save(actor.getCreditCard());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		res.setUserAccount(savedua);
		res.setCreditCard(savedCredit);
		
		res.setFunds(0.0);
		res.setFare(0.0);
		
		res.setAddress(actor.getAddress());
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		res.setSurnames(actor.getSurnames());
		
		Counselor saved = counselorService.save(res);
		
		return saved;
	}
	

	public Actor reconstruct(RegisterForm form, BindingResult binding){
		
		//Creamos la tarjeta de credito:
		CreditCard credit = creditCardService.create();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(form.getExpirationYear(), form.getExpirationMonth(), 1);
		Date date = calendar.getTime();
		
		
		credit.setCVV(form.getCVV());
		credit.setExpirationDate(date);
		credit.setHolder(form.getHolder());
		credit.setMake(form.getMake());
		credit.setNumber(form.getNumber());
		

		//Creamos la cuenta de usuario.
		
		UserAccount ua = userAccountService.create();
		
		ua.setPassword(form.getPassword());
		ua.setUsername(form.getUsername());
		
		// Le asignamos la authority cosrrespondiente.
		
		Authority authority = new Authority();
		authority.setAuthority(form.getType());
		ua.addAuthority(authority);
		
		// Creamos el actor con la useraccount sin persistir.
		
		Actor actor = this.create(ua);
		
		actor.setAddress(form.getAddress());
		actor.setCreditCard(credit);
		actor.setEmail(form.getEmail());
		actor.setName(form.getName());
		actor.setPhone(form.getPhone());
		actor.setPhoto(form.getPhoto());
		actor.setSurnames(form.getSurnames());
		
		validator.validate(form, binding);		
		
		if (form.getPassword().equals(form.getPassword2())==false) {
			ObjectError error = new ObjectError(form.getPassword(), "password does not match");
			binding.addError(error);
		}		
		return actor;
	}
	
	public ProfileForm rellenaForm (Actor a , ProfileForm f , List<String> parts){
		
		if (parts.contains("personal") || parts.isEmpty()) {
			// personal data
			f.setAddress(a.getAddress());
			f.setEmail(a.getEmail());
			f.setPhone(a.getPhone());
			f.setPhoto(a.getPhoto());
			f.setSurnames(a.getSurnames());
			f.setName(a.getName());
		}
		
		if(parts.contains("credit") || parts.isEmpty()){
			//credit card
			f.setCVV(a.getCreditCard().getCVV());
			f.setExpirationMonth(a.getCreditCard().getExpirationDate().getMonth());
			f.setExpirationYear(a.getCreditCard().getExpirationDate().getYear()+1900);
			f.setHolder(a.getCreditCard().getHolder());
			f.setMake(a.getCreditCard().getMake());
			f.setNumber(a.getCreditCard().getNumber());
		}
		
		if(parts.contains("account")|| parts.isEmpty()){
			//account
			f.setPassword("default");
			f.setPassword2("default");
			f.setUsername(a.getUserAccount().getUsername());
		}
		
		return f;
	}
	
	
	public String actorToJson(Actor actor){
		
		ProfileForm form = new ProfileForm();
		
		//credit card
		form.setCVV(actor.getCreditCard().getCVV());
		form.setExpirationMonth(actor.getCreditCard().getExpirationDate().getMonth());
		form.setExpirationYear(actor.getCreditCard().getExpirationDate().getYear());
		form.setHolder(actor.getCreditCard().getHolder());
		form.setMake(actor.getCreditCard().getMake());
		form.setNumber(actor.getCreditCard().getNumber());
		
		
		form.setAddress(actor.getAddress());
		form.setEmail(actor.getEmail());
		form.setName(actor.getName());
		form.setPassword("null");
		form.setPassword2("null");
		form.setPhone(actor.getPhone());
		form.setPhoto(actor.getPhoto());
		form.setSurnames(actor.getSurnames());
		form.setUsername(actor.getUserAccount().getUsername());
		
		String res = "";
		ObjectMapper mapper = new ObjectMapper();
		
        try {
            String json = mapper.writeValueAsString(form);
            res = json;
            System.out.println("JSON = " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
		return res;
	}
	
	
	public Actor findByUsername (String username){
		return actorRepository.findByUsername(username);
	}
	
	public Collection<Actor> findBySpammer (Boolean a){
		return actorRepository.findBySuspicous(a);
	}



}