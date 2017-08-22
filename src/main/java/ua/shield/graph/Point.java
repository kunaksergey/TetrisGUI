package ua.shield.graph;

public class Point {
 private int x; //Координата абсцисы
 private int y;//Координата ординаты
 boolean axle; //Ось вращения:true-да;false:нет
  Point(int x,int y){
	 this(x,y,false);
  }
  Point(int x,int y,boolean axle){
	  this.x=x;
	  this.y=y;
	  this.axle=axle;
  }
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	  
}
