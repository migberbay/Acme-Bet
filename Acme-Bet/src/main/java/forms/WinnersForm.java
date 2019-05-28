package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class WinnersForm {
	
	private Integer betPoolId;
	private String winner;

	@NotNull
	public Integer getBetPoolId() {
		return betPoolId;
	}

	public void setBetPoolId(Integer betPoolId) {
		this.betPoolId = betPoolId;
	}

	@NotEmpty
	@ElementCollection
	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
}
