package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import security.LoginService;
import domain.Counselor;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculaService {

	// Managed Repository -----

	@Autowired
	private CurriculaRepository curriculaRepository;

	// Supporting Services -----

	@Autowired
	private CounselorService counselorService;

	@Autowired
	private PersonalRecordService personalRecordService;

	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;

	@Autowired
	private EducationRecordService educationRecordService;

	@Autowired
	private EndorserRecordService endorserRecordService;

	@Autowired
	private ProfessionalRecordService professionalRecordService;

	// Constructor
	public CurriculaService() {
		super();
	}

	// Simple CRUD methods -----

	@SuppressWarnings("unchecked")
	public Curricula create() {
		Curricula result;
		Counselor counselor = this.counselorService.findByPrincipal();
		PersonalRecord personalRecord;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		Collection<EducationRecord> educationRecords;
		Collection<EndorserRecord> endorserRecords;
		Collection<ProfessionalRecord> professionalRecords;

		result = new Curricula();
		personalRecord = this.personalRecordService.create();
		miscellaneousRecords = Collections.EMPTY_SET;
		educationRecords = Collections.EMPTY_SET;
		endorserRecords = Collections.EMPTY_SET;
		professionalRecords = Collections.EMPTY_SET;

		result.setTicker(this.generateTicker());
		result.setPersonalRecord(personalRecord);
		result.setMiscellaneousRecords(miscellaneousRecords);
		result.setEducationRecords(educationRecords);
		result.setEndorserRecords(endorserRecords);
		result.setProfessionalRecords(professionalRecords);
		result.setCounselor(counselor);

		return result;
	}

	public Collection<Curricula> findAll() {
		return curriculaRepository.findAll();
	}

	public Curricula findOne(int Id) {
		return curriculaRepository.findOne(Id);
	}

	public Curricula save(Curricula a) {
		Assert.isTrue(LoginService.hasRole("COUNSELOR"));
		Assert.notNull(a);
		Curricula saved = curriculaRepository.saveAndFlush(a);

		return saved;
	}

	public void delete(Curricula a) {
		this.curriculaRepository.delete(a);
	}

	public void flush() {
		this.curriculaRepository.flush();
	}

	// Other business methods -----

	public Curricula findByPersonalRecord(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);
		Curricula result;

		result = this.curriculaRepository.findByPersonalRecordId(personalRecord
				.getId());

		return result;
	}

	public Curricula findByMiscellaneousRecord(
			final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Curricula result;

		result = this.curriculaRepository
				.findByMiscellaneousRecord(miscellaneousRecord.getId());

		return result;
	}

	public Curricula findByEducationRecord(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Curricula result;

		result = this.curriculaRepository.findByEducationRecord(educationRecord
				.getId());

		return result;
	}

	public Curricula findByEndorserRecord(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Curricula result;

		result = this.curriculaRepository.findByEndorserRecord(endorserRecord
				.getId());

		return result;
	}

	public Curricula findByProfessionalRecord(
			final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Curricula result;

		result = this.curriculaRepository
				.findByProfessionalRecord(professionalRecord.getId());

		return result;
	}

	public Curricula findByCounselor(final Counselor counselor) {
		Assert.notNull(counselor);
		Curricula result;

		result = this.curriculaRepository.findByCounselorId(counselor.getId());

		return result;
	}

	public Curricula emptyCurricula(Curricula c) {
		Collection<EducationRecord> ed = c.getEducationRecords();
		for (int i = 0; i < ed.size(); i++) {
			EducationRecord cn = (EducationRecord) ed.toArray()[i];
			System.out.println("deleted: " + cn);
			educationRecordService.delete(cn);
			educationRecordService.flush();
		}

		Collection<MiscellaneousRecord> mi = c.getMiscellaneousRecords();
		for (int i = 0; i < mi.size(); i++) {
			MiscellaneousRecord cn = (MiscellaneousRecord) mi.toArray()[i];
			System.out.println("deleted: " + cn);
			miscellaneousRecordService.delete(cn);
			miscellaneousRecordService.flush();
		}

		Collection<EndorserRecord> en = c.getEndorserRecords();
		for (int i = 0; i < en.size(); i++) {
			EndorserRecord cn = (EndorserRecord) en.toArray()[i];
			System.out.println("deleted: " + cn);
			endorserRecordService.delete(cn);
			endorserRecordService.flush();
		}

		Collection<ProfessionalRecord> pr = c.getProfessionalRecords();
		for (int i = 0; i < pr.size(); i++) {
			ProfessionalRecord cn = (ProfessionalRecord) pr.toArray()[i];
			System.out.println("deleted: " + cn);
			professionalRecordService.delete(cn);
			professionalRecordService.flush();
			
		}

			PersonalRecord pe = c.getPersonalRecord();
			c.setPersonalRecord(null);
			Curricula res = this.save(c);
			personalRecordService.delete(pe);
			System.out.println("deleted: " + pe);

		return res;
	}
	
	private String generateTicker(){
        Date date = new Date(); // your date
        Calendar n = Calendar.getInstance();
        n.setTime(date);
        String t = "";
        String s = Integer.toString((n.get(Calendar.DAY_OF_MONTH)));
        String m = Integer.toString(n.get(Calendar.MONTH)+1);
        if(s.length()==1) s= "0"+Integer.toString((n.get(Calendar.DAY_OF_MONTH)));
        if(m.length()==1) m = "0"+ Integer.toString(n.get(Calendar.MONTH) +1);
        t = t + Integer.toString(n.get(Calendar.YEAR) - 2000) + m + s + "-"+ randomWordAndNumber();

        return t;
    }

    private String randomWordAndNumber(){
         String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < 5) { // length of the random string.
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            String saltStr = salt.toString();
            return saltStr;
    }

}