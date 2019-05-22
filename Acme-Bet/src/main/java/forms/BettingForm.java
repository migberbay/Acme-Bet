package forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class BettingForm {
	
	private Integer betPoolId;
	private Double amount;
	private String winner;
	
	@NotNull
	@NotBlank
	public Integer getBetPoolId() {
		return betPoolId;
	}
	public void setBetPoolId(Integer betPoolId) {
		this.betPoolId = betPoolId;
	}
	
	@NotBlank
	@Min(10)
	@Max(10000)
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@NotBlank
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	

}
