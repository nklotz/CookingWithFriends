package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.util.HashSet;
import java.util.Set;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import UserInfo.Account;
import UserInfo.Recipe;

import client.Client;

public class HomeWindow extends JFrame {

	private Account _account;
	private Client _client;
	private JTextArea _ingredients;
	private JTextArea _shoppingList;
	private JTextArea _recipes;
	
	
	public HomeWindow(Account account, Client client){
    	super("Cooking with Friends!");
    	_account = account;
    	_client = client;
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	final JFXPanel fxPanel = new JFXPanel();
    	this.add(fxPanel);
    	this.setSize(1000, 550);
    	this.setVisible(true);
    	fxPanel.setPreferredSize(new java.awt.Dimension(950,550));
    	
    	Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    		initFX(fxPanel);
    		}
    	});
    }
    
    private void initFX(JFXPanel fxPanel) {
    	fxPanel.setScene(makeScene());
    }
    
    
	private Scene makeScene() {
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
        recipeGrid.add(displayRecipes(), 0, 0);
        
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
        fridgeGrid.add(displayIngredients(), 0, 0);
        
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
        shoppingGrid.add(displayShoppingList(),0,0);
        
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
        Text userName = new Text(_account.getUserId());
        Text area = new Text("Area: ");
        Text userArea = new Text(_account.getAddress());
        info.add(name,0,0);
        info.add(userName, 1, 0);
        info.add(area,0,1);
        info.add(userArea, 1, 1);
        //edit Info Button
        Button editInfo = new Button("Edit Info");
        editInfo.setStyle(Style.BUTTON);
        HBox hbBtn = new HBox(10);
        hbBtn.setPrefHeight(80);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(editInfo);
        info.add(hbBtn, 1, 2);
        
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
        //edit Info Button
        Button save = new Button("Save");
        save.setStyle(Style.BUTTON);
        HBox hbBtn = new HBox(10);
        hbBtn.setPrefHeight(80);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(save);
        info.add(hbBtn, 1, 2);
        save.setOnAction(new EventHandler<ActionEvent>() {
          	 
            @Override
            public void handle(ActionEvent e) {
            	String newID = userName.getText();
            	_account.setUserId(newID);
            	_account.setAddress(userArea.getText());
            	updateAccount(); 
            	grid.add(displayUserInfo(grid), 0, 1, 1, 3);
            }
        });
        return info;
	}
	
	public GridPane displayPreferences(){
		GridPane info = new GridPane();
        info.setVgap(8);
        info.setStyle(Style.TEXT);
        info.setPrefSize(130, 80);
        //TODO: ACCOUNT.getPreferences
        Button edit = new Button("+/-");
        edit.setStyle(Style.BUTTON);
        HBox hbBtn = new HBox(10);
        hbBtn.setPrefHeight(80);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(edit);
        info.add(hbBtn, 1, 2);
        return info;
	}
	
	public GridPane displayDislikes(){
		GridPane dislikes = new GridPane();
		dislikes.setStyle(Style.TEXT);
		dislikes.setPrefSize(130, 80);
		//TODO: ACCOUNT.getDislikes (do we really need this?)
		Button edit = new Button("+/-");
		edit.setStyle(Style.BUTTON);
		HBox hbBtn = new HBox(10);
        hbBtn.setPrefHeight(80);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(edit);
        dislikes.add(hbBtn, 1, 2);
        return dislikes;
	}
	
	public GridPane displayRecipes(){
		GridPane recipes = new GridPane();
		recipes.setHgap(3);
		recipes.setPrefSize(350, 176);
		recipes.setStyle(Style.TEXT);
		ScrollPane rScroll = new ScrollPane();//Scrolling Panel
		rScroll.setStyle(Style.TEXT);
		rScroll.setPrefSize(222, 176);
		rScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		rScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		rScroll.setFitToWidth(true);
		//grid panel for buttons
		GridPane rList = new GridPane();
		rList.setPrefSize(300, 200);
		rList.setVgap(2);
		rScroll.setContent(rList);
		Set<Recipe> recipeSet = _account.getRecipes();//TODO: can we alphabetical order these?
		//TODO: get rid of the fake recipes
		recipeSet.add(new MockRecipe("Beef Stew"));
		recipeSet.add(new MockRecipe("Eye of Newt"));
		recipeSet.add(new MockRecipe("Tongue of Cat"));
		recipeSet.add(new MockRecipe("Fritatta"));
		recipeSet.add(new MockRecipe("Modest Mice"));
		recipeSet.add(new MockRecipe("Burger"));
		recipeSet.add(new MockRecipe("Duck Confit"));
		recipeSet.add(new MockRecipe("Toad in a Hole"));
		recipeSet.add(new MockRecipe("Toad in a Blanket"));
		recipeSet.add(new MockRecipe("Special Lassi"));
		int i = 0;
		for (Recipe r : recipeSet){
			rList.add(recipeButton(r),0,i);
			i++;
		}
		recipes.add(rScroll, 0, 0);
		Button edit = new Button("+/-");
		edit.setStyle(Style.BUTTON);
		HBox hbBtn = new HBox(10);
        hbBtn.setPrefHeight(80);
        hbBtn.setAlignment(Pos.TOP_RIGHT);
        hbBtn.getChildren().add(edit);
		edit.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
            	//TODO: Edit recipes
            }
        });
		recipes.add(hbBtn, 1, 0);
		return recipes;
	}
	
	private Button recipeButton(final Recipe r){ //TODO: turn this into a generic list button
		Button b = new Button(r.getName());
		b.setStyle(Style.RECIPE);
		b.setPrefWidth(350);
		b.setOnAction(new EventHandler<ActionEvent>() {
         	 
            @Override
            public void handle(ActionEvent e) {
            	//TODO: Go to recipe page
            	System.out.println(r.getName() + "!!!!");
            }
        });
		return b;
	}
	
	private GridPane displayIngredients(){
		GridPane ingredients = new GridPane();
		ingredients.setHgap(3);
		ingredients.setPrefSize(350, 176);
		ingredients.setStyle(Style.TEXT);
		ScrollPane iScroll = new ScrollPane();//Scrolling Panel
		iScroll.setStyle(Style.TEXT);
		iScroll.setPrefSize(222, 176);
		iScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		iScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		iScroll.setFitToWidth(true);
		//grid panel for buttons
		GridPane rList = new GridPane();
		rList.setPrefSize(300, 200);
		rList.setVgap(2);
		iScroll.setContent(rList);
		Set<String> ingredientSet = _account.getIngredients();//TODO: we should probably have ingredient object with a quantity, etc.
		//TODO: get rid of the fake recipes
		ingredientSet.add("Apple");
		ingredientSet.add("Orange");
		ingredientSet.add("Banana");
		ingredientSet.add("Quinoa");
		ingredientSet.add("Milk");
		ingredientSet.add("Ground Beef");
		ingredientSet.add("Duck Breast");
		ingredientSet.add("Eggs");
		ingredientSet.add("Duck Eggs");
		ingredientSet.add("Coconut Water");
		int i = 0;
		for (String s : ingredientSet){
			rList.add(ingredientButton(s),0,i);
			i++;
		}
		ingredients.add(iScroll, 0, 0);
		Button edit = new Button("+/-");
		edit.setStyle(Style.BUTTON);
		HBox hbBtn = new HBox(10);
        hbBtn.setPrefHeight(80);
        hbBtn.setAlignment(Pos.TOP_RIGHT);
        hbBtn.getChildren().add(edit);
		edit.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
            	//TODO: Edit recipes
            }
        });
		ingredients.add(hbBtn, 1, 0);
		return ingredients;
	}
	
	private Button ingredientButton(final String s){ //TODO: turn this into a generic list button
		Button b = new Button(s);
		b.setStyle(Style.RECIPE);
		b.setPrefWidth(350);
		b.setOnAction(new EventHandler<ActionEvent>() {
         	 
            @Override
            public void handle(ActionEvent e) {
            	//TODO: Go to recipe page
            	System.out.println(s + "!!!!");
            }
        });
		return b;
	}
	
	private GridPane displayShoppingList(){
		GridPane shoppingList = new GridPane();
		shoppingList.setHgap(3);
		shoppingList.setPrefSize(350, 176);
		shoppingList.setStyle(Style.TEXT);
		ScrollPane sScroll = new ScrollPane();//Scrolling Panel
		sScroll.setStyle(Style.TEXT);
		sScroll.setPrefSize(222, 176);
		sScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sScroll.setFitToWidth(true);
		//grid panel for buttons
		GridPane rList = new GridPane();
		rList.setPrefSize(300, 200);
		rList.setVgap(2);
		sScroll.setContent(rList);
		Set<String> shoppingListSet = _account.getShoppingList();//TODO: we should probably have ingredient object with a quantity, etc.
		//TODO: get rid of the fake recipes
		shoppingListSet.add("Lettuce");
		shoppingListSet.add("Spinach");
		shoppingListSet.add("Brocoli");
		shoppingListSet.add("Caviar");
		shoppingListSet.add("Truffle Oil");
		shoppingListSet.add("Yams");
		shoppingListSet.add("Game Hen");
		shoppingListSet.add("Quail Eggs");
		shoppingListSet.add("Goat Cheese");
		shoppingListSet.add("Coconut");
		int i = 0;
		for (String s : shoppingListSet){
			rList.add(shoppingButton(s),0,i);
			i++;
		}
		shoppingList.add(sScroll, 0, 0);
		Button edit = new Button("+/-");
		edit.setStyle(Style.BUTTON);
		HBox hbBtn = new HBox(10);
        hbBtn.setPrefHeight(80);
        hbBtn.setAlignment(Pos.TOP_RIGHT);
        hbBtn.getChildren().add(edit);
		edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	//TODO: Edit recipes
            }
        });
		shoppingList.add(hbBtn, 1, 0);
		return shoppingList;
	}
	
	private Button shoppingButton(final String s){ //TODO: turn this into a generic list button
		Button b = new Button(s);
		b.setStyle(Style.RECIPE);
		b.setPrefWidth(350);
		b.setOnAction(new EventHandler<ActionEvent>() {
         	 
            @Override
            public void handle(ActionEvent e) {
            	//TODO: Go to recipe page
            	System.out.println(s + "!!!!");
            }
        });
		return b;
	}
	
	private GridPane displayKitchens(){
		GridPane kitchenList = new GridPane();
		kitchenList.setHgap(3);
		kitchenList.setPrefSize(350, 400);
		kitchenList.setStyle(Style.TEXT);
		ScrollPane sScroll = new ScrollPane();//Scrolling Panel
		sScroll.setStyle(Style.TEXT);
		sScroll.setPrefSize(222, 400);
		sScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sScroll.setFitToWidth(true);
		//grid panel for buttons
		GridPane rList = new GridPane();
		rList.setPrefSize(300, 200);
		rList.setVgap(2);
		sScroll.setContent(rList);
		Set<String> kitchenListSet = _account.getKitchens();//TODO: we should probably have ingredient object with a quantity, etc.
		//TODO: get rid of the fake recipes
		if (kitchenListSet == null){
			kitchenListSet = new HashSet<>();
		}
		kitchenListSet.add("West House");
		kitchenListSet.add("Home");
		kitchenListSet.add("38 Transit");
		kitchenListSet.add("Playboy Mansion");
		kitchenListSet.add("Natalie Surprise Party");
		kitchenListSet.add("Date Party");
		int i = 0;
		for (String s : kitchenListSet){
			rList.add(kitchenButton(s),0,i);
			i++;
		}
		kitchenList.add(sScroll, 0, 0);
		Button edit = new Button("+/-");
		edit.setStyle(Style.BUTTON);
		HBox hbBtn = new HBox(10);
        hbBtn.setPrefHeight(80);
        hbBtn.setAlignment(Pos.TOP_RIGHT);
        hbBtn.getChildren().add(edit);
		edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	//TODO: Edit recipes
            }
        });
		kitchenList.add(hbBtn, 1, 0);
		return kitchenList;
	}
	
	private Button kitchenButton(final String s){ //TODO: turn this into a generic list button
		Button b = new Button(s);
		b.setStyle(Style.RECIPE);
		b.setPrefWidth(350);
		b.setOnAction(new EventHandler<ActionEvent>() {
         	 
            @Override
            public void handle(ActionEvent e) {
            	//TODO: Go to kitchen
            	System.out.println(s + "!!!!");
            }
        });
		return b;
	}
	
	/**
	 * Tells client to send account updates to server.
	 */
	public void updateAccount(){
		_client.storeAccount(_account.getUserId(), _account); //TODO: this needs to be limited, and we should have an unchangeable userid that they don't ever see
	}
	
	public void updateFridge(Set<String> ingredients){
		_ingredients.setText("");
		for (String ingredient : ingredients){
			_ingredients.append("--" + ingredient + "\n");
		}
	}
	
	public void updateRecipes(Set<Recipe> recipes){
		_recipes.setText("");
		for (Recipe recipe : recipes){
			_recipes.append("--" + recipe.getID()+ "\n");
		}
	}
	
	public void updateShoppingList(Set<String> list){
		_shoppingList.setText("");
		for (String item : list){
			_shoppingList.append("--" + item+ "\n");
		}
	}

}
