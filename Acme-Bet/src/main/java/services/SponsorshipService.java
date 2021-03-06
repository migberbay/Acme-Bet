package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import security.LoginService;
import domain.BetPool;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {
	
	//Managed repository ----------------------------------------------------------------------------
	
	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	//Supporting services ----------------------------------------------------------------------------
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private Validator validator;
	
	//SIMPLE CRUD METHODS -----------------------------------------------------------------------------
	
	public Sponsorship create(){
		Sponsorship res = new Sponsorship();
		
		res.setIsActivated(true);
		res.setSponsor(sponsorService.findByPrincipal());

		return res;
	}
	
	public Sponsorship save(Sponsorship sponsorship) {
		Assert.isTrue(LoginService.hasRole("SPONSOR"));
		
		return sponsorshipRepository.saveAndFlush(sponsorship);
	}
	
	public void delete(Sponsorship sponsorship) {
		Assert.isTrue(LoginService.hasRole("SPONSOR"));
		
		sponsorshipRepository.delete(sponsorship);
	}
	
	public Sponsorship findOne(int sponsorshipId) {
		return sponsorshipRepository.findOne(sponsorshipId);
	}
	
	public Collection<Sponsorship> findAll() {
		return sponsorshipRepository.findAll();
	}
	
	//OTHER BUSINESS METHODS --------------------------------------------------------------------------------
	
	public Collection<Sponsorship> findByPrincipal() {
		Collection<Sponsorship> sponsorships;
		
		sponsorships = this.sponsorshipRepository.findBySponsor(sponsorService.findByPrincipal().getId());
		
		return sponsorships;
	}
	
	public void activateSponsorship(Sponsorship ss, Boolean res){
		Assert.isTrue(LoginService.hasRole("SPONSOR"));
		
		ss.setIsActivated(res);
		this.save(ss);
	}
	
	public Collection<Sponsorship> findByBetPool(BetPool betPool){
		return this.sponsorshipRepository.findByBetPool(betPool);
	}
	
//	
//	public Collection<Sponsorship> findByPosition(Position position) {
//		Collection<Sponsorship> sponsorships;
//		
//		sponsorships = this.sponsorshipRepository.findByPositionId(position.getId());
//		
//		return sponsorships;
//	}
//	
	public Sponsorship reconstruct(Sponsorship sponsorship, int id, BindingResult binding) {
		Sponsorship result;
		
		if(id==0) {
			result = sponsorship;
			result.setSponsor(this.sponsorService.findByPrincipal());
			result.setIsActivated(true);

		} else {
			result = this.sponsorshipRepository.findOne(id);
			result.setBanner(sponsorship.getBanner());
			result.setBetPool(sponsorship.getBetPool());
			result.setIsActivated(sponsorship.getIsActivated());
			result.setLink(sponsorship.getLink());
		}
		
		validator.validate(result, binding);
		if(binding.hasErrors()) {
			throw new ValidationException();
		}
		
		return result;
	}
	
	public Double getAvgSponsorshipsPerSponsor(){
		Double res = sponsorshipRepository.getAvgSponsorshipsPerSponsor();
		if(res==null)res=0d;
		return res;
	}
	
	public Integer getMinSponsorshipsPerSponsor(){
		Integer res = sponsorshipRepository.getMinSponsorshipsPerSponsor();
		if(res==null)res=0;
		return res;
	}
	
	public Integer getMaxSponsorshipsPerSponsor(){
		Integer res = sponsorshipRepository.getMaxSponsorshipsPerSponsor();
		if(res==null)res=0;
		return res;
	}

	public Double getStdevSponsorshipsPerSponsor(){
		Double res = sponsorshipRepository.getStdevSponsorshipsPerSponsor();
		if(res==null)res=0d;
		return res;
	}

}
