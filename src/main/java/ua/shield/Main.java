package ua.shield;

import static java.lang.System.out;

import ua.shield.animation.*;
import ua.shield.graph.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Main extends Application {
	public static final int RECTSIZE=30; //Размер Ш-В елемента-кирпичика 
	public static final int LIMITLEFT=200; //Левая граница игрового поля
	public static final int LIMITRIGHT=500; //Правая граница игрового поля
	public static final int LIMITTOP=100; //Верняя граница игрового поля
	public static final int LIMITDOWN=700; //Нижняя граница игрового поля
	public static Integer score=0; //Количество заполенных строк
	public static Integer level=1; //Уровень игры
	public static Label labelLevel=new Label(); //Метка уровня
	public static Label labelScore=new Label(); //Метка набранных линий
	public static Shape currentShape=null;  //Текущий движущийся объект
	public static List<Shape> rectList=new LinkedList<>(); //Список 'Объекты на сцене'
	public  Shape nextShape=null;
	
	private static boolean isPause=false; //Пауза игры
	private static boolean isGameOver=false; //Конец игры
	private static Timeline timeline=new Timeline(); //Временная линейка
	public static Pane root=root=new GamePane();//Игровое поле
	private static MenuBox menuBox;   //Меню
	private static Pane MainRoot=new Pane();     //Основная панель
	
	/**
	 * Запуск приложения
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			//Настройка родительского окна
			primaryStage.setWidth(600); //Ширина основного окна
			primaryStage.setHeight(800); //Высота основного окна
			primaryStage.setTitle("Тетрис"); //Название приложения
			primaryStage.setResizable(false); //Окно не изменяет размер	
			
			Image img=new Image(getClass().getResourceAsStream("Dniepr.jpg"));//Картинка фона
			ImageView image=new ImageView(img);
			image.setFitWidth(600);
			image.setFitHeight(800);
			
			
			//Метка счета
			labelScore.setTextFill(Color.RED);
			labelScore.setTranslateX(20);
			labelScore.setTranslateY(20);
			labelScore.setFont(new Font("Arial", 16));
			
			//Метка уровня
			labelLevel.setTextFill(Color.RED);
			labelLevel.setTranslateX(20);
			labelLevel.setTranslateY(60);
			labelLevel.setFont(new Font("Arial", 25));
			
			Rectangle backShape=new Rectangle(Main.LIMITLEFT,Main.LIMITTOP,  //Задний фон основного поля игры
					Main.LIMITRIGHT-Main.LIMITLEFT,
					Main.LIMITDOWN-Main.LIMITTOP);
			backShape.setFill(Color.LIGHTGRAY);
			
			MainRoot.getChildren().addAll(image,
										  labelScore,
										  labelLevel,
										  backShape
									      ); 
			
			MenuItem newGame=new MenuItem("Новая игра");
			MenuItem options=new MenuItem("Настройки");
			MenuItem exit=new MenuItem("Выход");
			
			SubMenu mainMenu=new SubMenu(newGame,options,exit);
			menuBox=new MenuBox(mainMenu);
			MainRoot.getChildren().addAll(root,menuBox);
			
			Scene scene=new Scene(MainRoot);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		    //Инициализация новой игры
	        initNewGame();
	        
	        //Выход из игры
	        exit.setOnMouseClicked(event->{
				System.exit(0);
			});
	        
	        //Инициализация новой игры
	        newGame.setOnMouseClicked(event->{
	           	initNewGame();
	        });
	        
	        //Обработчик событий нажития
	    	scene.setOnKeyPressed(event->{
				if(event.getCode()==KeyCode.ESCAPE){
					
					Main.isPause=!Main.isPause;	
					if (isPause){
						root.setVisible(false);//делаем поле не видимым
						timeline.pause();      //время на паузу
					}	
					else{
						root.setVisible(true);
						timeline.play();
					}
				   
					FadeTransition ft=new FadeTransition(Duration.seconds(0.5),menuBox);
					if(!menuBox.isVisible()){
						ft.setFromValue(0);
						ft.setToValue(1);
						ft.play();
						menuBox.setVisible(true);
					}else{
						ft.setFromValue(1);
						ft.setToValue(0);
						ft.setOnFinished(ev->{
						menuBox.setVisible(false);
						});
						ft.play();
					}
				}
				
			  try{
					if(event.getCode()==KeyCode.LEFT&&!isPause&&!isGameOver){
						if(currentShape.isMoveLeft())
						   currentShape.moveLeft();
					}	
					if(event.getCode()==KeyCode.DOWN&&!isPause&&!isGameOver){
						if(currentShape.isMoveDown())
						   currentShape.moveDown();
					}
					if(event.getCode()==KeyCode.RIGHT&&!isPause&&!isGameOver){
						if(currentShape.isMoveRight())
						   currentShape.moveRight();
					}
					if(event.getCode()==KeyCode.UP&&!isPause&&!isGameOver){
						if(currentShape.isMoveTurn())
						   currentShape.moveTurn();
					}	
			
				}catch(NullPointerException e){
					
				}	
		   });	
	        
	   	} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Инициализация новой игры
	 */
    private void initNewGame(){
    	score=0;
    	level=1;
    	labelScore.setText("Score:"+score);
    	labelLevel.setText("Level:"+level);
    	Main.isPause=false;
    	Main.isGameOver=false;
    	root.getChildren().clear();
    	rectList.clear();
    	root.setVisible(true);
    	menuBox.setVisible(false);
    	currentShape=FactoryShape.getShape();
    	nextShape=FactoryShape.getShape(70,200);
    	root.getChildren().addAll(currentShape,nextShape);
    	//Настройка таймера
    	timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        
       	setTime(); //Устанавливаем таймер игры
    } 
    
    /**
     * Утановка таймера
     */
	private void setTime(){
	  timeline.stop();
	  timeline.getKeyFrames().clear();
	  
	  EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            update(); //Вызов метода каждый квант времени 
            }
      };
      
      Duration duration = Duration.millis(200-17*level);
      KeyFrame keyFrame = new KeyFrame(duration, onFinished );
      timeline.getKeyFrames().add(keyFrame);
      timeline.play();  
    
	}
	
	/**
	 * Обновление кадра
	 */
	public void update(){
		
		if (currentShape==null){
			/*currentShape=FactoryShape.getShape(); //Генерируем новый падающий объект
			root.getChildren().addAll(currentShape); //и добавляем его на сцену
    	 */
			currentShape=nextShape;
			currentShape.moveStart();
			nextShape=FactoryShape.getShape(80,200);
			root.getChildren().addAll(nextShape);
    		if (!currentShape.isMoveDown()){
    			System.out.println("Gave over");
    			gameOver();
    			timeline.stop();
    		}
		}else
		{
		if(currentShape.isMoveDown()){ //Если объект может двигаться, то передвигаем
			   currentShape.moveDown();
			}
	    	else{						  //иначе добавляем в cтатический список 'Объекты на сцене' 	
	    		rectList.add(currentShape); ///Помещаем объект в список
	    		//addShapeToLines(); //Добавляем в счетчик
	    		addShapeToLines();
	    		currentShape=null;
	    	}
		}
	}
    
	/**
	 * Конец игры
	 * 
	 */
	private void gameOver(){
		isGameOver=true;
		root.getChildren().clear();
		currentShape=null;
		rectList.clear();
	}
	
	private void addShapeToLines(){
	
		while(checkLines()){
		}
	}
	
	private boolean  checkLines(){
	  HashMap<Integer, Integer> lines=new HashMap<>(); 
	
		for(Shape shape:rectList){
			for (Rectangle rect:shape.getCollection()){
				int key=(int)rect.getY();
				if (lines.get(key)==null)
					lines.put(key,1);
				else lines.put(key, lines.get(key)+1);
				if(lines.get(key)==10){
					//animation(key);
					removeLine(key);
					score++;
					labelScore.setText("Score:"+score);
					if(score%10==0){
						level++;
						setTime();
					   labelLevel.setText("Level:"+level);
					}
					moveRectList(key);
					cleanRectList();
					return true;
				}
			}
		}
		return false;
	
	}	

	/** 
	 * Удаление строки сцены с текущим ключем
	 * @param key-ключ строки
	 */
	private static void removeLine(int key){
		 
		for(Shape shape:rectList){
				 List<Rectangle> delList=new LinkedList<Rectangle>();
				 shape.getCollection().forEach(rect->{
					 if(rect.getY()==key)
						 delList.add(rect);
				 });
				 
				//Удаляем линию из набора rectList 
		        shape.getCollection().removeAll(delList);
				
		      //Удаляем линию из панели root
				root.getChildren().forEach(sp->{
				    	Shape sh=(Shape)sp;
						sh.getChildren().removeAll(delList);
				});
				
			
			
		}
	}
	
	/**
	 * 
	 * @param Y - координата Y,ниже которой двиваются фигуры
	 */
	private static void moveRectList(int Y){
		rectList.forEach(shape->{
			shape.moveDown(Y);
		});
	}
	
	//Уборщик мусора rectList
	private static void cleanRectList(){
	  List<Shape> delList=new LinkedList<Shape>();
	  
	  rectList.forEach(shape->{
			if(shape.getCollection().size()==0){
				delList.add(shape);
			}
	  });
	  rectList.removeAll(delList);
	  root.getChildren().removeAll(delList);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void animation(double key) {
		timeline.pause();
		double X0=Main.LIMITLEFT+(int)(Main.LIMITRIGHT-Main.LIMITLEFT)/2;
		Shape line=new ShapeTestLine(X0, key, Color.BLACK);
		root.getChildren().add(line);
		timeline.pause();
	}
	

}
