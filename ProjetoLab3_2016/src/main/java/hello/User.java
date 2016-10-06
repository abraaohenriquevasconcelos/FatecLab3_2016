package hello;



import java.util.LinkedList;
import java.util.List;

public class User {
	
	private String userName;
	private String password;
	private String nivelAcesso;
	
	
	
	public User(String userName, String password, String nivelAcesso) {
		this.userName = userName;
		this.password = password;
		this.nivelAcesso = nivelAcesso;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNivelAcesso() {
		return nivelAcesso;
	}
	public void setQuestion(String nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}
	
	
	
	
	
	
}
