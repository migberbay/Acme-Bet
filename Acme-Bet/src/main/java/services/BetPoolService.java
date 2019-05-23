package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
		}
		
		res.setCategory(form.getCategory());
		res.setDescription(form.getDescription());
		res.setEndDate(form.getEndDate());
		res.setIsFinal(form.getIsFinal());					
		
		String[] participants = form.getParticipants().split(",");
		Collection<String> part = new ArrayList<String>();
		for (int i = 0; i < participants.length; i++) {
			part.add(participants[i].trim());
		}
		res.setParticipants(part);
		
		res.setResultDate(form.getResultDate());
		res.setStartDate(form.getStartDate());
		res.setTitle(form.getTitle());
		res.setWarranty(form.getWarranty());
		
		validator.validate(form, binding);
		
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
		res.setWinners(pool.getWinners());
		
		return res;
	}
}
