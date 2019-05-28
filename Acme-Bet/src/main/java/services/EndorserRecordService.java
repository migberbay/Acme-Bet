package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import security.LoginService;
import domain.Curricula;
import domain.EndorserRecord;


@Service
@Transactional
public class EndorserRecordService {

	//Managed Repository -----
	
	@Autowired
	private EndorserRecordRepository endorserRecordRepository;
	
	//Supporting Services -----
	@Autowired
	private CurriculaService curriculaService;
	
	//Constructor
	public EndorserRecordService() {
		super();
	}
	
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
	
	public EndorserRecord save(EndorserRecord a, Curricula curricula){
		Assert.isTrue(LoginService.hasRole("COUNSELOR"));
		Assert.notNull(a);
		EndorserRecord result;
		
		curricula = this.curriculaService.findOne(curricula.getId());
		result = this.endorserRecordRepository.save(a);
		curricula.getEndorserRecords().add(a);
		this.curriculaService.save(curricula);
		
		return result;
	}
	
	public EndorserRecord trueSave(final EndorserRecord endorserRecord) {
		Assert.isTrue(LoginService.hasRole("COUNSELOR"));
		return this.endorserRecordRepository.save(endorserRecord);
	}
	
	public void delete(EndorserRecord a){
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		Assert.isTrue(this.endorserRecordRepository.exists(a.getId()));
		
		endorserRecordRepository.delete(a);
	}
	
	public void pureDelete(EndorserRecord a) {
		this.endorserRecordRepository.delete(a);
		this.endorserRecordRepository.flush();

	}
	
	public void flush() {
		this.endorserRecordRepository.flush();
	}
	
	//Other business methods -----

}