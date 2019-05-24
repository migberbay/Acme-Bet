package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bookmaker;
import domain.Petition;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Integer>{
	
	@Query("select p from Petition p where p.bet.betPool.bookmaker = ?1") 
	Collection<Petition> findByPrincipal(Bookmaker bookmaker);

}

