package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EducationRecordRepository;
import domain.Actor;
import domain.EducationRecord;


@Service
@Transactional
public class EducationRecordService {

	//Managed Repository -----
	
	@Autowired
	private EducationRecordRepository educationRecordRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public EducationRecord create(){
		EducationRecord res = new EducationRecord();
		
		return res;
	}
	
	public Collection<EducationRecord> findAll(){
		return educationRecordRepository.findAll();
	}
	
	public EducationRecord findOne(int Id){
		return educationRecordRepository.findOne(Id);
	}
	
	public EducationRecord save(EducationRecord a){
		
		EducationRecord saved = educationRecordRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(EducationRecord a){
		educationRecordRepository.delete(a);
	}
	
	//Other business methods -----

}