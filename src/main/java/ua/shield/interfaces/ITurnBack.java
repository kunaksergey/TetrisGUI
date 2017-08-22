package ua.shield.interfaces;
/**
 * 
 * @author sa
 * Интерфейс фигуры вращения туда сюда
 */
public interface ITurnBack extends ITurnable {
	
	@Override
	default int getDirectionTurn(){
		return -1;
	}
}
