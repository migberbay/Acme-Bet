package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Counselor;

@Repository
public interface CounselorRepository extends JpaRepository<Counselor, Integer>{
	
	@Query("select a from Counselor a where a.userAccount = ?1") 
	Counselor findByUserAccount(UserAccount ua);
	

	
}
