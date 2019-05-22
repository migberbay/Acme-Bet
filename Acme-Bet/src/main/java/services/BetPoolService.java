package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BetPoolRepository;
import security.LoginService;
import domain.Actor;
import domain.Bet;
import domain.BetPool;
import domain.Category;
import domain.Finder;
import domain.HelpRequest;

@Service
@Transactional
public class BetPoolService {

	@Autowired
	private BetPoolRepository betPoolRepository;

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
}
