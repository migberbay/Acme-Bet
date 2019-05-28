package controllers.actor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import controllers.AbstractController;
import domain.Actor;
import forms.RegisterForm;

@Controller
@RequestMapping("actor/")
public class ActorCreateController extends AbstractController {

	// Services ----------------------------------------------------------------

	// @Autowired
	// private UserAccountService userAccountService;

	@Autowired
	private ActorService actorService;

	// Constructors ------------------------------------------------------------

	public ActorCreateController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerActor(String type) {
		RegisterForm form = new RegisterForm();
		System.out.println(type);
		form.setType(type);
		return this.createRegisterModelAndView(form);
	}

	
	// SAVE-------------------------------------------------------
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("registerForm") RegisterForm registerForm, final BindingResult binding) {
		Boolean expired = false;
		Boolean passMatch = false;
		
		Calendar c = Calendar.getInstance();		
		System.out.println("el año es: " + c.get(Calendar.YEAR)+ " y el mes es: "+ c.get(Calendar.MONTH));
		if(registerForm.getExpirationYear()== c.get(Calendar.YEAR) && registerForm.getExpirationMonth()<=c.get(Calendar.MONTH)){
			expired = true;
		}
		
		if(registerForm.getPassword().equals(registerForm.getPassword2())){passMatch=true;}
		
		Actor actor = this.actorService.reconstruct(registerForm, binding);//de aqui ya sale con la authority, creditcard y datos basicos.
			
			if (binding.hasErrors() || expired || !passMatch) {
				System.out.println(binding);
				ModelAndView res = this.createRegisterModelAndView(registerForm);
				res.addObject("isExpired", expired);
				res.addObject("passMatch", passMatch);
				return res;
			} else {
			try {
				switch (registerForm.getType()) {
				case "USER":
					actorService.registerUser(actor);
					
					break;
				case "SPONSOR":
					actorService.registerSponsor(actor);
					
					break;
				case "BOOKMAKER":
					actorService.registerBookmaker(actor);
					
					break;
				case "COUNSELOR":
					actorService.registerCounselor(actor);
					
					break;
				case "ADMIN":
					actorService.registerAdmin(actor);
					
					break;
				}
				return new ModelAndView("redirect:/");
			
		} catch (final Throwable oops) {
			oops.printStackTrace();
			return this.createRegisterModelAndView(registerForm, "commit.error");
			}
			
		}
	}

	protected ModelAndView createRegisterModelAndView(RegisterForm form) {
		ModelAndView result;
		result = this.createRegisterModelAndView(form, null);
		return result;
	}
	

	protected ModelAndView createRegisterModelAndView(RegisterForm form, String messageCode) {
		ModelAndView res;
		Date d = new Date();
		Collection <Integer> months = new ArrayList<>();
		Collection <Integer> years = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			months.add(i+1);
			years.add(d.getYear()+i+1900);
		}
		Authority adminAuth = new Authority();
		adminAuth.setAuthority(Authority.ADMIN);

		Boolean isAdmin = false;
		try {
			isAdmin = LoginService.getPrincipal().getAuthorities().contains(adminAuth);
		} catch (Exception e) {
			System.out.println("the user is not logged?");
		}
		
		if (form.getType().equals("ADMIN") && !isAdmin) {
			return new ModelAndView("error/access");
		}else{
		res = new ModelAndView("actor/register");
		
		
		res.addObject("registerForm",form);
		res.addObject("message", messageCode);
		res.addObject("months", months);
		res.addObject("years", years);
		return res;
	}
}
	


}
