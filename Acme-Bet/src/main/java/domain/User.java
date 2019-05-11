package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

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
	Collection<Counselor> blockedCounserlors;
	Collection<Bet> bets;
	Collection<Review> reviews;
	Collection<HelpRequest> helpRequests;
	
	public Collection<Counselor> getBlockedCounserlors() {
		return blockedCounserlors;
	}

	public void setBlockedCounserlors(Collection<Counselor> blockedCounserlors) {
		this.blockedCounserlors = blockedCounserlors;
	}

	public Collection<Bet> getBets() {
		return bets;
	}

	public void setBets(Collection<Bet> bets) {
		this.bets = bets;
	}

	public Collection<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Collection<Review> reviews) {
		this.reviews = reviews;
	}

	public Collection<HelpRequest> getHelpRequests() {
		return helpRequests;
	}

	public void setHelpRequests(Collection<HelpRequest> helpRequests) {
		this.helpRequests = helpRequests;
	}
	
	

}
