package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorserRecordRepository;
import domain.Actor;
import domain.EndorserRecord;


@Service
@Transactional
public class EndorserRecordService {

	//Managed Repository -----
	
	@Autowired
	private EndorserRecordRepository endorserRecordRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public EndorserRecord create(){
		EndorserRecord res = new EndorserRecord();
		
		return res;
	}
	
	public Collection<EndorserRecord> findAll(){
		return endorserRecordRepository.findAll();
	}
	
	public EndorserRecord findOne(int Id){
		return endorserRecordRepository.findOne(Id);
	}
	
	public EndorserRecord save(EndorserRecord a){
		
		EndorserRecord saved = endorserRecordRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(EndorserRecord a){
		endorserRecordRepository.delete(a);
	}
	
	//Other business methods -----

}