package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import domain.*;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;

@Service
@Transactional
public class FinderService {

	//Managed Repository -----
	@Autowired
	private FinderRepository finderRepository;
	
	//Supporting Services -----
	
	@Autowired
	private UserService		userService;

	@Autowired
	private ConfigurationService configurationService;

	
	//Simple CRUD methods -----
	public Finder create(){
		Finder result;
		result = new Finder();
		result.setBetPools(new HashSet<BetPool>());
		return result;
	}

	
	public Collection<Finder> findAll(){
		Collection<Finder> result;
		result = this.finderRepository.findAll();
		Assert.notNull(result);
		return result;
	}
	
	public Finder findOne(int finderId){
		Finder finder;
		finder = this.finderRepository.findOne(finderId);
		return finder;
	}

	public Finder findByPrincipal(){
		Finder result;
		User principal= userService.findByPrincipal();
		result = findByUser(principal);
		/*In case is expired, we set all its parameters to null*/
		if(result.getMoment() == null || isVoid(result) || isExpired(result)){
			result = setAllToParametersToNullAndSave(result);
		}
		return result;
	}
	
	
	public Finder save(Finder finder){
		Finder result;
		Assert.notNull(finder);
		if (finder.getId() != 0) {
			Assert.isTrue(this.esDeActorActual(finder));
			if(isVoid(finder)){
				finder.setMoment(null);
				finder.setBetPools(new HashSet<BetPool>());
			}else{
				finder.setMoment(DateUtils.addMilliseconds(new Date(),-1));
				filterParades(finder);
			}

		}else{
			Assert.isNull(findByUser(finder.getUser()));

			Assert.isTrue(isVoid(finder));

			Assert.isNull(finder.getMoment());
		}
		result = this.finderRepository.save(finder);
		return result;
	}
	
	public Finder clear(Finder finder){
		Assert.notNull(finder);
		Assert.isTrue(this.esDeActorActual(finder));
		finder.setMoment(null);
		return this.setAllToParametersToNullAndSave(finder);
	}

	public void delete(Finder finder){
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		this.finderRepository.delete(finder);
	}

	public Finder findByUser(User user){
		Finder result;

		result = finderRepository.findByUser(user.getId());

		return result;
	}
	
	//Other business methods -----
	
	private Boolean esDeActorActual(final Finder finder) {
		Boolean result;

		final User principal = this.userService.findByPrincipal();
		final User memberFromFinder = finderRepository.findOne(finder.getId()).getUser();

		result = principal.equals(memberFromFinder);
		return result;
	}

	public Boolean isVoid(final Finder finder){
		Boolean result;

		result = (finder.getKeyword() == null || finder.getKeyword() == "")
				&& finder.getMinRange() == null && finder.getMaxRange() == null
				&& finder.getOpeningDate() == null && finder.getEndDate() == null
				&& finder.getCategory() == null;

		return result;
	}

	private Finder setAllToParametersToNullAndSave(Finder finder){
		Assert.isTrue(finder.getMoment() == null || isExpired(finder));
		Finder result;

		finder.setMoment(null);
		finder.setKeyword(null);
		finder.setMinRange(null);
		finder.setMaxRange(null);

		finder.setOpeningDate(null);
		finder.setEndDate(null);
		finder.setCategory(null);
		finder.setBetPools(null);

		result = save(finder);
		return result;
	}

	public Boolean isExpired(Finder finder){
		Boolean result = true;
		Configuration configuration = configurationService.find();
		Double cacheTimeInHours = configuration.getFinderCacheTime();
		Date expirationMoment =  new Date();
			/*Adding Hours*/
			expirationMoment= DateUtils.addHours(expirationMoment, cacheTimeInHours.intValue());
			/*Adding Hours*/
			expirationMoment = DateUtils.addMinutes(expirationMoment,
				Double.valueOf(60 * (cacheTimeInHours - cacheTimeInHours.intValue())).intValue());

			result = finder.getMoment().after(expirationMoment);

		return result;
	}

	/*Don't declare finder parameter as final*/
	public Finder filterParades(Finder finder){
		String keyword;
		Date openingDate, endDate;
		Double minRange, maxRange;
		Integer categoryId;

		Collection<BetPool> positions;

		keyword = finder.getKeyword();

		minRange = finder.getMinRange();
		maxRange = finder.getMaxRange();
		openingDate = finder.getOpeningDate();
		endDate = finder.getEndDate();
		categoryId = finder.getCategory();

		positions = finderRepository.filterBetPools(keyword, maxRange, minRange, openingDate, endDate, categoryId);
		finder.setBetPools(positions);
		return finder;
	}


}