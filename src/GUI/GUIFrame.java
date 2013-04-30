package GUI;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javax.swing.JFrame;

import server.APIInfo;
import UserInfo.Account;
import UserInfo.Kitchen;
import client.Client;

public class GUIFrame extends JFrame {

	private Client _client;
	private JFXPanel _panel;
	private Account _account;
	private Kitchen _kitchen;
	private GUIFrame _frame;
	private GUIScene _kitchenScene, _homeScene, _searchScene;
	private APIInfo _engines;
	
	public GUIFrame(Client client, Account account, APIInfo info){
		super("Cooking with Friends!");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 550);
    	this.setSize(1000, 550);
    	this.setVisible(true);
    	
    	_panel = new JFXPanel();
    	this.add(_panel);
    	this.setSize(1000, 550);
    	this.setVisible(true);
    	_panel.setPreferredSize(new java.awt.Dimension(950,550));
    	
    	_account = account;
    	_frame = this;
    	_engines = info;
    	Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			HomeScene home = new HomeScene(_account, _frame, _engines);
    			_panel.setScene(home.makeScene());
    		}
    		
    		
    	});
		
	}
	
	public void loadCopyScene(){
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			CopyOfHomeScene homeCopy = new CopyOfHomeScene(_account, _frame);
    			_panel.setScene(homeCopy.makeScene());
    		}
		});
    		
	}
	
	public void loadHomeScene(){
		HomeScene homeCopy = new HomeScene(_account, this, _engines);
		_panel.setScene(homeCopy.makeScene());
	}
	
	public void loadKitchenScene(Kitchen kitchen){
		_kitchen = kitchen;
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			//CopyOfHomeScene homeCopy = new CopyOfHomeScene(_account, _frame);
    			KitchenScene kitchenScene = new KitchenScene(_kitchen, _frame);
    			_panel.setScene(kitchenScene.makeScene());
    		}
		});
	}

}
