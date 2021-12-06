package controllers;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 1-Flow starts from here which invokes landing page 2-after landing page
 * either login page flow is followed or registration or exit 3- after login or
 * from guest page or registration page MASTER PAGE IS opened ON master page
 * left side is menu and each menuitem is rendered on the same window.
 */
public class LoadApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Homepage.fxml"));
			Scene scene = new Scene(root, 500, 300);
			stage.setTitle("Football Ticket");
			stage.setScene(scene);
			stage.show();
			stage.setResizable(false);
			stage.centerOnScreen();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
