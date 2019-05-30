package forms;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import domain.HelpRequest;

public class ReviewForm {
	
	private String description;
	private Collection<String> attachments;
	private Double score;
	
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
	
	@NotNull
	@Range(min=0,max=10)
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}


	private HelpRequest helpRequest;
	
	@Valid
	@NotNull
	public HelpRequest getHelpRequest() {
		return helpRequest;
	}

	public void setHelpRequest(HelpRequest helpRequest) {
		this.helpRequest = helpRequest;
	}


}
