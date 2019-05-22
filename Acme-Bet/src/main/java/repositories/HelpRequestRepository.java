package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.HelpRequest;

@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, Integer>{
	


}

