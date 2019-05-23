package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {
	
	private Double funds;

	@NotNull
	public Double getFunds() {
		return funds;
	}

	public void setFunds(Double funds) {
		this.funds = funds;
	}
	
	//Relationships
	private Collection<Counselor> blockedCounselors;
	private Collection<Bet> bets;
	private Collection<Review> reviews;
	private Collection<HelpRequest> helpRequests;
	
	@OneToMany
	@Valid
	public Collection<Counselor> getBlockedCounselors() {
		return blockedCounselors;
	}

	public void setBlockedCounselors(Collection<Counselor> blockedCounselors) {
		this.blockedCounselors = blockedCounselors;
	}
	
	@OneToMany
	@Valid
	public Collection<Bet> getBets() {
		return bets;
	}

	public void setBets(Collection<Bet> bets) {
		this.bets = bets;
	}
	
	@OneToMany
	@Valid
	public Collection<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Collection<Review> reviews) {
		this.reviews = reviews;
	}

	@OneToMany(mappedBy="user")
	@Valid
	public Collection<HelpRequest> getHelpRequests() {
		return helpRequests;
	}

	public void setHelpRequests(Collection<HelpRequest> helpRequests) {
		this.helpRequests = helpRequests;
	}
	
	

}
