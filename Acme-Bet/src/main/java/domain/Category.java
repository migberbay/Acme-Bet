package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {
	
	private String spanishName;
	private String englishName;
	private String type;
	
	@Column(unique=true)
	public String getSpanishName() {
		return spanishName;
	}
	public void setSpanishName(String spanishName) {
		this.spanishName = spanishName;
	}
	@Column(unique=true)
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	@NotBlank
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	
	//Relationships

	

	
	

}
