package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {
	
	private Double funds;

	public Double getFunds() {
		return funds;
	}

	public void setFunds(Double funds) {
		this.funds = funds;
	}
	
	//relationships
	Collection<Sponsorship> sponsorships;

	@ElementCollection
	@OneToMany(mappedBy = "sponsor")
	public Collection<Sponsorship> getSponsorships() {
		return sponsorships;
	}

	public void setSponsorships(Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}
	

}
