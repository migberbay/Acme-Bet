package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.BetPool;
import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer>{
	
	@Query("select s from Sponsorship s where s.sponsor.id = ?1")
	Collection<Sponsorship> findBySponsor(int id);
	
	@Query("select s from Sponsorship s where s.betPool = ?1")
	Collection<Sponsorship> findByBetPool(BetPool betpool);
	

	@Query("select avg(s.sponsorships.size) from Sponsor s")
	Double getAvgSponsorshipsPerSponsor();
	
	@Query("select min(s.sponsorships.size) from Sponsor s")
	Integer getMinSponsorshipsPerSponsor();
	
	@Query("select max(s.sponsorships.size) from Sponsor s")
	Integer getMaxSponsorshipsPerSponsor();

	@Query("select avg(s.sponsorships.size) from Sponsor s")
	Double getStdevSponsorshipsPerSponsor();


}

