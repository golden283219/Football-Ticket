package controllers;

import java.io.IOException;
import java.util.Optional;

import dao.FxDialogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Class to control home page
 * 
 * @author
 *
 */
public class HomepageController {

	// UI Widgets
	@FXML private Button btnExit;
	@FXML private Button btnLogin;
	@FXML private Button btnSignup;

	/**
	 * Method called on click of exit button
	 */
	@FXML
	public void exit(ActionEvent ev) {
		Optional<ButtonType> response = FxDialogs.showConfirm("Football Ticket", "Would you like to exit the application?");
		if (response.isPresent() && response.get().getText().equals("OK")) {
			System.exit(0);
        } else {
			ev.consume();
		}
	}

	/**
	 * Method called on click of login button
	 */
	@FXML
	public void login(ActionEvent ev) {
		btnLogin.getScene().getWindow().hide();
		try {
			Stage login = new Stage();
			login.setTitle("Football Ticket");
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
			Scene scene = new Scene(root);
			login.setScene(scene);
			login.show();
			login.setResizable(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method called on click of register
	 */
	@FXML
	public void signup(ActionEvent ev) {
		btnSignup.getScene().getWindow().hide();
		try {
			Stage signup = new Stage();
			signup.setTitle("Football Ticket");
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Registration.fxml"));
			Scene scene = new Scene(root);
			signup.setScene(scene);
			signup.show();
			signup.setResizable(false);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}