package ua.shield.interfaces;
/**
 * 
 * @author sa
 * Интерфейс фигуры без вращения
 */
public interface ITurn0 extends ITurnable{
	
	@Override
	default int getDirectionTurn(){
		return 0;
	}
}
