package ua.shield.graph;

import java.net.PasswordAuthentication;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ua.shield.Main;
import ua.shield.interfaces.ITurn0;

/**
 * Фигура квадрат
 */
public class ShapeSquare extends Shape implements ITurn0{

	public ShapeSquare(double X0,double Y0, Color color){ //X0:Y0 -координаты вращения
		
		super(X0,Y0,color);
		this.rectList.add(new Rectangle(X0,Y0+Main.RECTSIZE,Main.RECTSIZE,Main.RECTSIZE));
		this.rectList.add(new Rectangle(X0+Main.RECTSIZE,Y0+Main.RECTSIZE,Main.RECTSIZE,Main.RECTSIZE));
		this.rectList.add(new Rectangle(X0+Main.RECTSIZE,Y0,Main.RECTSIZE,Main.RECTSIZE));
		initcolor();
	}
	

    public String toString(){
		return "Это квадрат:\n"+super.toString();
	} 
}	


