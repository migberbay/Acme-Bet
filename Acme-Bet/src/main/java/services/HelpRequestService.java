package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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
	
	@Autowired
	private Validator validator;
	
	//Simple CRUD methods -----
	
	public HelpRequest create(){
		HelpRequest res = new HelpRequest();
		res.setAttachments(new ArrayList<String>());
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
	
	public HelpRequest reconstruct(HelpRequest request, BindingResult binding){
		HelpRequest result;
		if(request.getId()==0){
			result = request;
			result.setStatus("OPEN");
			result.setMoment(new Date());
			result.setUser(userService.findByPrincipal());
		}else{
			result = helpRequestRepository.findOne(request.getId());
			result.setDescription(request.getDescription());
			result.setAttachments(request.getAttachments());
			result.setBetPool(request.getBetPool());
			result.setCategory(request.getCategory());
		}
		
		validator.validate(result, binding);
		if(binding.hasErrors()){
			throw new ValidationException();
		}
		return result;
	}


}