package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProfessionalRecordRepository;
import domain.Actor;
import domain.ProfessionalRecord;


@Service
@Transactional
public class ProfessionalRecordService {

	//Managed Repository -----
	
	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public ProfessionalRecord create(){
		ProfessionalRecord res = new ProfessionalRecord();
		
		return res;
	}
	
	public Collection<ProfessionalRecord> findAll(){
		return professionalRecordRepository.findAll();
	}
	
	public ProfessionalRecord findOne(int Id){
		return professionalRecordRepository.findOne(Id);
	}
	
	public ProfessionalRecord save(ProfessionalRecord a){
		
		ProfessionalRecord saved = professionalRecordRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(ProfessionalRecord a){
		professionalRecordRepository.delete(a);
	}
	
	//Other business methods -----

}