package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FinderRepository;
import domain.Actor;
import domain.Finder;


@Service
@Transactional
public class FinderService {

	//Managed Repository -----
	
	@Autowired
	private FinderRepository finderRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Finder create(){
		Finder res = new Finder();
		
		return res;
	}
	
	public Collection<Finder> findAll(){
		return finderRepository.findAll();
	}
	
	public Finder findOne(int Id){
		return finderRepository.findOne(Id);
	}
	
	public Finder save(Finder a){
		
		Finder saved = finderRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Finder a){
		finderRepository.delete(a);
	}
	
	//Other business methods -----

}