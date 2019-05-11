package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends Actor {
	
	private String spanishName;
	private String englishName;
	private String type;
	
	public String getSpanishName() {
		return spanishName;
	}
	public void setSpanishName(String spanishName) {
		this.spanishName = spanishName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	
	//Relationships

	

	
	

}
