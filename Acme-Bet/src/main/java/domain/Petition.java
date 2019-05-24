package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import security.Authority;

@Entity
@Access(AccessType.PROPERTY)
public class Petition extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String status;
	private String rejectReason;


	// Getters and Setters ---------------------------------------------------
	
	@Pattern(regexp = "^" +"PENDING"+ "|" +"ACCEPTED" + "|" + "REJECTED" + "$")
	@NotNull
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	// Relationships ----------------------------------------------------------

	private User user;
	private Bet bet;

	@Valid
	@ManyToOne(optional = true)
	public User getUser() {
		return this.user;
	}
	
	public void setUser(final User user) {
		this.user = user;
	}

	@Valid
	@OneToOne(optional = false)
	public Bet getBet() {
		return bet;
	}

	public void setBet(Bet bet) {
		this.bet = bet;
	}

	


}
