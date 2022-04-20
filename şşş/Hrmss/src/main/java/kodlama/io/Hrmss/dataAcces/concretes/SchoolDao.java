package kodlama.io.Hrmss.dataAcces.concretes;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import kodlama.io.Hrmss.entities.concretes.School;

public interface SchoolDao extends JpaRepository<School, Integer> {
	List<School> getByCv_Id(int cvId);
	List<School> getByCv_Id(int cvId, Sort sort);
}
