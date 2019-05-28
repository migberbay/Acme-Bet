package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer>{
	
	@Query("select c from Curricula c where c.counselor.id = ?1")
	Curricula findByCounselorId(int id);
	
	@Query("select c from Curricula c where c.personalRecord.id = ?1")
	Curricula findByPersonalRecordId(int id);
	
	@Query("select c from Curricula c join c.educationRecords e where e.id = ?1")
	Curricula findByEducationRecord(int id);
	
	@Query("select c from Curricula c join c.miscellaneousRecords m where m.id = ?1")
	Curricula findByMiscellaneousRecord(int id);
	
	@Query("select c from Curricula c join c.professionalRecords p where p.id = ?1")
	Curricula findByProfessionalRecord(int id);
	
	@Query("select c from Curricula c join c.endorserRecords en where en.id = ?1")
	Curricula findByEndorserRecord(int id);


}

