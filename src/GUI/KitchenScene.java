package GUI;

import javafx.scene.Scene;
import UserInfo.Kitchen;
import client.Client;

public class KitchenScene implements GUIScene {

	private Kitchen _kitchen;
	private GUIFrame _frame;
	private Client _client;
	
	public KitchenScene(Kitchen kitchen, GUIFrame frame, Client client){
		_kitchen = kitchen;
		_frame = frame;
		_client = client;
	}
	
	@Override
	public Scene makeScene() {
		// TODO Auto-generated method stub
		return null;
	}

}
