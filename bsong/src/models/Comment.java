package models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	private int id;
	private int idUser;
	private int idSong;
	private String comment;
	private Timestamp dateCreate;
	private int status;
	
	public Comment(int idUser, int idSong, String comment) {
		super();
		this.idUser = idUser;
		this.idSong = idSong;
		this.comment = comment;
	}
	
}
