package ua.shield.animation;

import javafx.scene.layout.VBox;

public class SubMenu extends VBox{
	   public SubMenu(MenuItem... items){
		   setSpacing(15);
		   setTranslateX(50);
		   setTranslateY(100);
		   for(MenuItem item:items){
			   getChildren().add(item);
		   }
	   }
}
