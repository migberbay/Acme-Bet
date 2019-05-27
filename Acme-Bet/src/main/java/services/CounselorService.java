package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CounselorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Counselor;
import domain.SocialProfile;


@Service
@Transactional
public class CounselorService {

	//Managed Repository -----
	
	@Autowired
	private CounselorRepository counselorRepository;
	
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Counselor create(UserAccount ua){
		Counselor res = new Counselor();

		res.setIsBanned(false);
		res.setIsSuspicious(false);
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<Counselor> findAll(){
		return counselorRepository.findAll();
	}
	
	public Counselor findOne(int Id){
		return counselorRepository.findOne(Id);
	}
	
	public Counselor save(Counselor a){
		
		Counselor saved = counselorRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Counselor a){
		counselorRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Counselor findByPrincipal(){
		return this.counselorRepository.findByUserAccount(LoginService.getPrincipal());
	}

	public 	Collection<Counselor> getSolvedCounselorsByUser(Integer userId){
		return this.counselorRepository.getSolvedCounselorsByUser(userId);
	}
	
	public Counselor getHighestAvgScoreCounselor(){
		Collection<Object> quer;
		Counselor res = new Counselor();
		quer = this.counselorRepository.getHighestAvgScoreCounselor();
		for(Object o: quer){
			Object[] n = (Object[]) o;
			res=(Counselor)n[0];
			break;
		}
		return res;
	}
}