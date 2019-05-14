package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {
	
	private Double funds;

	public Double getFunds() {
		return funds;
	}

	public void setFunds(Double funds) {
		this.funds = funds;
	}
	
	//Relationships
	Collection<Counselor> blockedCounselors;
	Collection<Bet> bets;
	Collection<Review> reviews;
	Collection<HelpRequest> helpRequests;
	
	@OneToMany
	public Collection<Counselor> getBlockedCounselors() {
		return blockedCounselors;
	}

	public void setBlockedCounselors(Collection<Counselor> blockedCounselors) {
		this.blockedCounselors = blockedCounselors;
	}
	@OneToMany
	public Collection<Bet> getBets() {
		return bets;
	}

	public void setBets(Collection<Bet> bets) {
		this.bets = bets;
	}
	@OneToMany
	public Collection<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Collection<Review> reviews) {
		this.reviews = reviews;
	}

	@OneToMany
	public Collection<HelpRequest> getHelpRequests() {
		return helpRequests;
	}

	public void setHelpRequests(Collection<HelpRequest> helpRequests) {
		this.helpRequests = helpRequests;
	}
	
	

}
