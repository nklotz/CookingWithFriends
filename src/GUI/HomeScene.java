package GUI;

import java.util.HashSet;
import java.util.Set;

import client.Client;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import server.APIInfo;
import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.KitchenName;
import UserInfo.Nameable;
import client.Client;


public class HomeScene implements GUIScene {


	private Account _account;
	private GUIFrame _frame;
	private APIInfo _autocorrect;
	private Client _client;
	
	public HomeScene(Account account, GUIFrame frame, APIInfo info, Client client){
		_account = account;
		_frame = frame;
		_autocorrect = info;
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
        
        Text scenetitle = new Text("Cooking with Friends: Home");
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
		Set<KitchenName> kitchenListSet = _account.getKitchens();
		if (kitchenListSet == null){
			kitchenListSet = new HashSet<>();
		}
		System.out.println("kitchens: " + kitchenListSet);
		int i = 0;
		for (KitchenName s : kitchenListSet){
			System.out.println("calling kitchen button");
			rList.add(kitchenButton(s),0,i);
			i++;
		}
		kitchenList.add(sScroll, 0, 0);
		Button edit = new Button("+/-");
		edit.setMinWidth(20);
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
	
	private Button kitchenButton(final KitchenName k){ //TODO: turn this into a generic list button
		System.out.println("getting  called");
		Button b = new Button(k.getName());
		KitchenHandler kHandle = new KitchenHandler();
		kHandle.setKitchen(k.getID());
		b.setStyle(Style.RECIPE);
		b.setPrefWidth(350);
		b.setOnAction(kHandle);
		return b;
	}
	
	private GridPane displayList(int width, int height, final boolean isIngredients, final boolean isFridge, final Set<? extends Nameable> items,
			EventHandler<ActionEvent> itemDest){
		final GridPane listPane = new GridPane();
		listPane.setHgap(3);
		listPane.setPrefSize(width, height);
		listPane.setStyle(Style.TEXT);
		ScrollPane scroll = new ScrollPane();//Scrolling Panel
		scroll.setStyle(Style.TEXT);
		scroll.setPrefSize(width, height);
		scroll.setMinWidth(width);
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setFitToWidth(true);
		//grid panel for buttons
		final GridPane itemList = new GridPane();
		itemList.setPrefSize(width,height);
		itemList.setVgap(2);
		scroll.setContent(itemList);
		int i = 0;
		for (Nameable item : items){
			itemList.add(itemButton(item, itemDest),1,i);
			i++;
		}
		Button edit = new Button("+/-");
		edit.setMinWidth(20);
		edit.setStyle(Style.BUTTON);
		HBox hbBtn = new HBox(10);
        hbBtn.setPrefHeight(80);
        hbBtn.setAlignment(Pos.TOP_RIGHT);
        hbBtn.getChildren().add(edit);
		edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	int j = 0;
            	for (Nameable item : items){
            		System.out.println("calling");
            		itemList.add(removeButton(item, isIngredients, isFridge), 2,j);
            		j++;
            	}
            	if (!isIngredients){
            		Button b = new Button("Search Recipes");
            		b.setStyle(Style.BUTTON);
            		b.setOnAction(new EventHandler<ActionEvent>(){
            			@Override
            			public void handle(ActionEvent e){
            				_frame.loadSearchScene();
            			}
            		});
            		listPane.add(b, 0, 1, 2, 1);
            	}
            }

			private Button removeButton(final Nameable item, final boolean isIngredient, final boolean isFridge) {
				Button b = new Button("X");
				System.out.println(b.getText());
				b.setStyle(Style.RECIPE);
				//b.setMinWidth(40);
				b.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent e){
						//TODO: Have these also change the edit button to a save button, add a search bar on the bottom.
						if (isIngredient){
							Set<Ingredient> list = null;
							if(isFridge){
								list = _account.getIngredients();
								list.remove(item);
								_account.setIngredients(list);
							} else {
								list = _account.getShoppingList();
								list.remove(item);
								_account.setShoppingList(list);
							}
						} else {
							
						}
					}
				});
				return b;
			}
        });
		if (isIngredients){
			Text qty = new Text("   Qty.   |");
			Text Ingredient = new Text("	Ingredient");
			//Display Quantities
			int j = 0;
			for (Nameable item : items){
				Ingredient thing = (Ingredient) item;
				itemList.add(quantButton(thing.getQuantity() + thing.getUnit()), 0, j);
				j++;
			}
			listPane.add(qty, 1, 0);
			listPane.add(Ingredient, 2, 0);
			listPane.add(scroll, 1, 1, 2, 1);
			listPane.add(hbBtn, 0, 0);
		} else {
			listPane.add(scroll, 1, 0);
			listPane.add(hbBtn, 0, 0);
		}
		return listPane;
	}
	/*
	private ScrollPane buttonList(int width, int height, Set<Nameable> items, EventHandler<ActionEvent> itemDest){
		ScrollPane scroll = new ScrollPane();//Scrolling Panel
		scroll.setStyle(Style.TEXT);
		scroll.setPrefSize(width, height);
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setFitToWidth(true);
		//grid panel for buttons
		final GridPane itemList = new GridPane();
		itemList.setPrefSize(width,height);
		itemList.setVgap(2);
		scroll.setContent(itemList);
		int i = 0;
		for (Nameable item : items){
			itemList.add(itemButton(item, itemDest),1,i);
			i++;
		}
		return scroll;
	}*/
	
	private Button quantButton(String called){
		System.out.println(called);
		Button b = new Button(called);
		b.setStyle(Style.RECIPE);
		//b.setMinWidth(45);
		b.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				System.out.println("nothing");
			}
		});
		return b;
	}
	
	private Button itemButton(Nameable item, EventHandler<ActionEvent> destination){ 
		Button b = new Button(item.getName());
		b.setStyle(Style.RECIPE);
		b.setPrefWidth(350);
		b.setOnAction(destination);
		return b;
	}
	
	private EventHandler<ActionEvent> nameHandler(final Button b){
		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				System.out.println("You selected: " + b.getText());
			}
		};
		return handler;
	}
	
	private class KitchenHandler<ActionEvent> implements EventHandler{
		
		private String _kitchenID;
		
		public void setKitchen(String kitchenID){
			_kitchenID = kitchenID;
		}
		
		@Override
		public void handle(Event arg0) {
			System.out.println(" requesting kitchen: " + _kitchenID);
			_client.setCurrentKitchen(_kitchenID);
			requestKitchen(_kitchenID);
			
		}
	}
	
	private class RecipeHandler<ActionEvent> implements EventHandler{
		
		private String _recipeID;
		
		public void setRecipe(String recipeID){
			_recipeID = recipeID;
		}
		
		
		
		@Override
		public void handle(Event arg0) {
			System.out.println(" requesting recipe: " + _recipeID);
			//TODO: pop up recipe
		}
	}
	
	/**
	 * Tells client to send account updates to server.
	 */
	public void updateAccount(){
		System.out.println("account: " + _account);
		System.out.println("client: " + _client);
		_client.storeAccount(_account.getID(), _account); //TODO: this needs to be limited, and we should have an unchangeable userid that they don't ever see
	}
	
	public void requestKitchen(String kitchenID){
		_client.getKitchen(kitchenID);
	}

	//TODO: Fix scrollbar formatting
	// make preference editable
	// get rid of dislikes
	// add adding/deleting ingredients
	// add deleting kitchens.
	// why aren't kitchens displaying?

}
