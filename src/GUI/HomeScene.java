package GUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import server.AutocorrectEngines;
import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.KitchenName;
import UserInfo.Nameable;
import UserInfo.Recipe;
import client.Client;


public class HomeScene implements GUIScene {


	private Account _account;
	private GUIFrame _frame;
	private AutocorrectEngines _autocorrect;
	private Client _client;

	public HomeScene(Account account, GUIFrame frame, AutocorrectEngines info, Client client){
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
        grid.add(scenetitle, 0, 0, 3, 1);
        
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
        dislike.setStyle(Style.INFO_HEADER);
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
        recipeGrid.add(displayList(350, 176, false, false, _account.getRecipes(), new RecipeHandler<ActionEvent>(), recipeGrid), 0, 0);
        
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
        }, fridgeGrid), 0, 0);
        
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
        }, shoppingGrid),0,0);
        
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


	private Text defaultFill(String message){
		Text t = new Text(message);
		t.setFont(Font.font ("Verdana", FontPosture.ITALIC, 10));
		t.setFill(Color.GRAY);
		return t;
	}

	private Pane editPersonalInfo(final GridPane grid){
        GridPane info = new GridPane();
        info.setVgap(8);
        info.setStyle(Style.TEXT);
        info.setPrefSize(130, 160);
        Text name = new Text("Name: ");
        final TextField userName = new TextField(_account.getName());
        Text area = new Text("Area: ");
        final TextField userArea = new TextField(_account.getAddress());
        info.add(name,0,0);
        info.add(userName, 1, 0);
        info.add(area,0,1);
        info.add(userArea, 1, 1);
        Text id = new Text("Email: ");
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
            	_client.storeAccount(_account);
            	grid.add(displayUserInfo(grid), 0, 1, 1, 3);
            }
        });
        return info;
	}

	private VBox IngredientTypeBar(final GridPane grid){
        GridPane info = new GridPane();
        //GridPane buttonPane = new GridPane();
        VBox vbox = new VBox();
        info.setVgap(8);
        info.setStyle(Style.TEXT);
        info.setPrefSize(600, 600);
        Text name = new Text("Ingredient: ");
        final TextField ingredients = new TextField();
        ingredients.setMinWidth(50);
        ingredients.setPrefWidth(50);
        ingredients.setMaxWidth(400);
        final ComboBox<String> box = new ComboBox<String>();
        box.setEditable(true);
        List<String> list = new ArrayList<String>();
        box.getItems().addAll(list);
        
//        Button[] buttons = new Button[4];
//        for(int i = 0; i < buttons.length; i++){
//        	buttons[i] = new Button("hi");
//        }
        
        //ingredients.textProperty().addListener(new ChangeListener<String>() {\
      //  box.valueProperty().addListener(new ChangeListener<String>() {
        box.getEditor().textProperty().addListener(new ChangeListener<String>() {
        //box.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	
            	String text = box.getEditor().getText();
            	List<String> suggs = null;
            	if(text.trim().length()!=0)
            		suggs = _autocorrect.getIngredientSuggestions(text);
            	if(suggs!=null){
            		System.out.println("HEREEE NOT NULL");
            		box.getItems().clear();
            		box.getItems().addAll(suggs);
            	}
            		
            }
        });
        info.add(name,0,0);
        info.add(ingredients, 1, 0);
//        for(int i = 0; i < buttons.length; i++){
//        	buttonPane.add(buttons[i], 0, i);
//        	
//        }
        vbox.getChildren().add(info);
        vbox.getChildren().add(box);
