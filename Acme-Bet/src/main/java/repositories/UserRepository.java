package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("select a from User a where a.userAccount = ?1") 
	User findByUserAccount(UserAccount ua);
	

	
}
