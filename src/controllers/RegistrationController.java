package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationResult;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RegistrationController implements Initializable {

	@FXML
	private TextField firstName;

	@FXML
	private TextField surName;

	@FXML
	private ComboBox<String> gender;

	@FXML
	private TextField contact;

	@FXML
	private TextField address;

	@FXML
	private TextField userName;

	@FXML
	private TextField postalCode;

	@FXML
	private TextField email;

	@FXML
	private PasswordField password;

	@FXML
	private PasswordField confirmPassword;

	@FXML
	private Button btnSignup;

	PopOver popOver = new PopOver();
	
	ArrayList<String> existingUsernames;
	
	ValidationSupport validationSupport = new ValidationSupport();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> options = FXCollections.observableArrayList("Male", "Female", "Other");
		gender.setItems(options);

		Label label = new Label("");
		// label.setPadding(new Insets(0, 10, 0, 10));
		label.setTextFill(Color.WHITE);
		HBox box = new HBox(0);
		box.setPadding(new Insets(5, 5, 5, 5));
		box.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
		box.getChildren().add(label);

		existingUsernames = ConnectionDAO.getUserNamesFromDB();
		
		validationSupport.registerValidator(firstName, Validator.createRegexValidator("First name should be atleast 3 alphabetic characters!", Constants.REGEX_NAME, Severity.ERROR));
		validationSupport.registerValidator(surName, Validator.createRegexValidator("Last name should be atleast 3 alphabetic characters!", Constants.REGEX_NAME, Severity.ERROR));
		validationSupport.registerValidator(gender, Validator.createEmptyValidator("Gender is required"));
		validationSupport.registerValidator(email, Validator.createRegexValidator("Email should be valid!", Constants.REGEX_EMAIL, Severity.ERROR));
		validationSupport.registerValidator(contact, Validator.createRegexValidator("Contact No should be numeric only!", Constants.REGEX_PHONE, Severity.ERROR));
		validationSupport.registerValidator(address, Validator.createEmptyValidator("Address is required"));
		validationSupport.registerValidator(postalCode, Validator.createEmptyValidator("Postalcode is required"));
		validationSupport.registerValidator(userName, Validator.createRegexValidator("Username must be alphanumeric only!", Constants.REGEX_USERNAME, Severity.ERROR));
		validationSupport.registerValidator(password, Validator.createRegexValidator(
						"Password should be 6-20 characters long and must contain uppercase, "
								+ "lowercase characters, numbers and one of (@#$%) characters!",
						Constants.REGEX_PASSWORD, Severity.ERROR));
		//validationSupport.registerValidator(confirmPassword, Validator.createEqualsValidator("Password must match!", Arrays.asList(password.getText())));
		validationSupport.registerValidator(confirmPassword, (control, content) -> {
			return ValidationResult.fromMessageIf(control, "Passwords must match!", Severity.ERROR, !password.getText().equals(confirmPassword.getText())); 
		});

		address.setOnMouseEntered(mouseEvent -> {
			label.setText("Address is required!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(address);
		});

		address.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});

		postalCode.setOnMouseEntered(mouseEvent -> {
			label.setText("Postalcode is required!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(postalCode);
		});

		postalCode.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});

		firstName.setOnMouseEntered(mouseEvent -> {
			label.setText("First name should be alphabetic only!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(firstName);
		});

		firstName.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});

		surName.setOnMouseEntered(mouseEvent -> {
			label.setText("Last name should be alphabetic only!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(surName);
		});

		surName.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});

		contact.setOnMouseEntered(mouseEvent -> {
			label.setText("Contact No should be numeric only!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(contact);
		});

		contact.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});

		userName.setOnMouseEntered(mouseEvent -> {
			label.setText("Username must be alphanumeric only!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(userName);
		});

		userName.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});

		email.setOnMouseEntered(mouseEvent -> {
			label.setText("Email should be valid!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(email);
		});

		email.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});

		password.setOnMouseEntered(mouseEvent -> {
			label.setText("Password should be 6-20 characters long and must contain uppercase,\n"
					+ "lowercase characters, numbers and one of (@#$%) characters!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(password);
		});

		password.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});

		confirmPassword.setOnMouseEntered(mouseEvent -> {
			label.setText("Password should match!");
			popOver.setContentNode(box);
			// Show PopOver when mouse enters label
			popOver.show(confirmPassword);
		});

		confirmPassword.setOnMouseExited(mouseEvent -> {
			// Hide PopOver when mouse exits label
			popOver.hide();
		});
	}

	@FXML
	public void signup(ActionEvent ev) {
		String fname = firstName.getText().trim();
		String lname = surName.getText().trim();
		String sex = gender.getValue();
		String phone = contact.getText().trim();
		String add = address.getText().trim();
		String user = userName.getText().trim();
		String postcode = postalCode.getText().trim();
		String emailid = email.getText().trim();
		String pass = password.getText().trim();
		String conpass = confirmPassword.getText().trim();

		if(validationSupport.isInvalid()) {
			String error = "";
			for(ValidationMessage msg : validationSupport.getValidationResult().getMessages()) {
				error += msg.getText() + "\n";
			}
			FxDialogs.showError("Football Ticket", error);
		} else {
			if (existingUsernames.contains(user)) {
				FxDialogs.showError("Football Ticket", "Username already exists!");
				userName.setText("");
				password.setText("");
				confirmPassword.setText("");
			}
			else {
				boolean success = ConnectionDAO.register(fname, lname, sex, phone, add, user, postcode, emailid, pass, conpass);
				if (success) {
					FxDialogs.showInformation("Football Ticket", "Registration Successful!");
					loadDashboard();
				} else {
					FxDialogs.showError("Football Ticket", "Error occured, try again!");
					userName.setText("");
				}
			}
		}
	}

	private void loadDashboard() {
		// Hide this current window
		btnSignup.getScene().getWindow().hide();

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
