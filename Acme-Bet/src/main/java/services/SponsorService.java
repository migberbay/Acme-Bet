package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SponsorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Sponsor;
import domain.SocialProfile;


@Service
@Transactional
public class SponsorService {

	//Managed Repository -----
	
	@Autowired
	private SponsorRepository sponsorRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Sponsor create(UserAccount ua){
		Sponsor res = new Sponsor();

		res.setIsBanned(false);
		res.setIsSuspicious(false);
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<Sponsor> findAll(){
		return sponsorRepository.findAll();
	}
	
	public Sponsor findOne(int Id){
		return sponsorRepository.findOne(Id);
	}
	
	public Sponsor save(Sponsor a){
		
		Sponsor saved = sponsorRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Sponsor a){
		sponsorRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Sponsor findByPrincipal(){
		return this.sponsorRepository.findByUserAccount(LoginService.getPrincipal());
	}

}