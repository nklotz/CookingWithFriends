package GUI2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import UserInfo.Recipe;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class RecipeBox extends VBox{
	
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
		
		final RecipeBox _self = this;
		
		this.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");
                
                /* allow any transfer mode */
                Dragboard db = _self.startDragAndDrop(TransferMode.ANY);
                
                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();

                content.putString(_self.getString(_recipe));
                System.out.println("has string: " + db.hasString());
                db.setContent(content);          
                event.consume();
            }
        });
		
	}
    
    
    
    
	public static String getString(Recipe rb) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(rb);
	        oos.close();
		} catch (IOException e) {
			System.out.println("ERROR: Could not make serializable object." + e.getMessage());
		}
		//Imports all of this so it doesn't conflict with the other Base64 import above.
        return new String(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(baos.toByteArray()));
    }
    
}
