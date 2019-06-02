package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.BetPool;
import domain.Finder;

import java.util.Collection;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer>{
/*
	@Query("select b from BetPool b where " +
			"((:keyword is null or :keyword like '' ) or " +
				"(b.title like %:keyword% " +
				"or b.description like %:keyword%)) " +
			"and (:maxRange is null or b.maxRange <= :maxRange) " +
			"and (:minRange is null or b.minRange >= :minRange) " +
			"and (:categoryId is null or b.category.id =:categoryId)")
	Collection<BetPool> filterBetPools(@Param("keyword") String keyword,
			 							@Param("maxRange") Double maxRange, @Param("minRange") Double minRange,
										   @Param("categoryId") Integer categoryId);*/
	
	@Query("select b from BetPool b where " +
			"((?1 is null or ?1 like '' ) or " +
				"(b.title like %?1% " +
				"or b.description like %?1%)) " +
			"and (?2 is 0.0 or b.maxRange <= ?2) " +
			"and (?3 is 0.0 or b.minRange >= ?3) " +
			"and (?4 is 0 or b.category.id =?4)")
	Collection<BetPool> filterBetPools(String keyword,Double maxRange,Double minRange,Integer categoryId);
	
	@Query("select f from Finder f where f.user.id = :userId")
	Finder findByUser(@Param("userId") Integer userId);

	@Query("select avg(f.betPools.size) from Finder f")
	Double getAvgResultsPerFinder();

	@Query("select min(f.betPools.size) from Finder f")
	Integer getMinResultsPerFinder();
	
	@Query("select max(f.betPools.size) from Finder f")
	Integer getMaxResultsPerFinder();
	
	@Query("select stddev(f.betPools.size) from Finder f")
	Double getStdevResultsPerFinder();
	
}

