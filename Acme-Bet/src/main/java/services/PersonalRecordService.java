package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import security.LoginService;
import domain.Counselor;
import domain.Curricula;
import domain.PersonalRecord;


@Service
@Transactional
public class PersonalRecordService {

	//Managed Repository -----
	
	@Autowired
	private PersonalRecordRepository personalRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private CounselorService counselorService;
	
	//Constructor
	public PersonalRecordService() {
		super();
	}
	
	//Simple CRUD methods -----
	
	public PersonalRecord create(){
		PersonalRecord res = new PersonalRecord();
		
		return res;
	}
	
	public Collection<PersonalRecord> findAll(){
		return personalRecordRepository.findAll();
	}
	
	public PersonalRecord findOne(int Id){
		return personalRecordRepository.findOne(Id);
	}
	
	public PersonalRecord save(PersonalRecord a){
		Assert.isTrue(LoginService.hasRole("COUNSELOR"));
		Assert.notNull(a);
		
		Counselor counselor = this.counselorService.findByPrincipal();
		Curricula curricula = new Curricula();

		curricula = this.curriculaService.findByCounselor(counselor);

		if(a.getId() == 0) {
		curricula = this.curriculaService.create();
		curricula.setPersonalRecord(null);
		this.curriculaService.flush();
		curricula.setCounselor(counselor);
		}

		this.curriculaService.save(curricula);

		final PersonalRecord result = this.personalRecordRepository.save(a);
		this.flush();

		this.curriculaService.flush();

		return result;
	}
	
	public PersonalRecord saveTrue(final PersonalRecord personalRecord) {
		return this.personalRecordRepository.save(personalRecord);
	}
	
	public void delete(PersonalRecord a){
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		Assert.isTrue(this.personalRecordRepository.exists(a.getId()));

		this.personalRecordRepository.delete(a);
	}
	
	public void flush() {
		this.personalRecordRepository.flush();
	}
	
	//Other business methods -----

}