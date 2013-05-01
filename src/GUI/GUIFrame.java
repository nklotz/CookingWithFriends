package GUI;

import java.util.Map;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javax.swing.JFrame;

import server.AutocorrectEngines;
import API.Wrapper;
import API.YummlyAPIWrapper;
import UserInfo.Account;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import UserInfo.Recipe;
import client.Client;

public class GUIFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private Client _client;
	private JFXPanel _panel;
	private Account _account;
	private GUIScene _kitchenScene, _homeScene, _searchScene, _recipeScene;
	private AutocorrectEngines _engines;
	private Map<KitchenName,Kitchen> _kitchens;
	private Wrapper _api;
	
	public GUIFrame(Client client, Account account, final Map<KitchenName,Kitchen> kitchens, AutocorrectEngines engines) {
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
    	//loadKitchenScene(new Kitchen("38 Transit", "69"));
    	loadKitchenScene(_kitchens.get("/k/1"));
    	_api = new YummlyAPIWrapper();
    	
    	//loadHomeScene();
	}
	
	public void loadSearchScene(){
		_searchScene = new SearchScene(_account, this, _kitchens, _api);
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			_panel.setScene(_searchScene.makeScene());
    		}
		});
    		
	}
	
	
	public void loadHomeScene(){
		_homeScene = new HomeScene(_account, this,_engines, _client);
		Platform.runLater(new Runnable() {
    		@Override
    		public void run(){
    			_panel.setScene(_homeScene.makeScene());
    		}
		});
	}
	
	public void loadKitchenScene(Kitchen kitchen){
		_kitchenScene = new KitchenScene(kitchen, this, _client);
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			_panel.setScene(_kitchenScene.makeScene());
    		}
		});
	}

	public void loadRecipeScene(Recipe recipe) {
		_recipeScene = new RecipeScene(recipe, _account, this, _client);
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    			_panel.setScene(_homeScene.makeScene());
    		}
		});
	}

}
