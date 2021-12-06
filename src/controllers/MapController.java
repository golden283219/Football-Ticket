package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import dao.Browser;
import dao.FxDialogs;
import dao.LocationVO;
import dao.MapDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MapController implements Initializable {

	@FXML
	private Button search;

	@FXML
	private TextField lattitude;
	
	@FXML
	private TextField longitude;

	@FXML
	private VBox map;

	private Browser webview;

	@FXML
	private MenuItem dashboard;

	@FXML
	private Menu mainMenu;

	@FXML
	private MenuBar menuBar;

	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

		// set menu bar icon
		mainMenu.setGraphic(new ImageView("/Images/stackedlines.png"));

		// add map's web view to vbox
		webview = new Browser("");
		map.getChildren().add(webview);
	}

	@FXML
	private void search() {

		ArrayList<LocationVO> locationList = new ArrayList<LocationVO>();

		// Parameters from UI
		String lat = lattitude.getText();
		String lon = longitude.getText();

		ArrayList<String> latLong = null;
		if (!lat.equals("") && !lon.equals("")) {
			try {
				Double.parseDouble(lat);
				Double.parseDouble(lon);
				
				latLong = new ArrayList<>();
				latLong.add(lat);
				latLong.add(lon);
				
				LocationVO locationVO = new LocationVO();
				locationVO.setLat(Double.parseDouble(lat));
				locationVO.setLng(Double.parseDouble(lon));
				
				locationList.add(locationVO);
				
				
				webview.getWebView().getEngine().executeScript("clearMarkers()");
				webview.getWebView().getEngine().executeScript("initMap()");
				if (latLong != null) {
					webview.getWebView().getEngine().executeScript(
							"changeCenter(new google.maps.LatLng(" + latLong.get(0) + "," + latLong.get(1) + "))");
					webview.getWebView().getEngine().executeScript("changeZoom(15)");
				}
				
				
				String script = "drawMap(" + MapDao.getLocations(locationList) + ")";
				webview.getWebView().getEngine().executeScript(script);
			} catch (Exception e) {
				FxDialogs.showError("Football Ticket", "Please enter valid lattitude and longitude!");
			}
		} else {
			FxDialogs.showError("Football Ticket", "Please enter lattitude and longitude!");
		}
	}

	@FXML
	private void loadDashboard(ActionEvent ev) {
		// Hide this current window
		menuBar.getScene().getWindow().hide();

		Stage dashboard = new Stage();
		dashboard.setTitle("Football Ticket");
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Dashboard.fxml"));
			Scene scene = new Scene(root);
			dashboard.setScene(scene);
			dashboard.show();
			dashboard.setResizable(false);
			dashboard.centerOnScreen();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void loadQuestions(ActionEvent ev) {
		// Hide this current window
		menuBar.getScene().getWindow().hide();

		Stage questions = new Stage();
		questions.setTitle("Football Ticket");
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Questions.fxml"));
			Scene scene = new Scene(root);
			questions.setScene(scene);
			questions.show();
			questions.setResizable(false);
			questions.centerOnScreen();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void logout(ActionEvent ev) {

		Optional<ButtonType> response = FxDialogs.showConfirm("Football Ticket",
				"Would you like to Logout the application?");
		if (response.isPresent() && response.get().getText().equals("OK")) {
			// Hide this current window
			menuBar.getScene().getWindow().hide();

			Stage homepage = new Stage();
			homepage.setTitle("Football Ticket");
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/FXML/Homepage.fxml"));
				Scene scene = new Scene(root);
				homepage.setScene(scene);
				homepage.show();
				homepage.setResizable(false);
				homepage.centerOnScreen();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			ev.consume();
		}
	}
}
