package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Bookmaker;

@Repository
public interface BookmakerRepository extends JpaRepository<Bookmaker, Integer>{
	
	@Query("select a from Bookmaker a where a.userAccount = ?1") 
	Bookmaker findByUserAccount(UserAccount ua);
	

	
}
