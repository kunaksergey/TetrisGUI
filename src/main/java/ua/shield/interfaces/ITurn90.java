package ua.shield.interfaces;
/**
 * 
 * @author sa
 * Интерфейс фигуры вращения по часовой стрелке
 */
public interface ITurn90 extends ITurnable{
	
	@Override
	default int getDirectionTurn(){
		return 1;
	}
}
