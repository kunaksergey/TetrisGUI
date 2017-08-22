package ua.shield.graph;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import ua.shield.Main;
import ua.shield.interfaces.ITurnable;

abstract public class Shape extends Pane implements ITurnable{
	 
	 protected List<Rectangle> rectList=new LinkedList<Rectangle>(); //Набор елементов-кирпичиков
	 protected Color color;	 //текущий цвет фигуры	 
	 protected int angle=90; //Угол вращения по умолчанию
	
	 
	 /**
	  * Конструктор 
	  * @param X0 -Х координата точки вращения
	  * @param Y0 -Y координата точки вращения
	  * @param color -цвет фигуры
	  */
	  Shape(double X0,double Y0,Color color){
		//Добавляем первый елемент в набор
		//он же точка вращения фигуры
		 this.rectList.add(new Rectangle(X0,Y0,Main.RECTSIZE,Main.RECTSIZE));
		 this.color=color;
	  }
	  
	  protected void initcolor(){
		  this.getChildren().addAll(rectList);
		  
		  Stop[] stops = new Stop[] { 
				  new Stop(0, Color.BLACK), 
				  new Stop(1, this.color)};
		  LinearGradient linearGradient = 
			new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
			  
		  rectList.forEach(rect->{
			  rect.setFill(linearGradient);
			  rect.setStroke(Color.BLACK);
			  rect.setArcWidth(5);
			  rect.setArcHeight(5);
		  });
		//  rectList.get(0).setFill(Color.RED);
	  }
	
	/**
	 * Возвращаем коллекцию фигуры 
	 * @return коллекция элементов
	 */
	 public  List<Rectangle> getCollection(){
			 return rectList;
	 }  
	/**
	 * Перемещение каждого элемента фигуры Shape
	 * @param dX -на x
	 * @param dY -на y
	 */
	  protected void move(double dX,double dY){
		  rectList.forEach(rect->{
			  rect.setX(rect.getX()+dX);
			  rect.setY(rect.getY()+dY);
		  });
	  }
	  
	  public void moveStart(){
		
		  double X0=Main.LIMITLEFT+(int)(Main.LIMITRIGHT-Main.LIMITLEFT)/2 -rectList.get(0).getX();
		  double Y0=Main.LIMITTOP-rectList.get(0).getY();
		  move(X0,Y0);
	  }
	 /**
	  * Перемещение каждого элемента фигуры Shape с границей Y
      * @param dX -на x
	  * @param dY -на y
	  */
	  public void move(int dX,int dY,int bY){
		  rectList.forEach(rect->{
			  rect.setX(rect.getX()+dX);
			  rect.setY(rect.getY()+dY);
		  });
	  }
	  
	  /**
	   * Перемещение каждого элемента фигуры ВЛЕВО
	   */
	  public void moveLeft(){
	    move(-Main.RECTSIZE,0);  // '-Main.RECTSIZE' -смещение влево
	  } 
	  
	  /**
	   * Перемещение каждого элемента фигуры ВПРАВО
	   */
	  public void moveRight(){
	    move(Main.RECTSIZE,0);   // '-Main.RECTSIZE' -смещение вправо 
	  }
	  
	  /**
	   * Перемещение каждого элемента фигуры Вверх
	   */
	  public void moveUp(){
	    move(0,-Main.RECTSIZE);   // '-Main.RECTSIZE' -смещение вверх 
	  }
	
	  /**
	   * Перемещение каждого элемента фигуры вниз
	   */
	  public void moveDown(){
		  this.move(0,Main.RECTSIZE);
	  }
	  
	  /**
	   * Перемещение каждого элемента фигуры Shape вниз ниже Y
	   * @param Y -координаты строки, меньше которой можно двигать фигуры
	   */
	  public void moveDown(int Y){
		  rectList.forEach(rect->{
			  if (rect.getY()<Y)
		      rect.setY(rect.getY()+Main.RECTSIZE);
		  });
	  }
	  
	  /**
	   *Может ли текущий объект двигаться вниз. 
	   *@return true-может:false-нет
	   */
	  public boolean isMoveDown(){
		  for(Rectangle rect:rectList){
			  if((rect.getY()+Main.RECTSIZE)>=Main.LIMITDOWN)
				  return false;
			  if(isCrossing(rect,1,1,-2,-1))
				  return false;
	 	  }
		return true;
	  }
	  
	  /**
	   *Может ли текущий объект двигаться вверх. 
	   *@return true-может:false-нет
	   */
	  public boolean isMoveUp(){
		  for(Rectangle rect:rectList){
			  if((rect.getY()-Main.RECTSIZE)<=Main.LIMITTOP)
				  return false;
			  if(isCrossing(rect,1,1,-2,-1))
				  return false;
	 	  }
		return true;
	  }
	  	  
	  /**
	   *Может ли текущий объект двигаться влево.  
	   * @return true-может:false-нет
	   */
	  public boolean isMoveLeft(){
		  for(Rectangle rect:rectList){
			  if((rect.getX()-Main.RECTSIZE)<Main.LIMITLEFT)
				  return false;
			  if (isCrossing(rect,0,1,-1,-2))
				  return false;
		  }
		return true;
	  }
	  
