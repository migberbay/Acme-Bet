package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private Date moment;
	
	private String keyword;
	private Double minRange;
	private Double maxRange;
	private Date openingDate;
	private Date endDate;
	private Integer category;//this should be a select


	// Getters and Setters ---------------------------------------------------
	
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
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

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}



	// Relationships ----------------------------------------------------------

	private User user;
	private Collection<BetPool> betPools;

	@Valid
	@OneToOne(optional = false)
	public User getUser() {
		return this.user;
	}
	
	public void setUser(final User user) {
		this.user = user;
	}

	@Valid
	@ManyToMany(mappedBy = "finders")
	public Collection<BetPool> getBetPools() {
		return betPools;
	}

	public void setBetPools(Collection<BetPool> betPools) {
		this.betPools = betPools;
	}


}
