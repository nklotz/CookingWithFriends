package GUI;

import client.Client;
import UserInfo.Account;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SearchScene implements GUIScene {

	Account _account;
	GUIFrame _frame;
	Client _client;
	
	public SearchScene(Account account, GUIFrame frame){
		_account = account;
		_frame = frame;
	}
	
	@Override
	public Scene makeScene() {
		
	}
}
