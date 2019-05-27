package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("select a from User a where a.userAccount = ?1") 
	User findByUserAccount(UserAccount ua);
	
	@Query("select distinct u from User u where u.bets.size = (select max(u1.bets.size) from User u1)")
	Collection<User> getUsersWMoreBets();
	
	@Query("select distinct u from User u where u.helpRequests.size = (select max(u1.helpRequests.size) from User u1)")
	Collection<User> getUsersWMoreRequests();
	
}
