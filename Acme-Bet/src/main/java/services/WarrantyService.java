package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.WarrantyRepository;
import security.LoginService;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	//Managed Repository ----------------------------------------------------------------------
	
	@Autowired
	private WarrantyRepository warrantyRepository;
	
	//Supporting Services ---------------------------------------------------------------------
	
	@Autowired
	private Validator validator;
	
	//Simple CRUD methods ---------------------------------------------------------------------
	
	public Warranty create(){
		Warranty res = new Warranty();
		
		res.setIsFinal(false);
		res.setLaws(new ArrayList<String>());
		
		return res;
	}
	
	public Collection<Warranty> findAll(){
		return warrantyRepository.findAll();
	}
	
	public Warranty findOne(int Id){
		return warrantyRepository.findOne(Id);
	}
	
	public Warranty save(Warranty a){
		
		Assert.isTrue(LoginService.hasRole("ADMIN"));
		
		return warrantyRepository.saveAndFlush(a);
	}
	
	public void delete(Warranty a){
		
		Assert.isTrue(LoginService.hasRole("ADMIN"));
		
		warrantyRepository.delete(a);
	}
	
	//Other business methods --------------------------------------------------------------
	
	public Warranty reconstruct(Warranty warranty, int id, BindingResult binding) {
		Warranty result;
		
		if(id==0) {
			result = warranty;
			result.setIsFinal(false);
		} else {
			result = this.warrantyRepository.findOne(id);
			result.setIsFinal(warranty.getIsFinal());
			result.setLaws(warranty.getLaws());
			result.setTerms(warranty.getTerms());
			result.setTitle(warranty.getTitle());
		}
		
		validator.validate(result, binding);
		if(binding.hasErrors()) {
			throw new ValidationException();
		}
		
		return result;
		
	}

}