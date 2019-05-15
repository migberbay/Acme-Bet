package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Review extends Actor {
	
	private String description;
	private Collection<String> attachements;
	private Date moment;
	private Boolean isFinal;
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	@NotBlank
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
	
	public Boolean getIsFinal() {
		return isFinal;
	}
	
	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}

	//Relationships
	private User user;
	private Counselor counselor;

	@Valid
	@ManyToOne (optional=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Valid
	@ManyToOne (optional=false)
	public Counselor getCounselor() {
		return counselor;
	}
	public void setCounselor(Counselor counselor) {
		this.counselor = counselor;
	}
	

	
	

}
