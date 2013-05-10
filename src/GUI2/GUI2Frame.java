package GUI2;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.swing.JFrame;

import server.AutocorrectEngines;
import UserInfo.Account;
import UserInfo.Invitation;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import client.Client;

public class GUI2Frame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Client _client;
	private JFXPanel _panel;
	private Account _account;
	private AutocorrectEngines _engines;
	private Map<KitchenName,Kitchen> _kitchens;
	private Controller2 _controller;
	
	public GUI2Frame(Client client, Account account, final Map<KitchenName,Kitchen> kitchens, AutocorrectEngines engines) {
		super("Cooking with Friends!");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				_client.close();
		    }
		});
			
    	_panel = new JFXPanel();
    	_panel.setPreferredSize(new java.awt.Dimension(1024, 680));

    	this.add(_panel);
    	this.setVisible(true);
    	this.setResizable(false);
    	this.pack();
    	
    	_client = client;
    	_account = account;   	
    	_engines = engines;
    	_kitchens = kitchens;  
    	
    	Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
				try {
					//Creates the GUI from an FXML file, then adds it to the javafx frame.
					URL location = this.getClass().getResource("CookingWithFriends update.fxml");
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(location);
					fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
					Parent p = (Parent) fxmlLoader.load();
			        _controller = (Controller2) fxmlLoader.getController();
			        Scene scene = new Scene(p);
			        _controller.setUp(_client, _account, _kitchens, _engines);
			        _panel.setScene(scene);
				} catch (IOException e) {
					System.out.println("ERROR: Failed to create javafx panel.");
				}
    		}
		});
	}
	
	/***
	 * The following methods serve as callbacks to the controller, and use Platform.runLater to 
	 * schedule updates on the JavaFX thread.
	 */
	
	/**
	 * Tells the controller to invite an
	 * @param userInDatabase
	 */
	public void sendEmail(final boolean userInDatabase){
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			//Displays the message asking if the user actually wants to invite the user.
    			_controller.sendInviteEmails(userInDatabase);
    		}
		});
	}
	
	/**
	 * Tells the controller to update the current kitchen.
	 */
	public void updateKitchen(){
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			_controller.reDisplayKitchen();
    		}
		});
	}
	
	/**
	 * Returns whether or not the old password is correct.
	 * @param matches
	 */
	public void changePasswords(final boolean matches){
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			_controller.changePassword(matches);
    		}
		});
	}
	
	/**
	 * Displays a new kitchen.
	 * @param k
	 */
	public void displayNewKitchen(final Kitchen k){
		Platform.runLater(new Runnable() {
			@Override
			public void run(){
				_controller.displayKitchen(k.getKitchenName());
			}
		});
	}
	
	public void updateKitchenDropDown(){
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			_controller.populateKitchenSelector();
    		}
		});
	}
	
	public void refreshSearchAccordian(){
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			_controller.refreshSearchAccordion();
    		}
		});
	}

	public void sendInvite(final Invitation invitation) {
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			_controller.recieveInvite(invitation);
    		}
		});
	}

	public void passNewKitchen(final Kitchen k) {
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			_controller.addNewKitchenToAccount(k);
    		}
		});
	}
	
}
