package ies.projeto.watchful_care;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface emergencyRepository extends JpaRepository<emergency, Long>{
	List<emergency> findByPatientId(int id);
}
