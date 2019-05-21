package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	private BookmakerService bookmakerService;
	
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
		
		Assert.isTrue(LoginService.hasRole("BOOKMAKER"));
		
		return warrantyRepository.saveAndFlush(a);
	}
	
	public void delete(Warranty a){
		
		Assert.isTrue(LoginService.hasRole("BOOKMAKER"));
		
		warrantyRepository.delete(a);
	}
	
	//Other business methods --------------------------------------------------------------
	
	public Collection<Warranty> findByPrincipal(){

		return warrantyRepository.findWarrantyByBookmaker(bookmakerService.findByPrincipal().getId());
	}

}