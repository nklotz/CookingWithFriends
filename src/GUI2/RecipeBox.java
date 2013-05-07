package GUI2;

import UserInfo.Recipe;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class RecipeBox extends VBox {
   
	private Recipe _recipe;
    private Controller2 _controller;
    	
    public RecipeBox(Recipe recipe, Controller2 controller) {
		super(8);
		_recipe = recipe;
		_controller = controller;
		
		this.getStyleClass().add("recipeBox");
		this.setAlignment(Pos.CENTER);
		
		this.setMaxHeight(130);
		this.setPrefHeight(130);
		this.setMinHeight(130);
		
		this.setMaxWidth(150);
		this.setPrefWidth(150);
		this.setMinWidth(150);
		
		if (recipe.hasThumbnail()) {
			Image recipeThumbnail = new Image(recipe.getThumbnailUrl(), 100, 0, true, true, true); 
			if (recipeThumbnail.getHeight() < 60) {
				ImageView imageView = new ImageView(recipeThumbnail);
				imageView.getStyleClass().add("recipeThumbnail");
				this.getChildren().add(imageView);
			}
		}
		
		Label recipeLabel = new Label(recipe.getName());
		recipeLabel.setFont(new Font(11));
		recipeLabel.setMaxWidth(120);
		recipeLabel.setWrapText(true);
		this.getChildren().add(recipeLabel);
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				_controller.createPopup(_recipe);					
			}
		});
	}
}
