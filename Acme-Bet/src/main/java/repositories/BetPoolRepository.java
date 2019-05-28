package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.BetPool;
import domain.Bookmaker;
import domain.Category;

@Repository
public interface BetPoolRepository extends JpaRepository<BetPool, Integer>{
	
	@Query("select b from BetPool b where b.isFinal = true") 
	Collection<BetPool> findFinal();

	@Query("select b from BetPool b where b.category = ?1")
	Collection<BetPool> getPoolsByCategory(Category category);
	
	@Query("select b from BetPool b where b.groupCode = ?1")
	Collection<BetPool> getPoolsByCode(String code);
	
	@Query("select b from BetPool b where b.bookmaker = ?1")
	Collection<BetPool> getPoolsByPrincipal(Bookmaker bookmaker);
	
	@Query("select b from BetPool b where b.startDate < ?1 AND b.endDate > ?1 AND b.isFinal = true")
	Collection<BetPool> getPoolsInProgress(Date now);
	
	@Query("select b from BetPool b where b.startDate > ?1 AND b.isFinal = true")
	Collection<BetPool> getPoolsNotStarted(Date now);
	
	@Query("select b from BetPool b where b.endDate < ?1 AND b.isFinal = true")
	Collection<BetPool> getPoolsEnded(Date now);
}

