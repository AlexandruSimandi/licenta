package ro.asimandi.simsec.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
	
	@Id
	private String id;
	private String first_name;
	private String last_name;
	private Boolean analyzed;
	private Date last_analysis;
	public String getId() {
		return id;
	}
	public Boolean getAnalyzed() {
		return analyzed;
	}
	public void setAnalyzed(Boolean analyzed) {
		this.analyzed = analyzed;
	}
	public Date getLast_analysis() {
		return last_analysis;
	}
	public void setLast_analysis(Date last_analysis) {
		this.last_analysis = last_analysis;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
}
