package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class HelpRequest extends Actor {
	
	private String status;
	private String description;
	private Collection<String> attachements;
	private Date moment;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@ElementCollection
	public Collection<String> getAttachements() {
		return attachements;
	}
	public void setAttachements(Collection<String> attachements) {
		this.attachements = attachements;
	}
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	//Relationships
	private Counselor counselor;
	private Category category;
	private BetPool	betPool;

	@ManyToOne(optional=false)
	@Valid
	public Counselor getCounselor() {
		return counselor;
	}
	public void setCounselor(Counselor counselor) {
		this.counselor = counselor;
	}
	@Valid
	@ManyToOne(optional=false)
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@ManyToOne(optional=false)
	@Valid
	public BetPool getBetPool() {
		return betPool;
	}
	public void setBetPool(BetPool betPool) {
		this.betPool = betPool;
	}
	
	

	
	

	
	

}
