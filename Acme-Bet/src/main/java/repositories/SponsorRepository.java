package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer>{
	
	@Query("select a from Sponsor a where a.userAccount = ?1") 
	Sponsor findByUserAccount(UserAccount ua);
	
	@Query("select distinct s.sponsor from Sponsorship s where s.isActivated=true order by s")
	Collection<Sponsor> topInActivatedSponsorships();	
}
