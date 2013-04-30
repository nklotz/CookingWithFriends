package GUI;

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
	APIInfo _autocorrect;
	
	public SearchScene(Account account, GUIFrame frame, APIInfo info){
		_account = account;
		_frame = frame;
		_autocorrect = info;
	}
	
	@Override
	public Scene makeScene() {
		final GridPane grid = new GridPane();
		grid.setStyle(Style.BACKGROUND);
		grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Scene scene = new Scene(grid, 1000, 500);
        
        Text scenetitle = new Text("Cooking with Friends: Home");
        scenetitle.setStyle(Style.PAGE_HEADER);
        grid.add(scenetitle, 0, 0, 2, 1);
        
        //ME
        Text Me = new Text("Me");
        Me.setStyle(Style.SECTION_HEADER);
        grid.add(Me, 0, 1);
        //pane
        GridPane userGrid = new GridPane();
        userGrid.setPrefSize(250, 200);
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
        recipeGrid.add(displayList(350, 176, false, _account.getRecipes()), 0, 0);
        
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
        fridgeGrid.add(displayList(350, 176, true, _account.getIngredients()), 0, 0);
        
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
        shoppingGrid.add(displayList(350, 176, true, _account.getShoppingList()),0,0);
        
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

}
