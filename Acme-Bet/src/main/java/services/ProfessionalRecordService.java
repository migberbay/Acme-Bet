package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import security.LoginService;
import domain.Curricula;
import domain.ProfessionalRecord;


@Service
@Transactional
public class ProfessionalRecordService {

	//Managed Repository -----
	
	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService;
	
	//Constructor
	public ProfessionalRecordService() {
		super();
	}
	
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
	
	public ProfessionalRecord save(ProfessionalRecord a, Curricula curricula){
		Assert.isTrue(LoginService.hasRole("COUNSELOR"));
		Assert.notNull(a);
		ProfessionalRecord result;
		
		curricula = this.curriculaService.findOne(curricula.getId());
		result = this.professionalRecordRepository.save(a);
		curricula.getProfessionalRecords().add(a);
		this.curriculaService.save(curricula);
		
		return result;
	}
	
	public ProfessionalRecord trueSave(final ProfessionalRecord professionalRecord) {
		Assert.isTrue(LoginService.hasRole("COUNSELOR"));
		return this.professionalRecordRepository.save(professionalRecord);
	}
	
	public void delete(ProfessionalRecord a){
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		Assert.isTrue(this.professionalRecordRepository.exists(a.getId()));
		
		professionalRecordRepository.delete(a);
	}
	
	public void pureDelete(ProfessionalRecord a) {
		this.professionalRecordRepository.delete(a);
		this.professionalRecordRepository.flush();

	}
	
	public void flush() {
		this.professionalRecordRepository.flush();
	}
	
	//Other business methods ----

}