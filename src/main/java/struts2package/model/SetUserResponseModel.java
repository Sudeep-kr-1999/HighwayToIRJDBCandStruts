package struts2package.model;

public class SetUserResponseModel {

	private int number_of_rows_affected;

	public SetUserResponseModel(int no_of_rows_affected) {
		super();
		this.setNumber_of_rows_affected(no_of_rows_affected);
	}

	public int getNumber_of_rows_affected() {
		return number_of_rows_affected;
	}

	public void setNumber_of_rows_affected(int number_of_rows_affected) {
		this.number_of_rows_affected = number_of_rows_affected;
	}

}
