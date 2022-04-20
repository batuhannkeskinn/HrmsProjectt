package kodlama.io.Hrmss.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import kodlama.io.Hrmss.business.abstracts.EducationService;
import kodlama.io.Hrmss.core.utilities.result.DataResult;
import kodlama.io.Hrmss.core.utilities.result.Result;
import kodlama.io.Hrmss.core.utilities.result.SuccessDataResult;
import kodlama.io.Hrmss.core.utilities.result.SuccessResult;
import kodlama.io.Hrmss.dataAcces.concretes.EducationDao;
import kodlama.io.Hrmss.entities.concretes.Education;

public class EducationManager implements EducationService{

	private EducationDao educationDao;

	@Autowired
	public EducationManager(EducationDao educationDao) {
		this.educationDao = educationDao;
	}

	@Override
	public Result add(Education education) {

		educationDao.save(education);
		return new SuccessResult("Eğitim eklendi.");
	}

	@Override
	public Result update(Education education) {

		educationDao.save(education);
		return new SuccessResult("Eğitim güncellendi.");
	}

	 

		

	@Override
	public DataResult<List<Education>> getAll() {
		return new SuccessDataResult<List<Education>>(educationDao.findAll());
	}

	@Override
	public DataResult<Education> getById(int id) {
		return new SuccessDataResult<Education>(educationDao.getById(id));
	}

	@Override
	public DataResult<List<Education>> getAllByCvId(int cvId) {
		return new SuccessDataResult<List<Education>>(educationDao.getByCv_Id(cvId));
	}

	@Override
	public DataResult<List<Education>> getAllByCvIdSortedByGraduationDate(int cvId) {

		Sort sort = Sort.by(Sort.Direction.DESC, "graduationDate");

		return new SuccessDataResult<List<Education>>(educationDao.getByCv_Id(cvId, sort));
	}

	@Override
	public Result delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}



	
	

}
