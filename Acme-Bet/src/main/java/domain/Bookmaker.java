package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Bookmaker extends Actor {
	
	
	//Relationships
	Collection<BetPool> betPools;

	@ElementCollection
	@OneToMany(mappedBy = "bookmaker")
	public Collection<BetPool> getBetPools() {
		return betPools;
	}

	public void setBetPools(Collection<BetPool> betPools) {
		this.betPools = betPools;
	}

}
