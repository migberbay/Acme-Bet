package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {
	
	private Double funds;

	@NotNull
	public Double getFunds() {
		return funds;
	}

	public void setFunds(Double funds) {
		this.funds = funds;
	}
	
	//relationships
	private Collection<Sponsorship> sponsorships;

	@ElementCollection
	@Valid
	@OneToMany(mappedBy = "sponsor")
	public Collection<Sponsorship> getSponsorships() {
		return sponsorships;
	}

	public void setSponsorships(Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}
	

}
