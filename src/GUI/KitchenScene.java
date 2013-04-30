package GUI;

import javafx.scene.Scene;
import UserInfo.Kitchen;

public class KitchenScene implements GUIScene {

	private Kitchen _kitchen;
	private GUIFrame _frame;
	
	public KitchenScene(Kitchen k, GUIFrame f){
		_kitchen = k;
		_frame = f;
	}
	
	@Override
	public Scene makeScene() {
		// TODO Auto-generated method stub
		return null;
	}

}
