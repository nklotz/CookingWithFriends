package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import GUI.HomeScene.RecipeHandler;
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
		final GridPane grid = new GridPane();
		grid.setStyle(Style.BACKGROUND);
		grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Scene scene = new Scene(grid, 1200, 500);
        
        Text scenetitle = new Text("Cooking with Friends: Kitchen- " + _kitchen.getName());
        scenetitle.setStyle(Style.PAGE_HEADER);
        grid.add(scenetitle, 0, 0, 2, 1);
        
        //ME
        Text Me = new Text("Me");
        Me.setStyle(Style.SECTION_HEADER);
        grid.add(Me, 0, 1);
        //pane
        GridPane userGrid = new GridPane();
        userGrid.setPrefSize(300, 200);
        userGrid.setStyle(Style.SECTIONS);
        userGrid.setHgap(5);
        grid.add(userGrid, 0, 2);
        //my info
        Text myInfo = new Text("My Info");
        myInfo.setStyle(Style.INFO_HEADER);
        userGrid.add(myInfo, 0, 0);
        userGrid.add(displayUserInfo(userGrid), 0, 1, 1, 3);
        //diet
        Text diet = new Text("Dietary Preferences");
        diet.setStyle(Style.INFO_HEADER);
        userGrid.add(diet, 1, 0);
        userGrid.add(displayPreferences(), 1, 1);
        //dislikes
        Text dislike = new Text("Dislikes");
        diet.setStyle(Style.INFO_HEADER);
        userGrid.add(dislike, 1, 2);
        userGrid.add(displayDislikes(), 1, 3);
        
        //MY RECIPES
        Text MyRecipes = new Text("My Recipes");
        MyRecipes.setStyle(Style.SECTION_HEADER);
        grid.add(MyRecipes, 0, 3);
        //pane
        GridPane recipeGrid = new GridPane();
        recipeGrid.setPrefSize(300, 200);
        recipeGrid.setStyle(Style.SECTIONS);
        grid.add(recipeGrid, 0, 4);
        //recipe list
        recipeGrid.add(displayList(350, 176, false, false, _account.getRecipes(), new RecipeHandler<ActionEvent>()), 0, 0);
        
        //MY FRIDGE
        Text MyFridge = new Text("My Fridge");
        MyFridge.setStyle(Style.SECTION_HEADER);
        grid.add(MyFridge, 1, 1);
        //pane 
        GridPane fridgeGrid = new GridPane();
        fridgeGrid.setPrefSize(300, 200);
        fridgeGrid.setStyle(Style.SECTIONS);
        grid.add(fridgeGrid, 1, 2);
        //fridge list
        fridgeGrid.add(displayList(350, 176, true, true, _account.getIngredients(), new EventHandler<ActionEvent>(){
        	@Override
    		public void handle(ActionEvent e) {
    			System.out.println("This doesn't do anything.");
        	}
        }), 0, 0);
        
        //MY SHOPPING LIST
        Text ShoppingList = new Text("My Shopping List");
        ShoppingList.setStyle(Style.SECTION_HEADER);
        grid.add(ShoppingList, 1, 3);
        //pane
        GridPane shoppingGrid = new GridPane();
        shoppingGrid.setPrefSize(300, 200);
        shoppingGrid.setStyle(Style.SECTIONS);
        grid.add(shoppingGrid, 1, 4);
        //shopping list
        shoppingGrid.add(displayList(350, 176, true, false, _account.getShoppingList(), new EventHandler<ActionEvent>(){
        	@Override
    		public void handle(ActionEvent e) {
    			System.out.println("This doesn't do anything.");
        	}
        }),0,0);
        
        //KITCHENS
        Text Kitchens = new Text("Kitchens");
        Kitchens.setStyle(Style.SECTION_HEADER);
        grid.add(Kitchens, 2, 1);
        //pane
        GridPane kitchenGrid = new GridPane();
        kitchenGrid.setPrefSize(300,430);
        kitchenGrid.setStyle(Style.SECTIONS);
        grid.add(kitchenGrid, 2, 2, 1, 3);
        //kitchen list
        kitchenGrid.add(displayKitchens(), 0, 0);
        
		return scene;
		
	}
	
	private Pane displayUserInfo(final GridPane grid){
        //my info
        GridPane info = new GridPane();
        info.setVgap(8);
        info.setStyle(Style.TEXT);
        info.setPrefSize(130, 160);
        Text name = new Text("Name: ");
        Text userName = new Text(_account.getName());
        Text area = new Text("Area: ");
        Text userArea = new Text(_account.getAddress());
        Text id = new Text("Email Id: ");
        Text userId = new Text(_account.getID());
        info.add(name,0,0);
        info.add(userName, 1, 0);
        info.add(area,0,1);
        info.add(userArea, 1, 1);
        info.add(id, 0, 2);
        info.add(userId, 1, 2);
        //edit Info Button
        Button editInfo = new Button("Edit Info");
        editInfo.setStyle(Style.BUTTON);
        HBox hbBtn = new HBox(10);
        hbBtn.setPrefHeight(80);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(editInfo);
        info.add(hbBtn, 1, 4);
        
        editInfo.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent e) {
            	grid.add(editPersonalInfo(grid), 0, 1, 1, 3);
            }
        });
        return info;
	}
	
	private Pane editPersonalInfo(final GridPane grid){
        GridPane info = new GridPane();
        info.setVgap(8);
        info.setStyle(Style.TEXT);
        info.setPrefSize(130, 160);
        Text name = new Text("Name: ");
        final TextField userName = new TextField();
        Text area = new Text("Area: ");
        final TextField userArea = new TextField();
        info.add(name,0,0);
        info.add(userName, 1, 0);
        info.add(area,0,1);
        info.add(userArea, 1, 1);
        Text id = new Text("Email Id: ");
        Text userId = new Text(_account.getID());
        info.add(id, 0, 2);
        info.add(userId, 1, 2);
        //edit Info Button
        Button save = new Button("Save");
        save.setStyle(Style.BUTTON);
        HBox hbBtn = new HBox(10);
        hbBtn.setPrefHeight(80);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(save);
        info.add(hbBtn, 1, 4);
        save.setOnAction(new EventHandler<ActionEvent>() {
          	 
            @Override
            public void handle(ActionEvent e) {
            	String name = userName.getText();
            	_account.setName(name);
            	_account.setAddress(userArea.getText());
            	updateAccount(); 
            	grid.add(displayUserInfo(grid), 0, 1, 1, 3);
            }
        });
        return info;
	}

}