//        vbox.getChildren().add(buttonPane);
        //Pane[] ret = {info, buttonPane};
        //return ret;
        return vbox;
        /*edit Info Button
        Button save = new Button("Add");
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
            	_account.addIngredient(new Ingredient(name));
            	updateAccount(); 
            	//grid.add(displayUserInfo(grid), 0, 1, 1, 3);
            	System.out.println("REFRESH???");
            }
        }); */
       
	}
	
	
	public GridPane displayPreferences(){
		//main grid
		GridPane info = new GridPane();
        info.setVgap(8);
        info.setStyle(Style.TEXT);
        info.setPrefSize(130, 80);
        
        //scroll bar
        ScrollPane sScroll = new ScrollPane();//Scrolling Panel
		sScroll.setStyle(Style.TEXT);
		sScroll.setPrefSize(222, 400);
		sScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sScroll.setFitToWidth(true);
        
		//preferences grid
		GridPane rList = new GridPane();
		rList.setPrefSize(300, 200);
		rList.setVgap(2);
		sScroll.setContent(rList);


		Set<String> preferences = _account.getDietaryRestrictions();

		if(preferences.size()==0){
			rList.add(defaultFill("No dietary restrictions."), 0, 0);
		}
		else{
			int i = 0;
			for(String s: preferences){
				rList.add(new Text(s),0,i);
				i++;
			}
		}

		info.add(sScroll, 0, 0);

        Button edit = new Button("+/-");
        edit.setStyle(Style.BUTTON);
        
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	System.out.println("I WANT TO ADD A PREFERENCE!!");
            }
        });
        
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

		if(kitchenListSet.size()==0){
			rList.add(defaultFill("You don't currently belong to any kitchens. To add a kitchen click the +/- in the left hand corner :)"), 0, 0);

		}

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
        hbBtn.setAlignment(Pos.TOP_LEFT);
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
		b.setWrapText(true);
		return b;
	}

	private GridPane displayList(final int width, final int height, final boolean isIngredients, final boolean isFridge, final Set<? extends Nameable> items,
			final EventHandler<ActionEvent> itemDest, final GridPane parentGrid){
		final GridPane listPane = new GridPane();
		listPane.setHgap(3);
		listPane.setVgap(3);
		listPane.setPrefSize(width, height);
		listPane.setStyle(Style.TEXT);
		final ScrollPane scroll = new ScrollPane();//Scrolling Panel
		scroll.setStyle(Style.TEXT);
		scroll.setPrefSize(width, height);
		scroll.setMinWidth(width);
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setFitToWidth(true);
		//grid panel for buttons
		final GridPane itemList = buttonList(width, height, items, itemDest);
		scroll.setContent(itemList);
		Button edit = new Button("+/-");
		edit.setMinWidth(20);
		edit.setStyle(Style.BUTTON);
		final HBox editBox = new HBox(10);
        editBox.setPrefHeight(80);
        editBox.setAlignment(Pos.TOP_RIGHT);
        editBox.getChildren().add(edit);
		edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	editBox.getChildren().remove(0);
            	int j = 0;
            	for (Nameable item : items){
            		System.out.println("calling");
            		itemList.add(removeButton(width, height, item, isIngredients, isFridge, itemDest), 2,j);
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
            		HBox saveBox = new HBox();
        			saveBox.setAlignment(Pos.BOTTOM_LEFT);
        			saveBox.getChildren().add(b);
            		if (isIngredients){	
            			listPane.add(saveBox, 0, 2);
            		} else {
            			listPane.add(saveBox, 1, 2);
            		}
            	}
            	Button save = new Button("Save");
        		save.setMinWidth(40);
        		save.setStyle(Style.BUTTON);
        		HBox hbBtn = new HBox(10);
                hbBtn.setPrefHeight(80);
                hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
                
                //Working with pane arr returned and addding all the hbtn.
               // Pane[] ingredientPanes = IngredientTypeBar(itemList);
                VBox ingredientsBox = IngredientTypeBar(itemList);
                hbBtn.getChildren().add(ingredientsBox);
                hbBtn.getChildren().add(save);
               // hbBtn.getChildren().add(ingredientPanes[1]);
               
            	
                listPane.add(hbBtn, 2, 2, 2, 1);
                //listPane.add(ingredientPanes[0], 1,7);
               // listPane.add(ingredientPanes[1], arg1, arg2);
        		save.setOnAction(new EventHandler<ActionEvent>() {
        			@Override
                    public void handle(ActionEvent e) {
        				parentGrid.add(displayList(width, height, isIngredients, isFridge, items, itemDest, parentGrid), 0, 0);
        			}
        		});
        		
        	}

			private Button removeButton(final int width, final int height, final Nameable item,
					final boolean isIngredient, final boolean isFridge, final EventHandler<ActionEvent> itemDest) {
				Button b = new Button("X");
				System.out.println(b.getText());
				b.setStyle(Style.RECIPE);
				//b.setMinWidth(40);
				b.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent e){
						//TODO: Have these also change the edit button to a save button, add a search bar on the bottom.
						if (isIngredient){
							if(isFridge){
								items.remove(item);
								Ingredient ing = new Ingredient(item.getName());
								_account.setIngredients((Set<Ingredient>) items);
								_client.storeAccount(_account, ing);
							} else {
								items.remove(item);
								_account.setShoppingList((Set<Ingredient>) items);
								_client.storeAccount(_account);
							}
						} else {
							items.remove(item);
							_account.setRecipes((HashSet<Recipe>) items);
							_client.storeAccount(_account);
						}
						final GridPane itemList = new GridPane();
						itemList.setPrefSize(width,height);
						itemList.setVgap(2);
						int i = 0;
						for (Nameable item : items){
							itemList.add(itemButton(item, itemDest),1,i);
							if (isIngredients){
								Ingredient thing = (Ingredient) item;
								itemList.add(quantButton(thing.getQuantity() + thing.getUnit()), 0, i);
							}
							itemList.add(removeButton(width, height, item, isIngredients, isFridge, itemDest), 2,i);
							i++;
						}
						scroll.setContent(itemList);
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
			listPane.add(editBox, 0, 0);
		} else {
			listPane.add(scroll, 1, 0);
			listPane.add(editBox, 0, 0);
		}
		return listPane;
	}


	private GridPane buttonList(int width, int height, Set<? extends Nameable> items, EventHandler<ActionEvent> itemDest){
		//grid panel for buttons
		final GridPane itemListGrid = new GridPane();
		itemListGrid.setPrefSize(width,height);
		itemListGrid.setVgap(2);
		int i = 0;
		for (Nameable item : items){
			itemListGrid.add(itemButton(item, itemDest),1,i);
			i++;
		}
		return itemListGrid;
	}

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
		//_client.storeAccount(_account.getID(), _account); //TODO: this needs to be limited, and we should have an unchangeable userid that they don't ever see
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
