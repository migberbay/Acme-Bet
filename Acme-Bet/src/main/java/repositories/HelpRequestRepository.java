package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Counselor;
import domain.HelpRequest;

@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, Integer>{
	
	@Query("select h from HelpRequest h where h.category = ?1")
	Collection<HelpRequest> getRequestsByCategory(Category category);

	@Query("select h from HelpRequest h where h.user.id = ?1")
	Collection<HelpRequest> findRequestsByUser(int userId);
	
	@Query("select h from HelpRequest h where h.counselor.id = ?1")
	Collection<HelpRequest> findRequestsByCounselor(int counselorId);

	@Query("select h from HelpRequest h where h.status = 'OPEN' and ?1 not member of h.user.blockedCounselors")
	Collection<HelpRequest> getOpenRequests(Counselor counselor);
	
	@Query("select avg(u.helpRequests.size) from User u")
	Double getAvgHelpRequestsPerUser();

	@Query("select min(u.helpRequests.size) from User u")
	Integer getMinHelpRequestsPerUser();
	
	@Query("select max(u.helpRequests.size) from User u")
	Integer getMaxHelpRequestsPerUser();
	
	@Query("select stddev(u.helpRequests.size) from User u")
	Double getStdevHelpRequestsPerUser();
}

