package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import dao.ConnectionDAO;
import dao.Constants;
import dao.FxDialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Class to control guestpage events and widgets
 * 
 * @author
 *
 */
public class GuestController implements Initializable {

	// UI widgets
	@FXML
	private TextField user;
	
	@FXML
	private TextField firstname;
	
	@FXML
	private TextField guestno;
	
	@FXML
	private DatePicker dob;
	
	@FXML
	private ComboBox<String> gender;
	
	@FXML
	private Button btnLogin;

	private String guestNo;
	
	PopOver popOver = new PopOver();
	ValidationSupport validationSupport = new ValidationSupport();

	/**
	 * This method is called by the FXMLLoader when initialization is complete
	 */
	@Override 
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Male",
			        "Female",
			        "Other"
			    );
		gender.setItems(options);
		
		Label label = new Label("");
		// label.setPadding(new Insets(0, 10, 0, 10));
		label.setTextFill(Color.WHITE);
		HBox box = new HBox(0);
		box.setPadding(new Insets(5, 5, 5, 5));
		box.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
		box.getChildren().add(label);
		
		
		validationSupport.registerValidator(firstname, Validator.createRegexValidator("First name should be alphabetic only!", Constants.REGEX_NAME, Severity.ERROR));
		validationSupport.registerValidator(user, Validator.createRegexValidator("Username must be alphanumeric only!", Constants.REGEX_USERNAME, Severity.ERROR));
		validationSupport.registerValidator(gender, Validator.createEmptyValidator("Gender is required"));
		validationSupport.registerValidator(dob, Validator.createEmptyValidator("Date of birth is required"));
       
       guestNo = ConnectionDAO.getGuestNo();
       guestno.setText(guestNo);
       
       dob.setOnMouseEntered(mouseEvent -> {
			label.setText("Date of birth is required!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(dob);
		});

		dob.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});
       
		gender.setOnMouseEntered(mouseEvent -> {
			label.setText("Gender is required!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(gender);
		});

		gender.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});
		
		firstname.setOnMouseEntered(mouseEvent -> {
			label.setText("First name should be alphabetic only!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(firstname);
		});

		firstname.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});
       
		user.setOnMouseEntered(mouseEvent -> {
			label.setText("Username must be alphanumeric and do not contain any space !");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(user);
		});

		user.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});
	}

	@FXML
	private void login(ActionEvent ev) {
		
		String userName = user.getText().trim();
		String firstName = firstname.getText().trim();
		LocalDate dateOfBirth = dob.getValue();
		String sex = gender.getValue();
		
		if(validationSupport.isInvalid()) {
			String error = "";
			for(ValidationMessage msg : validationSupport.getValidationResult().getMessages()) {
				error += msg.getText() + "\n";
			}
			FxDialogs.showError("Football Ticket", error);
		} else {
			boolean success = ConnectionDAO.loginGuest(guestNo, userName, firstName, dateOfBirth, sex);	
			if (success) {
				FxDialogs.showInformation("Football Ticket", "Success!");
				loadDashboard();
			} else {
				FxDialogs.showError("Football Ticket", "Error occured!, try again!");
			}
		}
	}
	
	@FXML
	private void loadDashboard() {
		// Hide this current window
        btnLogin.getScene().getWindow().hide();
        
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
	
}
