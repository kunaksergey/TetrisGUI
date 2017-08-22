package ua.shield.interfaces;
/**
 * 
 * @author sa
 *Абстрактный интерфейс:
 *
*/
abstract public interface ITurnable {
	
	 /**
	  * Определение новой координаты X	 
	  * @param X0 -точка вращения по оси X
	  * @param Y0 -точка вращения по оси Y
	  * @param X  -текущая координата X
	  * @param Y  -текущая координата Y
	  * @param angle -угол поворота
	  * @return -новая координата X
	  */
	 public default double getNewX(double X0,double Y0,double X,double Y,int angle){
		 double radian=Math.toRadians(angle);  //Радиана угла
		 return (X-X0)*(int)Math.cos(radian)-(Y-Y0)*(int)Math.sin(radian)+X0;
	 }
	 
	 /**
	  * Определение новой координаты X	 
	  * @param X0 -точка вращения по оси X
	  * @param Y0 -точка вращения по оси Y
	  * @param X  -текущая координата X
	  * @param Y  -текущая координата Y
	  * @param angle -угол поворота
	  * @return -новая координата Y
	  */
	 public default double getNewY(double X0,double Y0,double X,double Y,int angle){
		 double radian=Math.toRadians(angle);  //Радиана угла
		 return (X-X0)*(int)Math.sin(radian)+(Y-Y0)*(int)Math.cos(radian)+Y0;
	 }
	 
	 /*** будет переопределен в интерефейсах наследниках:
	  * -1: для вращения 90 градусов:-90 градусов  
	  *  0: без вращения
	  *  1: для вращения по часовой стрелке
	  */
	 abstract int getDirectionTurn();
}
