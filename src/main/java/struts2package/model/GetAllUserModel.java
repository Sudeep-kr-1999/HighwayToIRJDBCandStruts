package struts2package.model;

public class GetAllUserModel {

	private String user_email = "";
	private String user_name = "";

	public GetAllUserModel(String user_email, String user_name) {
		super();
		this.setUser_email(user_email);
		this.setUser_name(user_name);
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

}
