package struts2package.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.struts2.json.annotations.JSON;
import struts2package.databaseconnection.DatabaseConnection;
import struts2package.model.SetUserResponseModel;

public class RegisterNewUserAction {
	private static final String USER_NAME = DatabaseConnection.getUserName();
	private static final String PASSWORD = DatabaseConnection.getPassword();
	private static final String DB_URL = DatabaseConnection.getDbURL();
	private static final String DRIVER = DatabaseConnection.getDriver();
	private static Connection connection;
	private PreparedStatement preparedStatement;
	private int no_of_rows_affected;
	private String username;
	private String email;
	private String password;
	private SetUserResponseModel response = null;
	private String responseString = "success";

	@JSON(name = "Username")
	public void setUsername(String userName) {
		this.username = userName;
	}

	@JSON(name = "Email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JSON(name = "Password")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getResponseString() {
		return this.responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public String getUsername() {
		return this.username;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public SetUserResponseModel getResponse() {
		return this.response;
	}

	public void setResponse(SetUserResponseModel response) {
		this.response = response;
	}

	public void setNumberOfRowsAffected(int num_of_rows_affected) {
		this.no_of_rows_affected = num_of_rows_affected;
	}

	public int getNumberOfRowsAffected() {
		return this.no_of_rows_affected;
	}

	private SetUserResponseModel setUser() throws SQLException {
		int number_of_rows_affected = 0;
		SetUserResponseModel res = null;
		try {
			String postQuery = "INSERT INTO highwaytoirdatabase.usertable(user_email,user_username,user_password)VALUES(?,?,?)";
			this.preparedStatement = connection.prepareStatement(postQuery);
			this.preparedStatement.setString(1, this.getEmail());
			this.preparedStatement.setString(2, this.getUsername());
			this.preparedStatement.setString(3, this.getPassword());
			number_of_rows_affected = this.preparedStatement.executeUpdate();
			this.setNumberOfRowsAffected(number_of_rows_affected);
			res = new SetUserResponseModel(getNumberOfRowsAffected());
		} catch (Exception e) {
			this.setResponseString("failure");
			e.printStackTrace();
		} finally {
			this.preparedStatement.close();
		}
		return res;
	}

	public String execute() throws SQLException {
		System.out.println(this.getEmail());
		System.out.println(this.getUsername());
		System.out.println(this.getPassword());
		System.out.print("inside action of GetAllRegisteredUserAction");
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			System.out.println(connection);
			try {
				this.setResponse(this.setUser());
				System.out.println(this.getResponse());
			} catch (Exception e) {
				System.out.println(e);
				this.setResponseString("failure");
			} finally {
				connection.close();
			}
		} catch (ClassNotFoundException e) {
			this.setResponseString("failure");
			e.printStackTrace();
		} catch (Exception e) {
			this.setResponseString("failure");
			e.printStackTrace();
		}

		return this.getResponseString();
	}

}
