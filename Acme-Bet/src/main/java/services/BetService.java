package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BetRepository;
import domain.Bet;
import domain.BetPool;
import domain.User;
import forms.BettingForm;

@Service
@Transactional
public class BetService {

	@Autowired
	private BetRepository betRepository;
	
	@Autowired
	private BetPoolService betPoolService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Validator validator;

	public Bet create(BetPool betPool, User user) {
		Bet res = new Bet();
		res.setBetPool(betPool);
		res.setMoment(new Date(System.currentTimeMillis()-1000));
		res.setUser(user);
		res.setTicker(generateTicker());
		return res;
	}

	public Bet save(Bet bet) {		
		return this.betRepository.saveAndFlush(bet);
	}

	public void delete(Bet bet) {
		this.betRepository.delete(bet);
	}

	public Collection<Bet> findAll() {
		return betRepository.findAll();
	}

	public Bet findOne(int Id) {
		return betRepository.findOne(Id);
	}
	
	// Other business operations go here.
	
	public Bet reconstruct(BettingForm form, BindingResult bind) {
		
		Bet res = this.create(betPoolService.findOne(form.getBetPoolId()), userService.findByPrincipal());
		res.setAmount(form.getAmount());
		res.setWinner(form.getWinner());
	
		validator.validate(form, bind);

		return res;
	}
	
	private String generateTicker(){
		Date date = new Date(); // your date
		Calendar n = Calendar.getInstance();
		n.setTime(date);
		String t = "";
		String s = Integer.toString((n.get(Calendar.DAY_OF_MONTH)));
		String m = Integer.toString(n.get(Calendar.MONTH)+1);
		if(s.length()==1) s= "0"+Integer.toString((n.get(Calendar.DAY_OF_MONTH)));
		if(m.length()==1) m = "0"+ Integer.toString(n.get(Calendar.MONTH) +1);
		t = t + Integer.toString(n.get(Calendar.YEAR) - 2000)
				+ m
				+ s
				+ "-"+ randomWordAndNumber();

		return t;
	}
	
	private String randomWordAndNumber(){
		 String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 6) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;
	}



}
