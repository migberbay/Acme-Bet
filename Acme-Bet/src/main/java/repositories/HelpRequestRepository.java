package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.HelpRequest;

@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, Integer>{
	
	@Query("select h from HelpRequest h where h.category = ?1")
	Collection<HelpRequest> getRequestsByCategory(Category category);

	@Query("select h from HelpRequest h where h.user.id = ?1")
	Collection<HelpRequest> findRequestsByUser(int userId);

}

