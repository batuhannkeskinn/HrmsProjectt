package kodlama.io.Hrmss.business.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


import kodlama.io.Hrmss.business.abstracts.UserActivationService;
import kodlama.io.Hrmss.core.utilities.result.DataResult;
import kodlama.io.Hrmss.core.utilities.result.Result;
import kodlama.io.Hrmss.core.utilities.result.SuccessDataResult;
import kodlama.io.Hrmss.core.utilities.result.SuccessResult;
import kodlama.io.Hrmss.dataAcces.concretes.UserActivationDao;
import kodlama.io.Hrmss.entities.concretes.UserActivation;

public class UserActivationManager  implements UserActivationService{

		private UserActivationDao userActivationDao;
	public UserActivationManager(UserActivationDao activationDao) {
			super();
			this.userActivationDao = userActivationDao;
		}

	@Override
	public Result add(UserActivation userActivation) {
		userActivation.setActivated(false);
		userActivation.setCode(GenerateCode());
		userActivation.setIsActivatedDate(LocalDateTime.now());
		
		userActivationDao.save(userActivation);
		return null;
		
	}

	private String GenerateCode() {
		UUID code = UUID.randomUUID();
		return code.toString();
	}

	@Override
	public Result update(UserActivation userActivation) {
		userActivationDao.save(userActivation);
		return new SuccessResult();
	}

	@Override
	public Result delete(int id) {
		userActivationDao.deleteById(id);
		return new SuccessResult();
	}

	@Override
	public DataResult<List<UserActivation>> getAll() {
		return new SuccessDataResult<List<UserActivation>>(userActivationDao.findAll());

	}

	@Override
	public DataResult<UserActivation> getById(int id) {
		return new SuccessDataResult<UserActivation>(userActivationDao.getById(id));

	}

	@Override
	public DataResult<UserActivation> getByCode(String code) {
		return new SuccessDataResult<UserActivation>(userActivationDao.getByCode(code));
	}

	@Override
	public DataResult<UserActivation> getByUserId(int userId) {
		return new SuccessDataResult<UserActivation>(userActivationDao.getByUser_Id(userId));
	}

	@Override
	public DataResult<List<UserActivation>> getAllByIsActivated(Boolean isActivated) {
		return new SuccessDataResult<List<UserActivation>>(userActivationDao.getByIsActivated(isActivated));
	}

}
