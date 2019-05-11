package domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class BetPool extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String ticker;
	private String title;
	private Date moment;
	private String description;
	private List<String> participants;
	private List<String> winners;
	private Double minRange;
	private Double maxRange;
	private Date startDate;
	private Date endDate;
	private Date resultDate;
	private Boolean isFinal;
	
	
	// Getters and Setters ---------------------------------------------------
	
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getParticipants() {
		return participants;
	}
	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}
	public List<String> getWinners() {
		return winners;
	}
	public void setWinners(List<String> winners) {
		this.winners = winners;
	}
	public Double getMinRange() {
		return minRange;
	}
	public void setMinRange(Double minRange) {
		this.minRange = minRange;
	}
	public Double getMaxRange() {
		return maxRange;
	}
	public void setMaxRange(Double maxRange) {
		this.maxRange = maxRange;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getResultDate() {
		return resultDate;
	}
	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
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
	private Collection<HelpRequest> helpRequests;
	private Collection<Bet> bets;


	public Bookmaker getBookmaker() {
		return bookmaker;
	}
	public void setBookmaker(Bookmaker bookmaker) {
		this.bookmaker = bookmaker;
	}
	public Warranty getWarranty() {
		return warranty;
	}
	public void setWarranty(Warranty warranty) {
		this.warranty = warranty;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Collection<HelpRequest> getHelpRequests() {
		return helpRequests;
	}
	public void setHelpRequests(Collection<HelpRequest> helpRequests) {
		this.helpRequests = helpRequests;
	}
	public Collection<Bet> getBets() {
		return bets;
	}
	public void setBets(Collection<Bet> bets) {
		this.bets = bets;
	}
	
	

}
