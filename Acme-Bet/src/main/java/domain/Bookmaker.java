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
public class Bookmaker extends Actor {
	
	
	//Relationships
	Collection<BetPool> betPools;

	public Collection<BetPool> getBetPools() {
		return betPools;
	}

	public void setBetPools(Collection<BetPool> betPools) {
		this.betPools = betPools;
	}

}
