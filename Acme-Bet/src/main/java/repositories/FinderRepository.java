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
			"and (:openingDate is null or b.startDate >= :openingDate) " +
			"and (:endDate is null or b.endDate <= :endDate) " +
			"and (:categoryId=0 or b.category.id = :categoryId)")
	Collection<BetPool> filterBetPools(@Param("keyword") String keyword,
			 							@Param("maxRange") Double maxRange, @Param("minRange") Double minRange,
										   @Param("openingDate") Date openingDate, @Param("endDate") Date endDate,
										   @Param("categoryId") Integer categoryId);
	
	@Query("select f from Finder f where f.user.id = :userId")
	Finder findByUser(@Param("userId") Integer userId);
/*
	@Query("select b from BetPool b where " +
			"((:keyword is null or :keyword like '' ) or " +
				"(b.title like %:keyword% " +
				"or b.description like %:keyword% ))" +
//				"or %:keyword% member of b.participants " +
	//			"or %:keyword% member of b.winners)) " +
			"and (:maxRange is null or b.maxRange < :maxRange) " +
			"and (:minRange is null or b.minRange > :minRange) " +
			"and (:openingDate is null or b.startDate >= :openingDate) " +
			"and (:endDate is null or b.endDate <= :endDate) " +
			"and (:categoryId=0 or b.category.id = :categoryId) ")
	Collection<BetPool> filterBetPools(@Param("keyword") String keyword,
			 								@Param("maxRange") Double maxRange, @Param("minRange") Double minRange,
										   @Param("openingDate") Date openingDate, @Param("endDate") Date endDate,
										   @Param("categoryId") Integer categoryId);*/
}

