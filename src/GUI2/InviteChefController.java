package GUI2;

import java.net.URL;
import java.util.ResourceBundle;

import Email.Sender;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class InviteChefController extends AnchorPane implements Initializable  {

	@FXML private TextField chefName;
	@FXML private Label invalidEmail;
	@FXML private Button send;
	private Controller2 _controller;
	@FXML private Pane inviteToJoinPane;
	
	public void setController(Controller2 controller){
		_controller = controller;
	}
	
    public void checkAndSendEmail(ActionEvent event) {
    	_controller.checkAndSendEmail(chefName.getText());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	
    }
    
    public void hideJoinInvite(){
    	inviteToJoinPane.setVisible(false);
    	chefName.setDisable(false);
    }
    
    public void displayJoinInvite(){
    	inviteToJoinPane.setVisible(true);
    	chefName.setDisable(true);
    }
    
    public void sendInviteEmails(boolean userInDatabase){
		String email = chefName.getText();
		if(email != null){
			if(isValidEmail(email)){
				if(userInDatabase){
					chefName.setText("");
					_controller.inviteUserToKitchen(email);
				}
				else{
					displayJoinInvite();
				}
			}
			else{
				invalidEmail.setVisible(true);
			}
		}
	}
    
	public boolean isValidEmail(String email){
		if(email.trim().length()!=0){
			String[] atSplit = email.split("\\@");
			if(atSplit.length==2){
				String[] dotSplit = atSplit[1].split("\\.");
				if(dotSplit.length>1){
					return true;
				}
			}
		}
		return false;
	}
	
	public void hideInvalidEmail(){
		invalidEmail.setVisible(false);
		hideJoinInvite();
	}
	
	public void yesButtonListener(){
		_controller.inviteToJoinCWF(chefName.getText());
		hideJoinInvite();
		chefName.setText("");
	}


}