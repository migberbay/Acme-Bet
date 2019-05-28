package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.BookmakerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Bookmaker;
import domain.SocialProfile;


@Service
@Transactional
public class BookmakerService {

	//Managed Repository -----
	
	@Autowired
	private BookmakerRepository bookmakerRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Bookmaker create(UserAccount ua){
		Bookmaker res = new Bookmaker();

		res.setIsBanned(false);
		res.setIsSuspicious(false);
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<Bookmaker> findAll(){
		return bookmakerRepository.findAll();
	}
	
	public Bookmaker findOne(int Id){
		return bookmakerRepository.findOne(Id);
	}
	
	public Bookmaker save(Bookmaker a){
		
		Bookmaker saved = bookmakerRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Bookmaker a){
		bookmakerRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Bookmaker findByPrincipal(){
		return this.bookmakerRepository.findByUserAccount(LoginService.getPrincipal());
	}

	public Collection<Bookmaker> getBookmakersWMoreBetPools(){
		return this.bookmakerRepository.getBookmakersWMoreBetPools();
	}
	
}