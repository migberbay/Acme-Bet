package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import domain.Bet;
import domain.Counselor;
import domain.HelpRequest;
import domain.Review;
import domain.User;
import domain.SocialProfile;


@Service
@Transactional
public class UserService {

	//Managed Repository -----
	
	@Autowired
	private UserRepository userRepository;
	
	//Supporting Services -----
	
	@Autowired
	private ConfigurationService configurationService;
	//Simple CRUD methods -----
	
	public User create(UserAccount ua){
		User res = new User();

		res.setIsBanned(false);
		res.setIsSuspicious(false);
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setUserAccount(ua);
		
		res.setFunds(configurationService.find().getFreeFunds());
		res.setBets(new ArrayList<Bet>());
		res.setBlockedCounselors(new ArrayList<Counselor>());
		res.setReviews(new ArrayList<Review>());
		res.setHelpRequests(new ArrayList<HelpRequest>());
		res.setLuckScore(0.0);
		
		return res;
	}
	
	public Collection<User> findAll(){
		return userRepository.findAll();
	}
	
	public User findOne(int Id){
		return userRepository.findOne(Id);
	}
	
	public User save(User a){
		
		User saved = userRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(User a){
		userRepository.delete(a);
	}
	
	//Other business methods -----
	
	public User findByPrincipal(){
		return this.userRepository.findByUserAccount(LoginService.getPrincipal());
	}

	public Collection<User> getUsersWMoreBets(){
		return this.userRepository.getUsersWMoreBets();
	}
	
	public Collection<User> getUsersWMoreRequests(){
		return this.userRepository.getUsersWMoreRequests();
	}
	

}