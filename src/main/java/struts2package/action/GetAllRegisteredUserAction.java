package struts2package.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import struts2package.databaseconnection.DatabaseConnection;
import struts2package.model.GetAllUserModel;

public class GetAllRegisteredUserAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final String USER_NAME = DatabaseConnection.getUserName();
	private static final String PASSWORD = DatabaseConnection.getPassword();
	private static final String DB_URL = DatabaseConnection.getDbURL();
	private static final String DRIVER = DatabaseConnection.getDriver();
	private static Connection connection;
	private Statement statement;
	private ResultSet rs;
	private List<GetAllUserModel> response = new ArrayList<GetAllUserModel>();
	private String responseString = "success";

	public String getResponseString() {
		return this.responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public List<GetAllUserModel> getResponse() {
		return this.response;
	}

	public void setResponse(List<GetAllUserModel> response) {
		this.response = response;
	}

	private List<GetAllUserModel> getAllUser() throws SQLException {
		List<GetAllUserModel> list = new ArrayList<GetAllUserModel>();
		try {
			String Query = "select * from highwaytoirdatabase.usertable";
			this.statement = connection.createStatement();
			this.rs = this.statement.executeQuery(Query);
			while (rs.next()) {
				GetAllUserModel userModel = new GetAllUserModel(rs.getString("user_email"),
						rs.getString("user_username"));
				list.add(userModel);
			}
		} catch (Exception e) {
			this.setResponseString("failure");
			e.printStackTrace();
		} finally {
			this.statement.close();
		}
		return list;
	}

	public String execute() throws SQLException {
		System.out.print("inside action of GetAllRegisteredUserAction");
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			System.out.println(connection);
			try {
				this.setResponse(this.getAllUser());
				System.out.println(this.getResponse());
			} catch (Exception e) {
				System.out.println(e);
				this.setResponseString("failure");
			} finally {
				connection.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			this.setResponseString("failure");
		} catch (Exception e) {
			e.printStackTrace();
			this.setResponseString("failure");
		}
		return getResponseString();
	}

}
