package GUI;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javax.swing.JFrame;

import UserInfo.Account;
import client.Client;

public class GUIFrame extends JFrame {

	private Client _client;
	private JFXPanel _panel;
	private Account _account;
	private GUIFrame _frame;
	
	public GUIFrame(Client client, Account account){
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
    	
    	Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			HomeScene home = new HomeScene(_account, _frame);
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
		HomeScene homeCopy = new HomeScene(_account, this);
		_panel.setScene(homeCopy.makeScene());
	}
	
	public void loadKitchenScene(String kitchenID){
		//_client.
		
	}
}
