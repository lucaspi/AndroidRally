package se.chalmers.dryleafsoftware.androidrally.libgdx.view;

import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class MessageStage extends Stage { 
	
	public MessageStage() {
		
	}
	
	public void dispGameOver(List<RobotView> robots) {
		System.out.println("Displaying game over");
	}
	
	public void dispGameWon(List<RobotView> robots) {
		System.out.println("Displaying game won");
	}
}
