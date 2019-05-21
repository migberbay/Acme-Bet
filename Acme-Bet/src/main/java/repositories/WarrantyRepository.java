package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Warranty;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Integer>{

	@Query("select distinct b.warranty from BetPool b where b.bookmaker.id = ?1")
	Collection<Warranty> findWarrantyByBookmaker(int id);
}
