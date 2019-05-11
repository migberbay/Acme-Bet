package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Bet extends Actor {
	
	private String ticker;
	private Double amount;
	private Date moment;
	
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	//Relationships
	User owner;
	Collection<BetPool> betPools;

	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public Collection<BetPool> getBetPools() {
		return betPools;
	}
	public void setBetPools(Collection<BetPool> betPools) {
		this.betPools = betPools;
	}
	

	
	

}
