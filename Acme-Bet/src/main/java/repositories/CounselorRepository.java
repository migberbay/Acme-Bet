package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Counselor;

@Repository
public interface CounselorRepository extends JpaRepository<Counselor, Integer>{
	
	@Query("select a from Counselor a where a.userAccount = ?1") 
	Counselor findByUserAccount(UserAccount ua);
	
	@Query("select distinct h.counselor from HelpRequest h where h.status='SOLVED' and h.user.id=?1")
	Collection<Counselor> getSolvedCounselorsByUser(Integer userId);
	

}
