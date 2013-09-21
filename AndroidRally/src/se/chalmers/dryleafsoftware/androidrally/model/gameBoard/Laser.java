package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

/**
 * Representing a laser on the gameBoard. The laser will shoot from a certain
 * position with a certain direction.
 * @author
 *
 */
public class Laser {
	
	private int positionX;
	private int positionY;
	private int direction;
	
	/**
	 * Creates a new laser with a set position and direction.
	 * @param x the position on the x-axis
	 * @param y the position on the y-axis
	 * @param direction use GamedBoard constants, i.e. GameBoard.NORTH
	 */
	public Laser(int x, int y, int direction){
		positionX = x;
		positionY = y;
		this.direction = direction;
	}
	
	/**
	 * Returns the position on the x-axis.
	 * @return the position on the x-axis.
	 */
	public int getX(){
		return positionX;
	}
	
	/**
	 * Returns the position on the y-axis.
	 * @return the position on the y-axis.
	 */
	public int getY(){
		return positionY;
	}
	
	/**
	 * Returns the direction of the laser.
	 * @return the direction of the laser.
	 */
	public int getDirection(){
		return direction;
	}
}
