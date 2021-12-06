package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Class to handle Database
 * 
 * @author
 *
 */
public class ConnectionDAO {

	/**
	 * Method to get connection handle to DB
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPassword);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method to execute the query in Database
	 * 
	 * @param query
	 * @return void
	 */
	public static void executeQuery(String query) {
		Connection conn = getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getUserNamesFromDB() {
		ArrayList<String> users = new ArrayList<>();
		ResultSet rs = MapDao.getAllColumns("Select userName from Person");
		try {
			while (rs.next())
				users.add(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public static ArrayList<String> getQuestions() {
		ArrayList<String> data = new ArrayList<String>();
		try {
			//
			ResultSet rs = MapDao.getAllColumns("select question from questions");
			while (rs.next()) {
				data.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static boolean submitQuestion(String question) {
		boolean success = false;
		try {
			Connection conn = ConnectionDAO.getConnection();
			String query = " insert into questions(question, create_timestamp) values (?, curdate())";

			// create the mysql insert prepared statement
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, question);

			// execute the prepared statement
			if (preparedStmt.executeUpdate() > 0) {
				success = true;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public static String getGuestNo() {
		String guestNo = null;
		ResultSet rs = MapDao.getAllColumns("select max(guestid) from guest");
		try {
			rs.next();
			guestNo = "" + (rs.getInt(1) + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return guestNo;
	}

	public static boolean loginGuest(String guestNo, String userName, String firstName, LocalDate dateOfBirth,
			String sex) {
		boolean success = false;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dob = dateOfBirth.format(formatter);

		try {
			Connection conn = ConnectionDAO.getConnection();
			String query = " insert into guest (GuestID, firstName, username, gender, age) values (?, ?, ?, ?, ?)";

			// create the mysql insert prepared statement
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, guestNo);
			preparedStmt.setString(2, firstName);
			preparedStmt.setString(3, userName);
			preparedStmt.setString(4, sex);
			preparedStmt.setString(5, dob);

			// execute the prepared statement
			if (preparedStmt.executeUpdate() > 0) {
				success = true;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public static boolean register(String firstName, String surName, String gender, String contact, String address,
			String userName, String postalCode, String email, String password, String confirmPassword) {
		boolean success = false;
		try {
			Connection conn = ConnectionDAO.getConnection();
			String query = " insert into person "
					+ "(firstName, surName, gender, contact, address,userName,postalCode,email,password,confirmPassword)"
					+ " values (?, ?, ?, ?, ?,?,?,?,?,?)";

			// create the mysql insert prepared statement
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, firstName);
			preparedStmt.setString(2, surName);
			preparedStmt.setString(3, gender);
			preparedStmt.setString(4, contact);
			preparedStmt.setString(5, address);
			preparedStmt.setString(6, userName);
			preparedStmt.setString(7, postalCode);
			preparedStmt.setString(8, email);
			preparedStmt.setString(9, password);
			preparedStmt.setString(10, confirmPassword);

			// execute the prepared statement
			if (preparedStmt.executeUpdate() > 0) {
				success = true;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public static boolean login(String username, String password) {
		boolean success = false;
		PreparedStatement statement;

		try {

			Connection conn = getConnection();
			statement = conn
					.prepareStatement("select userName from person where userName = ? and password= ?");

			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next())
				success = true;

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

}
