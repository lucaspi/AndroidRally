package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;


public class Laser {
	
	private int positionX;
	private int positionY;
	private int direction;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param direction use GamedBoard constants, i.e. GameBoard.NORTH
	 */
	public Laser(int x, int y, int direction){
		positionX = x;
		positionY = y;
		this.direction = direction;
	}
	
	public int getX(){
		return positionX;
	}
	
	public int getY(){
		return positionY;
	}
	
	public int getDirection(){
		return direction;
	}
}
