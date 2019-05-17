package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.BetPool;
import domain.Counselor;
import domain.Sponsorship;

@Repository
public interface BetPoolRepository extends JpaRepository<BetPool, Integer>{
	
	@Query("select b from BetPool b where b.isFinal = true") 
	Collection<BetPool> findFinal();

}

