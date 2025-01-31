package kodlama.io.HrmsProject.business.concretes;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlama.io.HrmsProject.business.absracts.CvService;
import kodlama.io.HrmsProject.business.absracts.JobSeekerService;
import kodlama.io.HrmsProject.business.absracts.UserActivationService;
import kodlama.io.HrmsProject.business.absracts.UserService;
import kodlama.io.HrmsProject.business.check.UserCheckService;
import kodlama.io.HrmsProject.core.utilities.result.DataResult;
import kodlama.io.HrmsProject.core.utilities.result.ErrorResult;
import kodlama.io.HrmsProject.core.utilities.result.Result;
import kodlama.io.HrmsProject.core.utilities.result.SuccessDataResult;
import kodlama.io.HrmsProject.core.utilities.result.SuccessResult;
import kodlama.io.HrmsProject.dataAcces.absracts.JobSeekerDao;
import kodlama.io.HrmsProject.entities.concretes.Cv;
import kodlama.io.HrmsProject.entities.concretes.JobSeeker;
import kodlama.io.HrmsProject.entities.concretes.UserActivation;



@Service
public class JobSeekerManager implements JobSeekerService {
 private JobSeekerDao jobSeekerDao;
 private CvService cvService;
 private UserService userService;
 private UserActivationService activationService;
 private UserCheckService userCheckService;
 

@Autowired
public JobSeekerManager(JobSeekerDao jobSeekerDao, CvService cvService, UserService userService,
		UserActivationService activationService, UserCheckService userCheckService) {
	super();
	this.jobSeekerDao = jobSeekerDao;
	this.cvService = cvService;
	this.userService = userService;
	this.activationService = activationService;
	this.userCheckService = userCheckService;
}




	@Override
	public Result add(JobSeeker jobSeeker) {
		jobSeekerDao.save(jobSeeker);
		cvService.add(new Cv(jobSeeker));
		return activationService.add(new UserActivation(jobSeeker));
		
	}





	@Override
	public Result update(JobSeeker jobSeeker) {
		JobSeeker j = getById(jobSeeker.getId()).getData();
				jobSeeker.setEmail(jobSeeker.getEmail() == null || jobSeeker.getEmail() == ""
								
				? j.getEmail() : jobSeeker.getEmail());
				
				jobSeeker.setPassword(jobSeeker.getPassword() == null || jobSeeker.getPassword() == ""
				? j.getPassword() : jobSeeker.getEmail());
				
				jobSeeker.setFirstName(jobSeeker.getFirstName() == null || jobSeeker.getFirstName() == ""
				? j.getFirstName() : jobSeeker.getFirstName());
				
				jobSeeker.setLastName(jobSeeker.getLastName()== null || jobSeeker.getLastName() == ""
				? j.getLastName() : jobSeeker.getLastName());
				
				jobSeeker.setIdentityNumber(jobSeeker.getIdentityNumber() == null || jobSeeker.getIdentityNumber() == ""
				? j.getIdentityNumber() : jobSeeker.getIdentityNumber());
				
				jobSeeker.setBirthOfYear(jobSeeker.getBirthOfYear()== null
				? j.getBirthOfYear() : jobSeeker.getBirthOfYear());
				
				jobSeekerDao.save(jobSeeker);
				return new SuccessResult("iş arayan güncellendi");
										
	}




	@Override
	public Result delete(int id) {
		jobSeekerDao.deleteById(id);
		return new SuccessResult("İş Arayan Silindi.");
	}





	@Override
	public DataResult<List<JobSeeker>> getAll() {
		return new SuccessDataResult<List<JobSeeker>>(jobSeekerDao.findAll());
	}





	@Override
	public DataResult<JobSeeker> getById(int id) {
		return new SuccessDataResult<JobSeeker>(jobSeekerDao.getById(id));
	}





	@Override
	public DataResult<JobSeeker> getByIdentityNumber(String identityNumber) {
		return new SuccessDataResult<JobSeeker>(jobSeekerDao.getByIdentityNumber(identityNumber));
		
	}
	private boolean checkIfEmailExists(String email) {

		boolean result = false;

		if (userService.getByEmail(email).getData() == null) {
			result = true;
		}

		return result;
	}

	private boolean checkIfIdentityNumberExists(String identityNumber) {

		boolean result = false;

		if (getByIdentityNumber(identityNumber).getData() == null) {
			result = true;
		}

		return result;
	}




	@Override
	public Result activate(String code) {
		UserActivation userActivation = activationService.getByCode(code).getData();
		if(userActivation == null) {
			return new ErrorResult("Geçersiz bir kod girdiniz.");
		}
		JobSeeker jobSeeker = getById(userActivation.getUser().getId()).getData();
		userActivation.setActivated(true);
		userActivation.setIsActivatedDate(LocalDateTime.now());
		
		jobSeekerDao.save(jobSeeker);
		activationService.update(userActivation);
		return new SuccessResult("Üye Olundu.");
				
	}


	@Override
	public DataResult<List<JobSeeker>> getAllByIsActivated(boolean isActivated) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	
	

	

}




