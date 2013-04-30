package GUI;

import javafx.scene.Scene;
import UserInfo.Kitchen;
import client.Client;

public class KitchenScene implements GUIScene {

	Kitchen _kitchen;
	GUIFrame _frame;
	Client _client;
	
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
