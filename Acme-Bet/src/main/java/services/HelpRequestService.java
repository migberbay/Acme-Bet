package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.HelpRequestRepository;
import domain.Category;
import domain.Counselor;
import domain.HelpRequest;
import domain.Message;


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
	private CounselorService counselorService;
	
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
	
	public Collection<HelpRequest> getOpenRequests(Counselor counselor){
		return helpRequestRepository.getOpenRequests(counselor);
	}
	
	public Collection<HelpRequest> findRequestsByCounselor() {
		return helpRequestRepository.findRequestsByCounselor(counselorService.findByPrincipal().getId());
	}
	
	public HelpRequest reconstruct(HelpRequest request, BindingResult binding){
		HelpRequest result;
		if(request.getId()==0){
			result = request;
			result.setStatus("OPEN");
			result.setMoment(new Date(System.currentTimeMillis()-1000));
			result.setUser(userService.findByPrincipal());
			if(request.getBetPool()!=null)result.setTicker(this.generateTicker(request.getBetPool().getTicker()));
		}else{
			System.out.println("owo");
			result = helpRequestRepository.findOne(request.getId());
			System.out.println("user " + result.getUser());
			result.setDescription(request.getDescription());
			result.setAttachments(request.getAttachments());
			result.setBetPool(request.getBetPool());
			result.setCategory(request.getCategory());
		}
		
		validator.validate(result, binding);
		if(binding.hasErrors()){
			System.out.println("uwu");
			System.out.println(binding.getFieldErrors());
			throw new ValidationException();
		}
		return result;
	}
	
	private String generateTicker(String poolTicker){
		String t = "";

		t = poolTicker.substring(7)+"-"+ randomNumbers();

		return t;
	}
	
	private String randomNumbers(){
		 String SALTCHARS = "1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 3) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;
	}

	public Message reconstructMessage(Message message, BindingResult bindingResult) {
		Message res;
		
		res = message;
		res.setFlagSpam(false);
		res.setMoment(new Date(System.currentTimeMillis()-1000));
		res.setSender(counselorService.findByPrincipal());
		
		validator.validate(res, bindingResult);
		if(bindingResult.hasErrors()){
			throw new ValidationException();
		}
		
		return res;
	}

	public Double getAvgHelpRequestsPerUser(){
		Double res = this.helpRequestRepository.getAvgHelpRequestsPerUser();
		if(res==null)res=0d;
		return res;
	}

	public Integer getMinHelpRequestsPerUser(){
		Integer res = this.helpRequestRepository.getMinHelpRequestsPerUser();
		if(res==null)res=0;
		return res;
	}
	
	public Integer getMaxHelpRequestsPerUser(){
		Integer res = this.helpRequestRepository.getMaxHelpRequestsPerUser();
		if(res==null)res=0;
		return res;
	}
	
	public Double getStdevHelpRequestsPerUser(){
		Double res = this.helpRequestRepository.getStdevHelpRequestsPerUser();
		if(res==null)res=0d;
		return res;
	}


}