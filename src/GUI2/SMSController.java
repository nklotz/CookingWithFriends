package GUI2;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.twilio.sdk.TwilioRestException;

import sms.SmsSender;

import UserInfo.Ingredient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class SMSController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Label invalidPhone;
    @FXML private TextField sendNumber;
    
    private List<Ingredient> _shoppingList;
    private Stage _stage;

    public void setUp(Stage stage, List<Ingredient> shoppingList) {
    	_shoppingList = shoppingList;
    	_stage = stage;
    }
    
    @FXML
    void hideInvalidNumber(KeyEvent event) {
    	invalidPhone.setVisible(false);
    }

    @FXML
    void sendSMS(ActionEvent event) {
    	String number = sendNumber.getText();
    	if (number != "") {
	    	number = number.replaceAll("[^0-9]", "");
	    	if (number.length() == 10) {
		    	try {
		    		Long.parseLong(number);
		    	}
		    	catch (NumberFormatException ex) {
		    		invalidPhone.setText("Invalid number. Please enter 10 digits.");
		    		invalidPhone.setVisible(true);
		    		return;
		    	}
		    	
		    	try {
					SmsSender.send(number, _shoppingList);
				} catch (TwilioRestException e) {
					invalidPhone.setText("Error sending message.");
		    		invalidPhone.setVisible(true);
		    		return;
				}
		    	_stage.close();
	    	}
    	}
    	invalidPhone.setText("Invalid number. Please enter 10 digits.");
		invalidPhone.setVisible(true);
    }

    @FXML
    void initialize() {
        assert invalidPhone != null : "fx:id=\"invalidPhone\" was not injected: check your FXML file 'SMSWindow.fxml'.";
        assert sendNumber != null : "fx:id=\"sendNumber\" was not injected: check your FXML file 'SMSWindow.fxml'.";
    }

}

