package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer>{


}
