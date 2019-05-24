package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class HelpRequest extends DomainEntity {
	
	private String status;
	private String description;
	private Collection<String> attachments;
	private Date moment;
	private String ticker;
	
	@NotNull
	@Pattern(regexp="^OPEN|PENDING|SOLVED$")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ElementCollection
	public Collection<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(Collection<String> attachments) {
		this.attachments = attachments;
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
	
	
	@Column(unique=true)
	@NotNull
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	
	
	
	//Relationships

	private Counselor counselor;
	private User user;
	private Category category;
	private BetPool	betPool;

	@ManyToOne(optional=true)
	@Valid
	public Counselor getCounselor() {
		return counselor;
	}
	public void setCounselor(Counselor counselor) {
		this.counselor = counselor;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Valid
	@ManyToOne(optional=false)
	@NotNull
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@ManyToOne(optional=false)
	@Valid
	@NotNull
	public BetPool getBetPool() {
		return betPool;
	}
	public void setBetPool(BetPool betPool) {
		this.betPool = betPool;
	}
	
}
