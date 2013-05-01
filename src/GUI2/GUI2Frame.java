package GUI2;

import java.io.IOException;
import java.util.Map;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javax.swing.JFrame;

import server.AutocorrectEngines;
import API.Wrapper;
import API.YummlyAPIWrapper;
import GUI.GUIScene;
import UserInfo.Account;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import client.Client;

public class GUI2Frame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Client _client;
	private JFXPanel _panel;
	private Account _account;
	private GUIScene _kitchenScene, _homeScene, _searchScene, _recipeScene;
	private AutocorrectEngines _engines;
	private Map<KitchenName,Kitchen> _kitchens;
	private Wrapper _api;
	
	public GUI2Frame(Client client, Account account, final Map<KitchenName,Kitchen> kitchens, AutocorrectEngines engines) {
		super("Cooking with Friends!");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	System.out.println("CLOSEEEINGING WINDOWNNNNNNNNNN");
				_client.close();
		    }
		});
		
		this.setSize(1400, 1000);
    	this.setVisible(true);
    	
    	_panel = new JFXPanel();
    	this.add(_panel);
    	this.setSize(1400, 1000);
    	this.setVisible(true);
    	_panel.setPreferredSize(new java.awt.Dimension(1400,1000));
    	
    	_client = client;
    	_account = account;   	
    	_engines = engines;
    	_kitchens = kitchens;
    	_api = new YummlyAPIWrapper();
    	
    	
    	Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    
		    	Pane page;
				try {
					page = (Pane) FXMLLoader.load(GUI2Frame.class.getResource("CookingWithFriends.fxml"));
			        Scene scene = new Scene(page);
			        _panel.setScene(scene);
				} catch (IOException e) {
					System.out.println("ewrjhoewrjewr");
					e.printStackTrace();
				}
    	
    		}
		});
	}
	
}