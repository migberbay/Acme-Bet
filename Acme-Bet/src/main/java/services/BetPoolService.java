package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BetPoolRepository;
import security.LoginService;
import domain.Actor;
import domain.Bet;
import domain.BetPool;
import domain.Category;
import domain.Finder;
import domain.HelpRequest;
import forms.BetPoolForm;

@Service
@Transactional
public class BetPoolService {

	@Autowired
	private BetPoolRepository betPoolRepository;
	
	@Autowired
	private BookmakerService bookmakerService;
	
	@Autowired
	private Validator validator;

	public BetPool create() {
		BetPool res = new BetPool();
		res.setBets(new ArrayList<Bet>());
		res.setFinders(new ArrayList<Finder>());
		res.setHelpRequests(new ArrayList<HelpRequest>());
		res.setTicker(generateTicker());
		res.setIsFinal(false);
		return res;
	}

	public BetPool save(BetPool betPool) {		
		return this.betPoolRepository.saveAndFlush(betPool);
	}

	public void delete(BetPool betPool) {
		this.betPoolRepository.delete(betPool);
	}

	public Collection<BetPool> findAll() {
		return betPoolRepository.findAll();
	}

	public BetPool findOne(int Id) {
		return betPoolRepository.findOne(Id);
	}
	
	// Other business operations go here.
	
	public Collection<BetPool> findFinal(){
		return this.betPoolRepository.findFinal();
	}
	
	public Collection<BetPool> getPoolsByCategory(Category category){
		return this.betPoolRepository.getPoolsByCategory(category);
	}
	
	public Collection<BetPool> getPoolsNotStarted(){
		return this.betPoolRepository.getPoolsNotStarted(new Date());
	}
	
	public Collection<BetPool> getPoolsInProgress(){
		return this.betPoolRepository.getPoolsInProgress(new Date());
	}
	
	public Collection<BetPool> getPoolsEnded(){
		return this.betPoolRepository.getPoolsEnded(new Date());
	}
	
	public Collection<BetPool>getPoolsByCode(String code){
		return this.betPoolRepository.getPoolsByCode(code);
	}

	public BetPool reconstruct(BetPoolForm form, BindingResult binding) {
		BetPool res;
		
		if (form.getId()!=0) {//editamos
			res = this.findOne(form.getId());
			
		}else{//creamos
			res = this.create();
			res.setBookmaker(bookmakerService.findByPrincipal());
			res.setMoment(new Date(System.currentTimeMillis()-1000));
			res.setMinRange(10.0);
			res.setMaxRange(49.99);
			res.setEndDate(form.getEndDate());
			res.setResultDate(form.getResultDate());
			res.setStartDate(form.getStartDate());
		}
		
		res.setCategory(form.getCategory());
		res.setDescription(form.getDescription());
		res.setIsFinal(form.getIsFinal());	
		
		if (res.getId() != 0 &&(form.getEndDate()!= null && form.getResultDate() != null && form.getStartDate() != null)) {
			res.setEndDate(form.getEndDate());
			res.setResultDate(form.getResultDate());
			res.setStartDate(form.getStartDate());
		}
		
		res.setTitle(form.getTitle());
		res.setWarranty(form.getWarranty());
		
		String[] participants = form.getParticipants().split(",");
		Collection<String> part = new ArrayList<String>();
		for (int i = 0; i < participants.length; i++) {
			part.add(participants[i].trim());
		}
		res.setParticipants(part);
		
		System.out.println("\n" + res);
		
		validator.validate(form, binding);
		
		System.out.println("validation worked?");
		
		return res;
	}

	public BetPool copy(BetPool pool) {
		BetPool res = this.create();
		
		res.setBets(pool.getBets());
		res.setBookmaker(pool.getBookmaker());
		res.setCategory(pool.getCategory());
		res.setDescription(pool.getDescription());
		res.setEndDate(pool.getEndDate());
		res.setIsFinal(pool.getIsFinal());
		res.setMaxRange(pool.getMaxRange());
		res.setMinRange(pool.getMinRange());
		res.setMoment(pool.getMoment());
		res.setParticipants(pool.getParticipants());
		res.setResultDate(pool.getResultDate());
		res.setStartDate(pool.getStartDate());
		res.setTicker(pool.getTicker());
		res.setTitle(pool.getTitle());
		res.setWarranty(pool.getWarranty());
		res.setWinner(pool.getWinner());
		
		return res;
	}

	public BetPoolForm build(BetPool pool) {
		
		BetPoolForm res = new BetPoolForm();
		res.setCategory(pool.getCategory());
		res.setDescription(pool.getDescription());
		res.setEndDate(pool.getEndDate());
		res.setId(pool.getId());
		res.setIsFinal(pool.getIsFinal());
		String participants = "";
		for (int i = 0; i < pool.getParticipants().size(); i++) {
			participants = participants + pool.getParticipants().toArray()[i];
			if (i<pool.getParticipants().size()-1) {
				participants = participants + " , ";
			}
		}
		
		res.setParticipants(participants);
		
		res.setResultDate(pool.getResultDate());
		res.setStartDate(pool.getStartDate());
		res.setTitle(pool.getTitle());
		res.setWarranty(pool.getWarranty());
		
		
		return res;
	}
	
	public Collection<BetPool> getPoolsByPrincipal(){
		return this.betPoolRepository.getPoolsByPrincipal(bookmakerService.findByPrincipal());
	}
	
	public String generateTicker(){
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
