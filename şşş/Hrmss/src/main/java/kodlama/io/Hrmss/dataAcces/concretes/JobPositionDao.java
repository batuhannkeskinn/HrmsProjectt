package kodlama.io.Hrmss.dataAcces.concretes;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlama.io.Hrmss.entities.concretes.JobPosition;

public interface JobPositionDao extends JpaRepository<JobPosition, Integer>{
	JobPosition getByJobName(String jobName);
	JobPosition getByJobExplanation(String jobExplanation);
}
