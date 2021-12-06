package dao;

import com.google.gson.Gson;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Class to handle map screen queries
 * 
 * @author
 *
 */
public class MapDao {

	/**
	 * Method to get all the location coordinates using a query
	 * 
	 * @param query
	 * @return
	 */
	public static ResultSet getAllColumns(String query) {
		System.out.println(query);
		try {
			PreparedStatement statement = ConnectionDAO.getConnection().prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			ConnectionDAO.getConnection().close();
			return resultSet;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getLocations(ArrayList<LocationVO> locationList) {
		// todao get update
		Gson gson = new Gson();

		return gson.toJson(locationList);
	}
}
