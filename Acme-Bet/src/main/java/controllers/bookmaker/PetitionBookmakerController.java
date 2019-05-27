package controllers.bookmaker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BetService;
import services.MessageService;
import services.PetitionService;
import services.UserService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Bet;
import domain.Message;
import domain.Petition;
import domain.User;

@Controller
@RequestMapping("petition/bookmaker")
public class PetitionBookmakerController extends AbstractController {

	@Autowired
	private PetitionService petitionService;
	
	@Autowired
	private BetService betService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;

	//List -----------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result = new ModelAndView("petition/list");
			result.addObject("petitions", petitionService.getPetitionsByPrincipal());
			result.addObject("petition", petitionService.create());
			return result;
		}
	
	
	//Accept -----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.POST , params = "accept")
	public ModelAndView accept(Petition petition) {
		
		Petition res = petitionService.findOne(petition.getId());
		Bet bet = res.getBet();
		bet.setIsAccepted(true);
		Bet saved = betService.save(bet);
		
		res.setStatus("ACCEPTED");
		petitionService.save(res);
		
		User user = res.getUser();
		user.setFunds(user.getFunds()-bet.getAmount());
		user.getBets().add(saved);
		userService.save(user);
		
		return list();
	}
	
	//Reject -----------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.POST , params = "reject")
		public ModelAndView reject(Petition petition) {
			ModelAndView result = list();
			
			Petition res = petitionService.findOne(petition.getId());
			
			if (petition.getRejectReason().equals("")){
				result.addObject("rejectReasonEmpty", true);
				
			}else{
				res.setStatus("REJECTED");
				petitionService.save(res);
				
				Bet bet = res.getBet();

				Message message = messageService.create(res.getBet().getBetPool().getBookmaker());
				message.setBody("your betting petition for the betting pool " +res.getBet().getBetPool().getTitle() +
						" has been rejected for the following reason: "+ petition.getRejectReason() +", sorry about that.");
				message.setMoment(new Date(System.currentTimeMillis()-1000));
				message.setRecipient(res.getUser());
				message.setSubject("Rejected bet "+ bet.getTicker());
				List<String> aux = new ArrayList<String>();
				aux.add("REJECTED_BET");
				message.setTags(aux);
				messageService.save(message);
			}
			
			return result;

		}
	
	//Ancillary -----------
	
}
