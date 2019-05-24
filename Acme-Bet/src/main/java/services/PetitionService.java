package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PetitionRepository;
import domain.Actor;
import domain.Petition;


@Service
@Transactional
public class PetitionService {

	//Managed Repository -----
	
	@Autowired
	private PetitionRepository petitionRepository;
	
	//Supporting Services -----
	
	@Autowired
	private BookmakerService bookmakerService;
	
	//Simple CRUD methods -----
	
	public Petition create(){
		Petition res = new Petition();
		
		return res;
	}
	
	public Collection<Petition> findAll(){
		return petitionRepository.findAll();
	}
	
	public Petition findOne(int Id){
		return petitionRepository.findOne(Id);
	}
	
	public Petition save(Petition a){
		
		Petition saved = petitionRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Petition a){
		petitionRepository.delete(a);
	}

	public Collection<Petition> getPetitionsByPrincipal() {
		
		return this.petitionRepository.findByPrincipal(bookmakerService.findByPrincipal());
	}
	
	//Other business methods -----

}