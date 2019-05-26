package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class WinnersForm {
	
	private Integer betPoolId;
	private Collection<String> winners;

	@NotNull
	public Integer getBetPoolId() {
		return betPoolId;
	}

	public void setBetPoolId(Integer betPoolId) {
		this.betPoolId = betPoolId;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getWinners() {
		return winners;
	}

	public void setWinners(Collection<String> winners) {
		this.winners = winners;
	}
}
