package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Bet extends Actor {
	
	private String ticker;
	private Double amount;
	private Date moment;
	
	@Column(unique = true)
	@Pattern(regexp="^([0]{1}[0-9]{1}|[1]{1}[0-8]{1})([0]{1}[1-9]{1}|[1]{1}[0-2]{1})([0-2]{1}[1-9]{1}|[3]{1}[0-1]{1})[-][A-Z0-9]{6}$")
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	@NotNull
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	
	//Relationships
	
	private User user;
	private BetPool betPool;
	
	@NotNull
	@ManyToOne (optional = false)
	@Valid
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@NotNull
	@ManyToOne(optional = false)
	@Valid
	public BetPool getBetPool() {
		return betPool;
	}
	public void setBetPool(BetPool betPool) {
		this.betPool = betPool;
	}
	
	
	
	

	
	

}
