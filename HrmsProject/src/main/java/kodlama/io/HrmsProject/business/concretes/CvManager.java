package kodlama.io.HrmsProject.business.concretes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kodlama.io.HrmsProject.business.absracts.ConverLetterService;
import kodlama.io.HrmsProject.business.absracts.CvService;
import kodlama.io.HrmsProject.business.absracts.EducationService;
import kodlama.io.HrmsProject.business.absracts.ExperienceService;
import kodlama.io.HrmsProject.core.utilities.result.DataResult;
import kodlama.io.HrmsProject.core.utilities.result.Result;
import kodlama.io.HrmsProject.core.utilities.result.SuccessDataResult;
import kodlama.io.HrmsProject.core.utilities.result.SuccessResult;
import kodlama.io.HrmsProject.dataAcces.absracts.CvDao;
import kodlama.io.HrmsProject.entities.concretes.Cv;
import kodlama.io.HrmsProject.entities.dto.CvDto;

@Service
public class CvManager implements CvService  {
	private CvDao cvDao;
	private ConverLetterService coverLetterService;
	private EducationService educationService;
	private ExperienceService experienceService;
	
	@Autowired
	public CvManager(CvDao resumeDao, ConverLetterService coverLetterService, EducationService educationService,
			ExperienceService experienceService) {
		super();
		this.cvDao = cvDao;
		this.coverLetterService = coverLetterService;
		this.educationService = educationService;
		this.experienceService = experienceService;
	}

	@Override
	public Result add(Cv cv) {
		cv.setCreationDate(LocalDateTime.now());
		cvDao.save(cv);
		return new SuccessResult("Özgeçmiş eklendi.");
	}

	@Override
	public Result update(Cv cv) {
		cv = getById(cv.getId()).getData();
		cv.setCreationDate(LocalDateTime.now());
		cvDao.save(cv);
		return new SuccessResult("Özgeçmiş Güncellendi");
	}

	@Override
	public Result delete(int id) {
		cvDao.deleteById(id);
		return new SuccessResult("Özgeçmiş Silindi");
	}

	@Override
	public DataResult<List<Cv>> getAll() {
		Sort sort = Sort.by(Sort.Direction.DESC,"creationDate");
		return new SuccessDataResult<List<Cv>>(cvDao.findAll(sort));
	}

	@Override
	public DataResult<Cv> getById(int id) {
		return new SuccessDataResult<Cv>(cvDao.getById(id));
	}

	@Override
	public Result addConverLetterToCv(int cvId, int converLetterId) {
		Cv cv = getById(cvId).getData();
		cv.setConverLetter(coverLetterService.getById(converLetterId).getData());

		update(cv);
		return new SuccessResult("Ön yazı özgeçmişe eklendi.");
	}

	@Override
	public Result deleteCoverLetterFromCv(int cvId) {
		Cv cv = getById(cvId).getData();
		cv.setConverLetter(null);

		update(cv);
		return new SuccessResult("Ön yazı özgeçmişten kaldırıldı.");
	}

	@Override
	public DataResult<List<CvDto>> getAllCvDetailsByActivatedJobSeeker() {
	List<CvDto> cvs = new ArrayList<CvDto>();
	for (Cv cv : getAll().getData()) {
		if(cv.getJobSeeker().getUserActivation().isActivated() == true) {
			cvs.add(getCvDetailsByJobSeekerId(cv.getJobSeeker().getId()).getData());
		}
	};
	return new SuccessDataResult<List<CvDto>>(cvs);
	}


	@Override
	public DataResult<Cv> getByJobSeekerId(int jobSeekerId) {
		return new SuccessDataResult<Cv>(cvDao.getByJobSeeker_Id(jobSeekerId));
	}

	@Override
	public DataResult<CvDto> getCvDetailsByJobSeekerId(int jobSeekerId) {
		Cv cv = getByJobSeekerId(jobSeekerId).getData();
		
		CvDto cvDto = new CvDto(cv.getId(),cv.getCreationDate(),cv.getJobSeeker(),cv.getConverLetter(),educationService.getAllByCvIdSortedByGraduationDate(cv.getId()).getData(),experienceService.getAllByCvIdSortedByFinishDate(cv.getId()).getData());
		return new SuccessDataResult<CvDto>(cvDto);
	}
}

