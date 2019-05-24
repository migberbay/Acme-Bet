package repositories;

import java.util.Collection;

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
	
	@Query("select b from BetPool b where b.bookmaker = ?1")
	Collection<BetPool> getPoolsByPrincipal(Bookmaker bookmaker);
}

