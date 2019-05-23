package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {
	
	private String spanishName;
	private String englishName;
	private String type;
	
	@Column(unique=true)
	@NotBlank
	public String getSpanishName() {
		return spanishName;
	}
	public void setSpanishName(String spanishName) {
		this.spanishName = spanishName;
	}
	@Column(unique=true)
	@NotBlank
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	@NotNull
	@Pattern(regexp="^REQUEST|POOL$")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	
	//Relationships

	

	
	

}
