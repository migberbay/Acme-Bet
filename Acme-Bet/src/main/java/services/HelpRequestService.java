package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.HelpRequestRepository;
import security.LoginService;
import domain.Actor;
import domain.Category;
import domain.HelpRequest;


@Service
@Transactional
public class HelpRequestService {

	//Managed Repository -----
	
	@Autowired
	private HelpRequestRepository helpRequestRepository;
	
	//Supporting Services -----
	
	@Autowired
	private UserService userService;
	
	//Simple CRUD methods -----
	
	public HelpRequest create(){
		HelpRequest res = new HelpRequest();
		res.setAttachements(new ArrayList<String>());
		return res;
	}
	
	public Collection<HelpRequest> findAll(){
		return helpRequestRepository.findAll();
	}
	
	public HelpRequest findOne(int Id){
		return helpRequestRepository.findOne(Id);
	}
	
	public HelpRequest save(HelpRequest a){
		
		HelpRequest saved = helpRequestRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(HelpRequest a){
		helpRequestRepository.delete(a);
	}

	//Other business methods -----
	
	public Collection<HelpRequest> getRequestsByCategory(Category category){
		return this.helpRequestRepository.getRequestsByCategory(category);
	}

	public Collection<HelpRequest> findRequestsByPrincipal() {
		return helpRequestRepository.findRequestsByUser(userService.findByPrincipal().getId());
	}


}