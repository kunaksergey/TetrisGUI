package ua.shield;

//import java.util.ArrayList;
import javafx.scene.paint.Color;
import ua.shield.graph.*;
public class FactoryShape {
	
  public static Shape getShape (){
	   double X0=Main.LIMITLEFT+(int)(Main.LIMITRIGHT-Main.LIMITLEFT)/2;
	   double Y0=Main.LIMITTOP;
	   return getShape (X0,Y0);
	}	
  
  public static Shape getShape (double X0,double Y0){
	  Shape shp=null;
	  EnumShape[] shape={ EnumShape.GLEFTPOLIGON,EnumShape.GRIGHTPOLIGON,
			  			  EnumShape.NLEFTPOLIGON,EnumShape.NRIGHTLIGON,
			              EnumShape.RECTANGLE,EnumShape.SQUARE,
			              EnumShape.TPOLIGON,EnumShape.TESTLINE};
	 int indexShape=(int)(Math.random()*6);
	  
	  
	  switch(shape[indexShape]){
	  case RECTANGLE: shp=new ShapeRectangle(X0, Y0, Color.SKYBLUE);break;
	  case SQUARE: shp=new ShapeSquare(X0, Y0, Color.YELLOW);break;
	  case NLEFTPOLIGON: shp=new ShapeNLeftPoligon(X0, Y0, Color.RED);break;
	  case NRIGHTLIGON: shp=new ShapeNRightPoligon(X0, Y0, Color.GREENYELLOW);break;
	  case GLEFTPOLIGON: shp=new ShapeGLeftPoligon(X0, Y0, Color.CORNFLOWERBLUE);break;
	  case GRIGHTPOLIGON: shp=new ShapeGRightPoligon(X0, Y0, Color.ORANGE);break;
	  case TPOLIGON: shp=new ShapeTPoligon(X0, Y0, Color.BLUEVIOLET);break;
	  case TESTLINE: shp=new ShapeTestLine(X0, Y0, Color.BLUEVIOLET);break;
	  }
	  return shp;																										
  }
}



