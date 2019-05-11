
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	//Bussines methods-----
	@Query("select m from Message m where m.sender = ?1")
	Collection<Message> findBySender(UserAccount sender);

}
