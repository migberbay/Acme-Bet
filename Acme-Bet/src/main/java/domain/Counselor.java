package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Max;

@Entity
@Access(AccessType.PROPERTY)
public class Counselor extends Actor {
	
	private Double funds;
	private Double fare;

	
	public Double getFunds() {
		return funds;
	}

	public void setFunds(Double funds) {
		this.funds = funds;
	}

	@Max(2)
	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}
	
	
	

}
