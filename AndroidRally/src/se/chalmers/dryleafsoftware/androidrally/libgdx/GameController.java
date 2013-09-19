package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameController implements GestureListener {

	private GdxGame game;
	private OrthographicCamera boardCamera, cardCamera;
	private boolean modifyBoard, modifyCards;

	public GameController(GdxGame game) {
		this.game = game;
		this.boardCamera = this.game.getBoardCamera();
		this.cardCamera = this.game.getCardCamera();
	}

	@Override
	public boolean tap(float arg0, float arg1, int arg2, int arg3) {
		return false;
	}

	@Override
	public boolean touchDown(float arg0, float arg1, int arg2, int arg3) {
		if (arg1 < Gdx.graphics.getHeight() / 2) {
			modifyBoard = true;
			modifyCards = false;
		} else {
			modifyBoard = false;
			modifyCards = true;
		}
		return false;
	}

	@Override
	public boolean longPress(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float arg0, float arg1, float arg2, float arg3) {
		if (modifyBoard) {
			if (boardCamera.zoom == 1.0f) {
				boardCamera.translate(0f, arg3);
			} else {
				boardCamera.translate(-arg2 * boardCamera.zoom, arg3
						* boardCamera.zoom);
			}
		} else if (modifyCards) {
			cardCamera.translate(-arg2 * cardCamera.zoom, 0f);
		}
		return false;
	}

	@Override
	public boolean fling(float arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float arg0, float arg1) {
		if (modifyBoard) {
			if (arg1 - arg0 > 0 && boardCamera.zoom > 0.4f) {
				boardCamera.zoom -= 0.05f;
			} else if (arg0 - arg1 > 0 && boardCamera.zoom < 1.0f) {
				boardCamera.zoom += 0.05f;
			} else if (boardCamera.zoom == 1.0f) {
				boardCamera.position.set(240, 400, 0f);
			}
			// checkCameraBounds();
		} else if (false) {
			if (arg1 - arg0 > 0 && cardCamera.zoom > 0.4f) {
				cardCamera.zoom -= 0.05f;
			} else if (arg0 - arg1 > 0 && cardCamera.zoom < 2.0f) {
				cardCamera.zoom += 0.05f;
			}
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 arg0, Vector2 arg1, Vector2 arg2, Vector2 arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public void checkCameraBounds() {
		Vector3 position = this.cardCamera.position;
		Vector3 relativeMinimum = new Vector3(240 * this.cardCamera.zoom,
				400 * this.cardCamera.zoom, 0);
		Vector3 relativeMaximum = new Vector3(480 - relativeMinimum.x,
				800 - relativeMinimum.y, 0);

		if (position.x < relativeMinimum.x) {
			if (position.y < relativeMinimum.y) {
				this.cardCamera.translate(relativeMinimum);
			} else if (position.y > relativeMaximum.y) {
				this.cardCamera.translate(relativeMinimum.x, relativeMaximum.y);
			} else {
				this.cardCamera.translate(relativeMinimum.x, position.y);
			}
		} else if (position.x > relativeMaximum.x) {
			if (position.y < relativeMinimum.y) {
				this.cardCamera.translate(relativeMaximum.x, relativeMinimum.y);
			} else if (position.y > relativeMaximum.y) {
				this.cardCamera.translate(relativeMaximum);
			} else {
				this.cardCamera.translate(relativeMaximum.x, position.y);
			}
		}
	}
}
