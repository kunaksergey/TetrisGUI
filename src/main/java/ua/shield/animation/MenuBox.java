package ua.shield.animation;

import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MenuBox extends Pane{
	static SubMenu subMenu;
	
	public MenuBox(SubMenu subMenu){
		MenuBox.subMenu=subMenu;
		setVisible(false);
		Rectangle rect=new Rectangle(600,800,Color.LIGHTGREEN);
		rect.setOpacity(0.4);
		getChildren().addAll(rect,subMenu);
	}
	
	public void setMenu(SubMenu subMenu){
		getChildren().remove(MenuBox.subMenu);
		MenuBox.subMenu=subMenu;
		getChildren().add(subMenu);
	}
	
}
