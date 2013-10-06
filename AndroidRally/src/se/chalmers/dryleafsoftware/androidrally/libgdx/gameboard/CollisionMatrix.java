package se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard;

import se.chalmers.dryleafsoftware.androidrally.sharred.MapBuilder;

public class CollisionMatrix {

	private final boolean[][][] walls;
	private boolean[][] dynamic;
	
	public CollisionMatrix(int width, int height) {
		this.walls = new boolean[width][height][2]; // 2 wall positions [0] = up, [1] = left
		clearDynamic();
	}
	
	public void setWall(int x, int y, int dir) {
		if(validWallPos(x, y, dir)) {
			this.walls[x][y][dir] = true;
		}
	}
	
	public void clearDynamic() {
		this.dynamic = new boolean[walls.length][walls[0].length];
	}
	
	public void setDynamic(int x, int y) {
		if(validBlockedPos(x, y)) {
			this.dynamic[x][y] = true;
		}
	}
	
	public boolean cannotTravel(int x, int y, int dir) {
		if(dir == MapBuilder.DIR_NORTH) {
			return (isWall(x, y, 0) || isBlocked(x, y - 1));
		}else if(dir == MapBuilder.DIR_SOUTH) {
			return (isWall(x, y + 1, 0) || isBlocked(x, y + 1));
		}else if(dir == MapBuilder.DIR_WEST) {
			return (isWall(x, y, 1) || isBlocked(x - 1, y));
		}else if(dir == MapBuilder.DIR_EAST) {
			return (isWall(x + 1, y, 1) || isBlocked(x + 1, y));
		}else{
			return true;
		}
	}
	
	public boolean isWall(int x, int y, int dir) {
		if(validWallPos(x, y, dir)) {
			return walls[x][y][dir];
		}else{
			return false;
		}
	}
	
	private boolean validWallPos(int x, int y, int dir) {
		return(x < walls.length && y < walls[0].length && dir < walls[0][0].length 
				&& x >= 0 && y >= 0 && dir >= 0);
	}
	
	private boolean validBlockedPos(int x, int y) {
		return(x < dynamic.length && y < dynamic[0].length 
				&& x >= 0 && y >= 0);
	}
	
	public boolean isBlocked(int x, int y) {
		if(validBlockedPos(x, y)) {
			return dynamic[x][y];
		}else{
			return false;
		}
	}
}
