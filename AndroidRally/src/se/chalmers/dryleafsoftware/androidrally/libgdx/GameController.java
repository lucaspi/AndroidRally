package se.chalmers.dryleafsoftware.androidrally.libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GameController implements GestureListener {

	private GdxGame game;
	private OrthographicCamera boardCamera, cardCamera;
	private boolean modifyBoard, modifyCards;
	
	/**
	 * Bara "" ger en tom ruta. "12:33" kommer skapa två elements på den rutan.
	 * entalen står för ID för elementet på den rutan. tiotalen står för
	 * speciella egenskaper för det elementet, t.ex 33 ger ett rullband (3) +
	 * roterat 3 gånger (30) = 33 t.ex 12 ger checkpoint (2) + numerordning 1
	 * (10) = 12 osv.
	 */
	private String[][] testmap = new String[][] {
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "16", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "14", "", "", "", "5", "", "", "", ""},
			{"", "37", "", "1", "", "", "", "233", "", "", "1", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "233", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "4", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "", "", "", "133", "", "", "", "", "", "", "", ""},
			{"", "5", "", "", "", "", "", "133", "", "", "", "1", "", "", "", ""},
			{"", "", "", "", "103", "103", "103", "133:103", "", "", "", "", "", "", "", ""},
			{"", "", "36", "", "", "", "", "", "", "", "", "", "", "", "", ""},
			{"", "", "", "", "4", "", "", "", "", "", "", "22", "", "", "", ""},
			{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""},
	};

	public GameController(GdxGame game) {
		this.game = game;
		this.boardCamera = this.game.getBoardCamera();
		this.cardCamera = this.game.getCardCamera();
		
		Texture boardTexture = new Texture(Gdx.files.internal("textures/testTile.png"));
		boardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		// TODO: Should get this from server
		game.getBoardView().createBoard(boardTexture, testmap);
		
		// TODO: Should get this from server
		TextureRegion playerTexture1 = new TextureRegion(boardTexture, 0, 64, 64, 64);
		PlayerPieceView player1 = new PlayerPieceView(1, playerTexture1);
		player1.setPosition(80, 800 - 160);
		player1.setOrigin(20, 20);
		game.getBoardView().addPlayer(player1);
		// TODO: Should get this from server
		TextureRegion playerTexture2 = new TextureRegion(boardTexture, 64, 64, 64, 64);
		PlayerPieceView player2 = new PlayerPieceView(2, playerTexture2);
		player2.setPosition(160, 400);
		player2.setOrigin(20, 20);
		game.getBoardView().addPlayer(player2);
		
		PlayerPieceView p = game.getBoardView().getPlayer(1);
		p.addAction(Actions.sequence(Actions.moveBy(40, 0, 2),
				Actions.parallel(Actions.fadeOut(2), Actions.scaleTo(0.3f, 0.3f, 2))));
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
