
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

	// no es necesario viene por defecto esta como referencia
	@Override
	@Query("select a from Box a where a.id = ?1")
	Box findOne(Integer Id);

	//Bussines methods-----

	@Query("select b from Box b where b.userAccount.id = ?1")
	Collection<Box> findByUserAccountId(Integer accountId);

	@Query("select b from Box b where b.userAccount.id = ?1 and b.name like ?2")
	Box findByUserAccountIdAndName(int accountId, String boxName);

	@Query("select b from Box b where b.parentBox = ?1")
	Collection<Box> findByParent(Box parent);
}
