package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CurriculaRepository;
import domain.Actor;
import domain.Curricula;


@Service
@Transactional
public class CurriculaService {

	//Managed Repository -----
	
	@Autowired
	private CurriculaRepository curriculaRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Curricula create(){
		Curricula res = new Curricula();
		
		return res;
	}
	
	public Collection<Curricula> findAll(){
		return curriculaRepository.findAll();
	}
	
	public Curricula findOne(int Id){
		return curriculaRepository.findOne(Id);
	}
	
	public Curricula save(Curricula a){
		
		Curricula saved = curriculaRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Curricula a){
		curriculaRepository.delete(a);
	}
	
	//Other business methods -----

}