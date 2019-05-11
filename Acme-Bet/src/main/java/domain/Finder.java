package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private Date moment;
	
	private Double minimumBet;
	private Date openingDate;
	private Date endDate;
	private String category;//this should be a select


	// Getters and Setters ---------------------------------------------------
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}
	

	public Double getMinimumBet() {
		return minimumBet;
	}

	public void setMinimumBet(Double minimumBet) {
		this.minimumBet = minimumBet;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}



	// Relationships ----------------------------------------------------------

	private User user;
	private Collection<BetPool> betPools;

	@Valid
	@OneToOne(optional = false)
	public User getUser() {
		return this.user;
	}
	
	public void setUser(final User user) {
		this.user = user;
	}

	@Valid
	@ManyToMany
	public Collection<BetPool> getBetPools() {
		return betPools;
	}

	public void setBetPools(Collection<BetPool> betPools) {
		this.betPools = betPools;
	}


}
