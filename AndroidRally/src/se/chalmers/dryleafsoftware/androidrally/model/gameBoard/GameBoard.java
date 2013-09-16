package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

public class GameBoard {
	Tile[][] tiles = null;
	
	public GameBoard(int width, int height) {
		tiles = new Tile[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++){
				tiles[i][j] = new Tile(0, null);
			}
		}
		
	}
}
