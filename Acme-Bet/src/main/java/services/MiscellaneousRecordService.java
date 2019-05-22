package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MiscellaneousRecordRepository;
import domain.Actor;
import domain.MiscellaneousRecord;


@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed Repository -----
	
	@Autowired
	private MiscellaneousRecordRepository miscellaneousRecordRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public MiscellaneousRecord create(){
		MiscellaneousRecord res = new MiscellaneousRecord();
		
		return res;
	}
	
	public Collection<MiscellaneousRecord> findAll(){
		return miscellaneousRecordRepository.findAll();
	}
	
	public MiscellaneousRecord findOne(int Id){
		return miscellaneousRecordRepository.findOne(Id);
	}
	
	public MiscellaneousRecord save(MiscellaneousRecord a){
		
		MiscellaneousRecord saved = miscellaneousRecordRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(MiscellaneousRecord a){
		miscellaneousRecordRepository.delete(a);
	}
	
	//Other business methods -----

}