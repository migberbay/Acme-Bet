package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Bookmaker;

@Repository
public interface BookmakerRepository extends JpaRepository<Bookmaker, Integer>{
	
	@Query("select a from Bookmaker a where a.userAccount = ?1") 
	Bookmaker findByUserAccount(UserAccount ua);
	
	@Query("select distinct b from Bookmaker b join b.betPools bp where bp.isFinal=true and b.betPools.size = (select max(b1.betPools.size) from Bookmaker b1 join b1.betPools bp1 where bp1.isFinal=true)")
	Collection<Bookmaker> getBookmakersWMoreBetPools();
	
}
