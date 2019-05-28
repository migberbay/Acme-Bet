package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import security.LoginService;
import domain.Curricula;
import domain.EducationRecord;


@Service
@Transactional
public class EducationRecordService {

	//Managed Repository -----
	
	@Autowired
	private EducationRecordRepository educationRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService;
	
	//Constructor
	public EducationRecordService() {
		super();
	}
	
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
	
	public EducationRecord save(EducationRecord a, Curricula curricula){
		Assert.isTrue(LoginService.hasRole("COUNSELOR"));
		Assert.notNull(a);
		EducationRecord result;
		
		
		result = this.educationRecordRepository.save(a);
		
		if(a.getId() == 0) {
			this.educationRecordRepository.flush();
			curricula.getEducationRecords().add(a);
			this.curriculaService.save(curricula);
		}
		
		return result;
	}
	
	public EducationRecord trueSave(final EducationRecord educationRecord) {
		Assert.isTrue(LoginService.hasRole("COUNSELOR"));
		return this.educationRecordRepository.save(educationRecord);
	}
	
	public void delete(EducationRecord a){
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		Assert.isTrue(this.educationRecordRepository.exists(a.getId()));
		
		educationRecordRepository.delete(a);
	}
	
	public void pureDelete(EducationRecord a) {
		this.educationRecordRepository.delete(a);
		this.educationRecordRepository.flush();

	}
	
	public void flush() {
		this.educationRecordRepository.flush();
	}
	
	//Other business methods -----

}