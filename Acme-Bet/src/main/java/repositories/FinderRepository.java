package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.BetPool;
import domain.Finder;

import java.util.Collection;
import java.util.Date;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer>{

	@Query("select b from BetPool b where " +
			"((:keyword is null or :keyword like '' ) or " +
				"(b.title like %:keyword% " +
				"or b.description like %:keyword% " +
				"or %:keyword% member of b.participants " +
				"or %:keyword% member of b.winners)) " +
			"and (:maxRange is null or b.maxRange < :maxRange) " +
			"and (:minRange is null or b.minRange > :minRange) " +
			"and (:openingDate is null or b.openingDate >= :openingDate) " +
			"and (:endDate is null or b.endDate <= :endDate) " +
			"and (:category is null or b.category.id == :categoryId) ")
	Collection<BetPool> filterBetPools(@Param("keyword") String keyword,
			 								@Param("maxRange") Double maxRange, @Param("minRange") Double minRange,
										   @Param("openingDate") Date openingDate, @Param("endDate") Date endDate,
										   @Param("categoryId") Integer categoryId);
	
	@Query("select f from Finder f where f.user.id = :userId")
	Finder findByUser(@Param("userId") Integer userId);
	
	@Query("select avg(f.positions.size) from Finder f")
	Double getAvgResultsPerFinder();

	@Query("select min(f.positions.size) from Finder f")
	Integer getMinResultsPerFinder();
	
	@Query("select max(f.positions.size) from Finder f")
	Integer getMaxResultsPerFinder();
	
	@Query("select stddev(f.positions.size) from Finder f")
	Double getStdevResultsPerFinder();
	
	@Query("select (count(f)/(select count(f1) from Finder f1 where f1.keyword!=null or f1.minSalary!=null or f1.maxSalary!=null or f1.maxDeadline!=null or f1.positions.size!=0))*1.0 from Finder f where f.keyword is null and f.minSalary is null and f.maxSalary is null and f.maxDeadline is null and f.positions.size=0")
	Double getRatioEmptyFinders();
}

