package controllers.actor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.AdminService;
import services.BookmakerService;
import services.CounselorService;
import services.CreditCardService;
import services.MessageService;
import services.SponsorService;
import services.UserService;
import controllers.AbstractController;
import domain.Actor;
import domain.Admin;
import domain.Bookmaker;
import domain.Counselor;
import domain.CreditCard;
import domain.SocialProfile;
import domain.Sponsor;
import domain.User;
import forms.ProfileForm;

@Controller
@RequestMapping("actor/")
public class ProfileActorController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookmakerService bookmakerService;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private CounselorService counselorService;
	
	@Autowired
	private CreditCardService cardService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private Validator validator;
	

	// Show --------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false) Integer actorId) {

		ModelAndView result;

		// Diferentes autoridades:
		Authority adminAuth = new Authority();
		adminAuth.setAuthority("ADMIN");

		Authority userAuth = new Authority();
		userAuth.setAuthority("USER");

		Authority bookmakerAuth = new Authority();
		bookmakerAuth.setAuthority("BOOKMAKER");
		
		Authority counselorAuth = new Authority();
		counselorAuth.setAuthority("COUNSELOR");
		
		Authority sponsorauth = new Authority();
		sponsorauth.setAuthority("SPONSOR");

		
		Actor principal;
		try {
			principal = actorService.getByUserAccount(LoginService.getPrincipal());
		} catch (Exception e) {
			principal = null;
		}

		result = new ModelAndView("actor/show");

		if (actorId != null) { //accedemos al perfil de otro actor
			Actor actor = actorService.findOne(actorId);
			Collection<SocialProfile> socialProfiles = actor.getSocialProfiles();
				
			result.addObject("actor", actor); // actor que se va a mostrar
			result.addObject("logged", false); // flag para permitir editar
			
			if (principal == null) {
				
			}else if (principal.getUserAccount().getAuthorities().contains(adminAuth)) {
				result.addObject("principalIsAdmin", true);
			}

			result.addObject("socialProfiles", socialProfiles);

		} else {//accedemos a nuestro perfil
			Actor actor = actorService.getByUserAccount(LoginService.getPrincipal());
			Collection<SocialProfile> socialProfiles = actor.getSocialProfiles();
			result.addObject("logged", true);
			
			if (actor.getUserAccount().getAuthorities().contains(adminAuth)) {
				Admin admin = adminService.findOne(actor.getId());
				result.addObject("actor", admin);
				result.addObject("principalIsAdmin", true);
			}

			if (actor.getUserAccount().getAuthorities().contains(userAuth)) {
				User user = userService.findOne(actor.getId());
				result.addObject("isUser", true);
				result.addObject("actor", user);
			}

			if (actor.getUserAccount().getAuthorities().contains(bookmakerAuth)) {
				Bookmaker bookmaker = bookmakerService.findOne(actor.getId());
				result.addObject("actor", bookmaker);
			}
			
			if (actor.getUserAccount().getAuthorities().contains(counselorAuth)) {
				Counselor counselor = counselorService.findOne(actor.getId());
				result.addObject("isCounselor", true);
				result.addObject("actor", counselor);
			}
			
			if (actor.getUserAccount().getAuthorities().contains(sponsorauth)) {
				Sponsor sponsor = sponsorService.findOne(actor.getId());
				result.addObject("isSponsor", true);
				result.addObject("actor", sponsor);
			}
			
			result.addObject("socialProfiles", socialProfiles);
		}
		
		result.addObject("requestURI", "actor/show.do");

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/editPersonal", method = RequestMethod.GET)
	public ModelAndView editPersonal() {

		ModelAndView result = new ModelAndView();
		ProfileForm f = new ProfileForm();
		Actor a = actorService.getByUserAccount(LoginService.getPrincipal());
		ProfileForm form = actorService.rellenaForm(a, f, new ArrayList<String>());
		
		
		result = createEditModelAndView(form,"personal");

		return result;
	}
	
	@RequestMapping(value = "/editCreditCard", method = RequestMethod.GET)
	public ModelAndView editCredit() {

		ModelAndView result = new ModelAndView();
		ProfileForm f = new ProfileForm();
		Actor a = actorService.getByUserAccount(LoginService.getPrincipal());
		ProfileForm form = actorService.rellenaForm(a, f, new ArrayList<String>());
		result = createEditModelAndView(form,"credit");

		return result;
	}
	
	@RequestMapping(value = "/editUserAccount", method = RequestMethod.GET)
	public ModelAndView editAccount() {

		ModelAndView result = new ModelAndView();
		ProfileForm f = new ProfileForm();
		Actor a = actorService.getByUserAccount(LoginService.getPrincipal());
		ProfileForm form = actorService.rellenaForm(a, f, new ArrayList<String>());
		result = createEditModelAndView(form,"account");

		return result;
	}

	// Save -----------------------------------------------------------------
	// Personal Data
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "savePersonal")
	public ModelAndView savePersonal(ProfileForm profileForm, final BindingResult binding) {
		ModelAndView res;
		
		Actor a = actorService.getByUserAccount(LoginService.getPrincipal());
		
		List<String> aux = new ArrayList<>();
		aux.add("account");
		aux.add("credit");
		ProfileForm form = actorService.rellenaForm(a, profileForm, aux );
		
		validator.validate(form, binding);
		
		if (binding.hasErrors()) {
			System.out.println(binding);
			res = this.createEditModelAndView(form, "personal");
			return res;
		} else {
			try {
				
			a.setAddress(form.getAddress());
			a.setEmail(form.getEmail());
			a.setName(form.getName());
			a.setPhone(form.getPhone());
			a.setPhoto(form.getPhoto());
			a.setSurnames(form.getSurnames());
			
			actorService.save(a);
		
		res = new ModelAndView("redirect:show.do");
		return res;
		
	} catch (final Throwable oops) {
		oops.printStackTrace();
		res = this.editPersonal();
		return res;
		}
	}
}
	
	// UserAccount
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveAccount")
	public ModelAndView saveAccount(ProfileForm profileForm, final BindingResult binding) {
		ModelAndView res;
		Boolean passMatch = false;		
		Actor a = actorService.getByUserAccount(LoginService.getPrincipal());
		
		List<String> aux = new ArrayList<>();
		aux.add("credit");
		aux.add("personal");
		ProfileForm form = actorService.rellenaForm(a, profileForm, aux );
		
		if(form.getPassword().equals(form.getPassword2())){passMatch=true;}
		
		validator.validate(form, binding);
		
		if (binding.hasErrors()|| !passMatch) {
			System.out.println(binding);
			res = this.createEditModelAndView(form, "account");
			res.addObject("passMatch", passMatch);
			return res;
		} else {
			try {
		UserAccount account = actorService.getByUserAccount(LoginService.getPrincipal()).getUserAccount();
		account.setPassword(form.getPassword());
		account.setUsername(form.getUsername());
		userAccountService.save(account);
		res = new ModelAndView("redirect:/j_spring_security_logout");
		return res;
		
	} catch (final Throwable oops) {
		oops.printStackTrace();
		res = this.editAccount();
		return res;
		}
	}
}
	// UserAccount
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveCredit")
	public ModelAndView saveCreditCard(ProfileForm profileForm, final BindingResult binding) {
		ModelAndView res;
		
		Boolean expired = false;
		Calendar cal = Calendar.getInstance();
		Actor a = actorService.getByUserAccount(LoginService.getPrincipal());
		
		List<String> aux = new ArrayList<>();
		aux.add("account");
		aux.add("personal");
		ProfileForm form = actorService.rellenaForm(a, profileForm, aux );
		
		if(form.getExpirationYear()== cal.get(Calendar.YEAR) && form.getExpirationMonth()<=cal.get(Calendar.MONTH)){
			expired = true;
		}
		
		validator.validate(form, binding);
		
		if (binding.hasErrors()|| expired) {
			System.out.println(binding);
			res = this.createEditModelAndView(form, "credit");
			res.addObject("isExpired", expired);

			return res;
		} else {
			try {
			CreditCard credit = actorService.getByUserAccount(LoginService.getPrincipal()).getCreditCard();
			Calendar calendar = Calendar.getInstance();
			calendar.set(form.getExpirationYear(), form.getExpirationMonth(), 1);
			Date date = calendar.getTime();
			
			credit.setCVV(form.getCVV());
			credit.setExpirationDate(date);
			credit.setHolder(form.getHolder());
			credit.setMake(form.getMake());
			credit.setNumber(form.getNumber());
			
			cardService.save(credit);
			
		res = new ModelAndView("redirect:show.do");
		return res;
		
	} catch (final Throwable oops) {
		oops.printStackTrace();
		res = this.editCredit();
		return res;
		}
	}
}
//Add funds-------------------------------------------------------------------------
	@RequestMapping(value = "/editFunds", method = RequestMethod.GET)
	public ModelAndView editFunds() {
		ModelAndView result = new ModelAndView("actor/editFunds");
		
		Authority userAuth = new Authority();
		Authority sponsorAuth = new Authority();
		
		userAuth.setAuthority(Authority.USER);
		sponsorAuth.setAuthority(Authority.SPONSOR);
		
		if(LoginService.getPrincipal().getAuthorities().contains(userAuth)){
			User user = userService.findByPrincipal();
			user.setFunds(0.0);
			result.addObject("actor",user);
			result.addObject("isUser", true);
		}else
		if(LoginService.getPrincipal().getAuthorities().contains(sponsorAuth)){
			Sponsor sponsor = sponsorService.findByPrincipal();
			sponsor.setFunds(0.0);
			result.addObject("actor",sponsor);
			result.addObject("isSponsor", true);
		}else{
			result = new ModelAndView("error/access");
		}

		return result;
	}
	
	
	@RequestMapping(value = "/editFunds", method = RequestMethod.POST, params = "saveUser")
	public ModelAndView saveFunds(User user,BindingResult binding) {
		ModelAndView res;
		
		try {
			if (user.getFunds() > 0 && user.getFunds() <= 1000) {
				res = new ModelAndView("redirect:show.do");
				User u = userService.findByPrincipal();
				u.setFunds(u.getFunds() + user.getFunds());
				userService.save(u);
			} else {
				res = editFunds();
				res.addObject("incorrectFunds", true);
			}

		} catch (Exception e) {
			res = editFunds();
			res.addObject("error", true);
			e.printStackTrace();
		}
		
		return res;
	}
	
	@RequestMapping(value = "/editFunds", method = RequestMethod.POST, params = "saveSponsor")
	public ModelAndView saveFunds(Sponsor sponsor,BindingResult binding) {
		ModelAndView res;
		
		try {
			if (sponsor.getFunds() > 0 && sponsor.getFunds() <= 1000) {
				res = new ModelAndView("redirect:show.do");
				Sponsor s = sponsorService.findByPrincipal();
				s.setFunds(s.getFunds() + sponsor.getFunds());
				sponsorService.save(s);
			} else {
				res = editFunds();
				res.addObject("incorrectFunds", true);
			}

		} catch (Exception e) {
			res = editFunds();
			res.addObject("error", true);
			e.printStackTrace();
		}
		
		return res;
	}
	

//Generate JSON---------------------------------------------------------------------
	@RequestMapping(value = "/generateData", method = RequestMethod.GET)
	public ModelAndView generate() {
		
		ModelAndView res = new ModelAndView("actor/personalData");

		Actor actor = actorService.getByUserAccount(LoginService.getPrincipal());
		
		res.addObject("data", actorService.actorToJson(actor));

		return res;
	}

	
//Ancillary---------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final ProfileForm form, String type) {
		ModelAndView result;
		result = this.createEditModelAndView(form, type ,null);
		return result;
	}

	private ModelAndView createEditModelAndView(final ProfileForm form, String type, final String message) {

		ModelAndView result = new ModelAndView("actor/edit");
		
		Date d = new Date();
		Collection <Integer> months = new ArrayList<>();
		Collection <Integer> years = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			months.add(i+1);
			years.add(d.getYear()+i+1900);
		}
		
		result.addObject("message", message);
		result.addObject("profileForm", form);
		result.addObject("showAccount", type=="account");
		result.addObject("showCredit", type=="credit");
		result.addObject("showPersonal", type=="personal");
		result.addObject("months",months);
		result.addObject("years",years);
		
		return result;
	}
}