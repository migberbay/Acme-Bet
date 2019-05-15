package domain;

import java.util.Collection;
import java.util.Date;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class BetPool extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String ticker;
	private String title;
	private Date moment;
	private String description;
	private Collection<String> participants;
	private Collection<String> winners;
	private Double minRange;
	private Double maxRange;
	private Date startDate;
	private Date endDate;
	private Date resultDate;
	private Boolean isFinal;
	
	
	// Getters and Setters ---------------------------------------------------
	
	@Pattern(regexp="^([0]{1}[0-9]{1}|[1]{1}[0-8]{1})([0]{1}[1-9]{1}|[1]{1}[0-2]{1})([0-2]{1}[1-9]{1}|[3]{1}[0-1]{1})[-][A-Z0-9]{6}$")
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Size(min = 2)//con 1 solo no es una apuesta no?
	@ElementCollection
	public Collection<String> getParticipants() {
		return participants;
	}
	public void setParticipants(Collection<String> participants) {
		this.participants = participants;
	}
	
	@ElementCollection
	public Collection<String> getWinners() {
		return winners;
	}
	public void setWinners(Collection<String> winners) {
		this.winners = winners;
	}
	@NotNull
	public Double getMinRange() {
		return minRange;
	}
	public void setMinRange(Double minRange) {
		this.minRange = minRange;
	}
	@NotNull
	public Double getMaxRange() {
		return maxRange;
	}
	public void setMaxRange(Double maxRange) {
		this.maxRange = maxRange;
	}
	
	@Future
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getResultDate() {
		return resultDate;
	}
	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
	
	@NotNull
	public Boolean getIsFinal() {
		return isFinal;
	}
	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	
	//Relationships--------------------------------------------------------------------
	
	private Bookmaker bookmaker;
	private Warranty warranty;
	private Category category;
	private Collection<Finder> finders;
	private Collection<HelpRequest> helpRequests;
	private Collection<Bet> bets;

	@ManyToOne(optional= false)
	@Valid
	public Bookmaker getBookmaker() {
		return bookmaker;
	}
	public void setBookmaker(Bookmaker bookmaker) {
		this.bookmaker = bookmaker;
	}
	@ManyToOne(optional = false)
	@Valid
	public Warranty getWarranty() {
		return warranty;
	}
	public void setWarranty(Warranty warranty) {
		this.warranty = warranty;
	}
	
	@ManyToOne(optional = false)
	@Valid
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@ManyToMany
	@Valid
	public Collection<Finder> getFinders() {
		return finders;
	}
	public void setFinders(Collection<Finder> finders) {
		this.finders = finders;
	}
	
	@ElementCollection
	@OneToMany(mappedBy = "betPool")
	@Valid
	public Collection<HelpRequest> getHelpRequests() {
		return helpRequests;
	}
	public void setHelpRequests(Collection<HelpRequest> helpRequests) {
		this.helpRequests = helpRequests;
	}
	
	@ElementCollection
	@OneToMany(mappedBy = "betPool")
	@Valid
	public Collection<Bet> getBets() {
		return bets;
	}
	public void setBets(Collection<Bet> bets) {
		this.bets = bets;
	}
	
	

}