	  /**
	   *Может ли текущий объект двигаться вправо.  
	   * @return true-может:false-нет
	   */
	  public boolean isMoveRight(){
		  for(Rectangle rect:rectList){
			  if((rect.getX()+Main.RECTSIZE)>=Main.LIMITRIGHT)
				  return false;
			  if(isCrossing(rect,1,1,-1,-2))
				  return false;
		  }
		  return true;
	  }
	  
	  /**
	   * Может ли объет повернутся
	   * @return
	   */
	  public boolean isMoveTurn(){
		  //получение следующего угла
		  int angel=this.angle*getDirectionTurn();
		  
		  //Координаты элемента
		  double X,Y,newX,newY; 
		  
		  //Координаты площади пересечения
		  double Xs,Ys;
		  		  
		  //Высота и ширина площади пересечения
		  double H,W;
		  
		  //центр вращения
		  double X0=rectList.get(0).getX(); 
		  double Y0=rectList.get(0).getY();
		  
		  //Список пересекающихся поверхностей 
		  List<Rectangle> rectCrossList=new LinkedList<Rectangle>(); 
		  
		    for(int i=1;i<rectList.size();i++){
		    	
		    	X=Xs=rectList.get(i).getX();
		  		Y=Ys=rectList.get(i).getY();
		  		
		  		newX=getNewX(X0,Y0,X,Y,angel);
		  		newY=getNewY(X0,Y0,X,Y,angel);
		  		
		  		W=X;
		  		H=Y;
		  		
		  		if (newX<Xs) Xs=newX;
		  		if (newY<Ys) Ys=newY;
		  		if (newX>W)  W=newX;
		  		if (newY>H)  H=newY;
		  		
		  		 Xs+=1;
		  		 Ys+=1;
		  		 
		  		 H=H-Ys+Main.RECTSIZE-1;
		  		 W=W-Xs+Main.RECTSIZE-1;
		   
		  		 //Проверки:
		  		if (newY>=Main.LIMITDOWN&&this.isMoveUp()){
		  	    	 this.moveUp();
		  		}
		  		 
		  		if (newX<Main.LIMITLEFT&&this.isMoveRight()){
		  	    	 this.moveRight();
		  		}		 
		  		if (newX>=Main.LIMITRIGHT&&this.isMoveLeft()){
		  		    this.moveLeft();
		  		} 		 
		  		  
		  		if (isCrossing(new Rectangle(Xs, Ys, W, H),0,0,0,0))
		  		return false;
		   }
	   	  return true;
	  }
	  
	 /**
       * Метод определяет пересекаются ли фигуры сцены с текущей фигурой 
       * @param dX -смещение начальной точки объекта по оси X
       * @param dY -смещение начальной точки объекта по оси Y
       * @param dW -уменьшение ширины фигуры
       * @param dH -уменьшение высоты фигуры
       * @return Если пересекаются, то false
       */
	  protected boolean isCrossing(Rectangle rect,double dX,double dY,int dW,int dH){
		     	
			 for (Shape shape: Main.rectList) {
				for(Rectangle rectShape:shape.rectList){
					if(rectShape.getBoundsInParent().intersects(rect.getX()+dX,
														   rect.getY()+dY,
														   rect.getWidth()+dW,
														   rect.getHeight()+dH
														   )
				     ) return true;
				}	
			  }				 
		return false;
	  }
	  
	 /**
      * Поворот фигуры
      */
	 public void moveTurn(){
		      angle*=getDirectionTurn(); //меняем направление поворота
			  double X0=rectList.get(0).getX();//Центр вращения,ось X
			  double Y0=rectList.get(0).getY();//Центр вращения,ось Y	
		      double X;//X-координата текущей точки объекта obj
			  double Y;//Y-координата текущей точки объекта obj
			  double newX;//Новая Х-координата точки объекта obj
			  double newY;//Новая Y-координата точки объекта obj
			  
			  //Проходим по всем точкам, кроме точки вращения: X0:Y0
			  for (int i=1;i<rectList.size();i++){
				  
				  //Получаем координаты текущей точки
				  X=rectList.get(i).getX(); 
				  Y=rectList.get(i).getY();
				   
				  //Формула преобразования координат
				  newX=getNewX(X0,Y0,X,Y,angle);
				  newY=getNewY(X0,Y0,X,Y,angle);
										  
				  //Устанавливаем новые координаты объекта после вращения на угол angle
				  rectList.get(i).setX(newX);
				  rectList.get(i).setY(newY);
			  }
	  }
	
	 /**
	  * Вывод информации о фигуре
	  * @return
	  */
	 @Override
	 public String toString(){
	  StringBuffer str=new StringBuffer();
		 for(Rectangle rect:rectList){
			 str.append(rect.getX()+":"+rect.getY()+"\n");
		 }
	   return str.toString();
	 }
}
