package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import security.LoginService;
import domain.Curricula;
import domain.MiscellaneousRecord;


@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed Repository -----
	
	@Autowired
	private MiscellaneousRecordRepository miscellaneousRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService;
	
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
	
	public MiscellaneousRecord save(MiscellaneousRecord a, Curricula curricula){
		Assert.isTrue(LoginService.hasRole("COUNSELOR"));
		Assert.notNull(a);
		MiscellaneousRecord result;
		
		result = this.miscellaneousRecordRepository.save(a);
		
		if(a.getId() == 0) {
			this.miscellaneousRecordRepository.flush();
			curricula.getMiscellaneousRecords().add(a);
			this.curriculaService.save(curricula);
			
		}
		return result;
	}
	
	public MiscellaneousRecord trueSave(final MiscellaneousRecord miscellaneousRecord) {
		Assert.isTrue(LoginService.hasRole("COUNSELOR"));
		return this.miscellaneousRecordRepository.save(miscellaneousRecord);
	}
	
	public void delete(MiscellaneousRecord a){
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		Assert.isTrue(this.miscellaneousRecordRepository.exists(a.getId()));
		
		miscellaneousRecordRepository.delete(a);
	}
	
	public void pureDelete(MiscellaneousRecord a) {
		this.miscellaneousRecordRepository.delete(a);
		this.miscellaneousRecordRepository.flush();
	}
	
	public void flush() {
		this.miscellaneousRecordRepository.flush();
	}
	
	//Other business methods -----

}