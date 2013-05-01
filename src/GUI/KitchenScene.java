package GUI;

import java.util.HashSet;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import UserInfo.Nameable;
import UserInfo.Recipe;
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
		final GridPane grid = new GridPane();
		grid.setStyle(Style.BACKGROUND);
		grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Scene scene = new Scene(grid, 1200, 500);
        
        Text scenetitle = new Text("Cooking with Friends: " + _kitchen.getName() + "'s Kitchen");
        scenetitle.setStyle(Style.PAGE_HEADER);
        grid.add(scenetitle, 0, 0, 2, 1);
        
        
		return null;
	}
}
