package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bet;

@Repository
public interface BetRepository extends JpaRepository<Bet, Integer>{
		
	@Query("select avg(b.bets.size) from BetPool b")
	Double getAvgBetsPerBetPool();

	@Query("select min(b.bets.size) from BetPool b")
	Integer getMinBetsPerBetPool();
	
	@Query("select max(b.bets.size) from BetPool b")
	Integer getMaxBetsPerBetPool();
	
	@Query("select stddev(b.bets.size) from BetPool b")
	Double getStdevBetsPerBetPool();
	
	@Query("select avg(u.bets.size) from User u")
	Double getAvgBetsPerUser();

	@Query("select min(u.bets.size) from User u")
	Integer getMinBetsPerUser();
	
	@Query("select max(u.bets.size) from User u")
	Integer getMaxBetsPerUser();
	
	@Query("select stddev(u.bets.size) from User u")
	Double getStdevBetsPerUser();

}

