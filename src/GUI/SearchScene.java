package GUI;

import client.Client;
import UserInfo.Account;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import server.APIInfo;
import UserInfo.Account;

public class SearchScene implements GUIScene {

	Account _account;
	GUIFrame _frame;
	Client _client;
	APIInfo _autocorrect;
	
	public SearchScene(Account account, GUIFrame frame, APIInfo info){
		_account = account;
		_frame = frame;
		_autocorrect = info;
	}
	
	@Override
	public Scene makeScene() {
		
	}
}
