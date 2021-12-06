package dao;

/**
 * Class to keep constants used through out the application
 * 
 * @author
 *
 */
public class Constants {

	// Class level constants - change database parameters
	public static final String dbUrl = "jdbc:mysql://localhost:3306/footballticketdb";
	public static final String dbUser = "root";
	public static final String dbPassword = "root";

	// validation rules
	public static final String REGEX_USERNAME = "[A-za-z0-9]*";
	public static final String REGEX_NAME = "[A-Za-z ]{3,}";
	public static final String REGEX_PASSWORD = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	public static final String REGEX_EMAIL = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
	public static final String REGEX_PHONE = "[0-9]*";
}