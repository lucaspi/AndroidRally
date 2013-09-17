package se.chalmers.dryleafsoftware.androidrally.model.gameBoard;

public class GameBoard {
	Tile[][] tiles = null;
	
	public GameBoard(int width, int height, int gameBoard) {
		tiles = new Tile[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++){
				tiles[i][j] = new Tile(0, null);
			}
		}
		
	}
	
	public Tile getTile(int x, int y){
		return tiles[x][y];
	}
	
	public int[][] getStartingPositions(){
		return null;// TODO
	}
}
