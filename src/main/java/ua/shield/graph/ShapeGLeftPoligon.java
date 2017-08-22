package ua.shield.graph;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ua.shield.Main;
import ua.shield.interfaces.ITurn0;
import ua.shield.interfaces.ITurn90;

/**
 * Фигура Г(левая)
 */
public class ShapeGLeftPoligon extends Shape implements ITurn90{
	
	public 	ShapeGLeftPoligon(double X0,double Y0, Color color){ //X0:Y0 -координаты вращения
		super(X0,Y0,color);
		this.rectList.add(new Rectangle(X0-Main.RECTSIZE,Y0,Main.RECTSIZE,Main.RECTSIZE));
		this.rectList.add(new Rectangle(X0+Main.RECTSIZE,Y0,Main.RECTSIZE,Main.RECTSIZE));
		this.rectList.add(new Rectangle(X0-Main.RECTSIZE,Y0-Main.RECTSIZE,Main.RECTSIZE,Main.RECTSIZE));
		initcolor();
	}
	
	public String toString(){
		return "Это Г-фигура(левая)";
	} 
}