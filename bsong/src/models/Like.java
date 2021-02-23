package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
	private int id;
	private int idUser;
	private int idSong;
	private int status;
	
	public Like(int idUser, int idSong) {
		super();
		this.idUser = idUser;
		this.idSong = idSong;
	}
	
}
