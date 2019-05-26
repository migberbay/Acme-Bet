package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	
	@Query("select r from Review r where r.user.id = ?1")
	Collection<Review> findReviewsByUser(int userId);

	@Query("select r from Review r where r.counselor.id = ?1")
	Collection<Review> findReviewsByCounselor(int counselorId);
}