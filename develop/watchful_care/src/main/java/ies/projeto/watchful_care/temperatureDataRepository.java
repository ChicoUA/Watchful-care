package ies.projeto.watchful_care;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface temperatureDataRepository extends JpaRepository<temperatureData, Long>{
	List<temperatureData> findByPatientId(int id);
}
