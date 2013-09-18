package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class GameController implements GestureListener {
	
	private GdxGame game;
	private OrthographicCamera camera;
	
	public GameController(GdxGame game) {
		this.game = game;
		this.camera = this.game.getCamera();
	}

	@Override
	public boolean tap(float arg0, float arg1, int arg2, int arg3) {
		return false;
	}

	@Override
	public boolean touchDown(float arg0, float arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float arg0, float arg1, float arg2, float arg3) {
		return false;
	}

	@Override
	public boolean fling(float arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float arg0, float arg1) {
		if(arg1-arg0 > 0 && camera.zoom > 0.4f) {
			camera.zoom -= 0.05f;
		} else if(arg0 - arg1 > 0 && camera.zoom < 1.0f){
			camera.zoom += 0.05f;
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 arg0, Vector2 arg1, Vector2 arg2, Vector2 arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
