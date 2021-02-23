package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
	private int id;
	private String name;
	private String email;
	private String website;
	private String content;
	
	public Contact(String name, String email, String content) {
		super();
		this.name = name;
		this.email = email;
		this.content = content;
	}
	
}
