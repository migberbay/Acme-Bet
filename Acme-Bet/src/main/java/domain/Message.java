
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private Date				moment;
	private String				body;
	private String				subject;
	private String				priority;
	private Collection<String>	tags;
	private Boolean				flagSpam;


	// Constructors -----------------------------------------------------------

	public Message() {
		super();
	}

	// Getters and Setters ---------------------------------------------------

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotBlank
	@Pattern(regexp = "^HIGH|NEUTRAL|LOW$")
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(final String priority) {
		this.priority = priority;
	}

	@ElementCollection
	public Collection<String> getTags() {
		return this.tags;
	}

	public void setTags(final Collection<String> tags) {
		this.tags = tags;
	}

	public Boolean getFlagSpam() {
		return this.flagSpam;
	}

	public void setFlagSpam(final Boolean flagSpam) {
		this.flagSpam = flagSpam;
	}


	// Relationships ----------------------------------------------------------

	private UserAccount				sender;
	private Collection<UserAccount>	recipients;


	@Valid
	@ManyToOne(optional = false)
	public UserAccount getSender() {
		return this.sender;
	}

	public void setSender(final UserAccount sender) {
		this.sender = sender;
	}

	@Valid
	@ManyToMany
	@NotNull
	public Collection<UserAccount> getRecipients() {
		return this.recipients;
	}

	public void setRecipients(final Collection<UserAccount> recipients) {
		this.recipients = recipients;
	}

}
