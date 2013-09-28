package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.AnimatedImage;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.CheckPointView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.ConveyorBeltView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.GearsView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.RobotView;
import se.chalmers.dryleafsoftware.androidrally.libgdx.gameboard.DockView;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

/**
 * This view class displays everything on the game area.
 * 
 * @author
 *
 */
public class BoardView extends Stage {

	private List<RobotView> robots = new ArrayList<RobotView>();
	private List<AnimatedImage> animated = new ArrayList<AnimatedImage>();
	private Vector2[] docks = new Vector2[8];
	private final Group container; 
	private final Table scrollContainer;
	private final ScrollPane pane;
		
	/*
	 * Used when loading the map from data values.
	 */
	private static final int 
			TILE_HOLE = 1,
			TILE_CHECKPOINT = 2,
			TILE_CONVEYORBELT = 3,
			TILE_GEARS = 4,
			TILE_REPAIR = 5,
			TILE_WALL = 6,
			TILE_LASER = 7,
			TILE_START = 8;
	
	/**
	 * Creates a new instance of BoardView.
	 */
	public BoardView() {
		super();
		// Create a group of all the objects on the map.
		container = new Group();
		container.setPosition(0, 0);
		container.setSize(480, 640);		
		
		// Create a inner container for the the ScrollPane.
		scrollContainer = new Table();
		scrollContainer.setFillParent(true);
		scrollContainer.setLayoutEnabled(false);
		scrollContainer.add(container);
		scrollContainer.debug();
		
		// Create the ScrollPane.
        ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
        pane = new ScrollPane(scrollContainer, scrollStyle);
        pane.setForceOverscroll(true, true);
        
        // Create a container to hold the ScrollPane.
        Table scrollParent = new Table();
        scrollParent.add(pane);
        scrollParent.debug();
        scrollParent.setPosition(0, 320);
        scrollParent.setSize(480, 480);
        addActor(scrollParent);
        
        // Create a default camera.
        OrthographicCamera boardCamera = new OrthographicCamera(480, 800);
		boardCamera.position.set(240, 400, 0f);
		boardCamera.update();
		setCamera(boardCamera);
        
        setZoom(1f, 0, 0);	        
        pane.addListener(new ActorGestureListener() {
        	@Override
        	public void zoom(InputEvent event, float initialDistance, float distance) {
        		if(distance - initialDistance > 0) {
        			setZoom(getZoom() + 0.02f, event.getStageX(), event.getStageY());
        		}else{
        			setZoom(getZoom() - 0.02f, event.getStageX(), event.getStageY());
        		}
        	}
        	@Override
        	public void tap(InputEvent event, float x, float y, int count, int button) {
        		if(count == 2) {
        			if(getZoom() == 2){
        				setZoom(1f, event.getStageX(), event.getStageY());
        			} else {
        				setZoom(2f, event.getStageX(), event.getStageY());
        			}
        		}
        	}
        });
	}
	
	/**
	 * Gives the current zoom level.
	 * @return The current zoom level.
	 */
	public float getZoom() {
		return container.getScaleX();
	}
	
	/**
	 * Sets the zoom level.
	 * @param zoom The new zoom level.
	 */
	public void setZoom(float zoom, float x, float y) {
		if(zoom < 1) {
			zoom = 1;
		}else if(zoom > 2) {
			zoom = 2f;
		}
		container.setScale(zoom);
		container.setSize(480 * zoom, 640 * zoom);	
		scrollContainer.setSize(container.getWidth(), container.getHeight());	
		pane.layout();
	}
	
	/**
	 * Builds the board using the specified texture and map data.
	 * @param texture The texture to use.
	 * @param map An array of strings describing the map's layout.
	 * NOTE: The bottom four rows of the map array will always be created as the dock area.
	 */
	public void createBoard(Texture texture, String[][] map) {
		Texture conveyerTexture = new Texture(Gdx.files.internal("textures/special/conveyor.png"));
		conveyerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		conveyerTexture.setWrap(TextureWrap.ClampToEdge, TextureWrap.Repeat);

		// To be added on top of all the other objects
		List<Image> overlay = new ArrayList<Image>();

		for(int y = 0; y < map[0].length; y++) {	
			for(int x = 0; x < map.length; x++) {
				// Create the floor
				Image floor = null;
				if(y < map[0].length - 4) {
					floor = new Image(new TextureRegion(texture, 0, 0, 64, 64));
				}else{
					floor = new Image(new TextureRegion(texture, 64, 0, 64, 64));
				}
				// Add the floor
				floor.setSize(40, 40);
				floor.setPosition(40 * x, 640 - 40 * (y+1));
				container.addActor(floor);

				// Add all the elements to the tile
				if(!map[x][y].equals("")) {
					for(String elementData : map[x][y].split(":")) {
						int tileData = Integer.parseInt(elementData);

						// Create the boardelement
						int tile = tileData % 10;
						Image i = null;
						// TODO : Switch
						switch(tile) {
						case TILE_HOLE:
							i = new Image(new TextureRegion(texture, 128, 0, 64, 64));
							break;
						case TILE_GEARS:
							AnimatedImage gear = new GearsView(new TextureRegion(texture, 320, 0, 64, 64), 
									tileData / 10 == 0 ? false : true);
							animated.add(gear);
							i = gear;			
							break;
						case TILE_CONVEYORBELT:
							AnimatedImage belt = new ConveyorBeltView(new TextureRegion(
									conveyerTexture, 64 * (tileData/100 - 1), 0, 64, 64), 
									((tileData / 10) % 10) * 90, tileData / 100);
							animated.add(belt);
							i = belt;	
							break;
						case TILE_CHECKPOINT:
							i = new CheckPointView(new TextureRegion(texture, 192, 0, 64, 64), 
									(tileData / 10));
							break;
						case TILE_REPAIR:
							i = new Image(new TextureRegion(texture, 256, 0, 64, 64));
							break;
						case TILE_START:
							i = new DockView(new TextureRegion(
									texture, 0, 128, 64, 64), tileData / 10);
							docks[tileData/10 - 1] = new Vector2(40 * x, 640 - 40 * (y+1));
							break;
						case TILE_WALL:
						case TILE_LASER:
							int rotation = tileData / 10;
							Image overlayImage;
							if(tile == TILE_WALL) {
								overlayImage = new Image(
										new TextureRegion(texture, 384, 0, 64, 64));
							}else{
								overlayImage = new Image(
										new TextureRegion(texture, 448, 0, 64, 64));
							}
							// Sets the objects position and size.
							overlayImage.setSize(40, 40);
							overlayImage.setPosition(40 * x - 20, 640 - 40 * (y+1));
							overlayImage.setOrigin(overlayImage.getWidth()/2 + 20, 
									overlayImage.getHeight()/2);
							overlayImage.rotate(-(1 + rotation) * 90);
							overlay.add(overlayImage);		
							break;
						}
						// Add the element if created and assign it the right size and position.
						if(i != null) {
							i.setSize(40, 40);
							i.setPosition(40 * x, 640 - 40 * (y+1));
							container.addActor(i);
						}
					} // loop - elements
				} // if
			} // loop - X
		} // loop - Y
				
		//Add walls (added last to be on top of everything else)
		for(Image i : overlay) {
			container.addActor(i);
		}
	}

	/**
	 * Starts the animations of the items connected with the specified phase.
	 * @param subPhase The phase to animate.
	 */
	public void setAnimate(int subPhase) {
		for(AnimatedImage a : animated) {
			a.enable(subPhase);
		}
	}

	/**
	 * Stops all animations.
	 */
	public void stopAnimations() {
		for(AnimatedImage a : animated) {
			a.disable();
		}
	}
	
	/**
	 * Gives an array of the eight docks' positions stored as vectors where (0,0) is the top
	 * left corner and (40, 40) is the tile to the right and down.
	 * @return An array of the eight docks.
	 */
	public Vector2[] getDocksPositions() {
		return this.docks;
	}
	
	/**
	 * Adds the specified player to the board.
	 * @param player The player to add.
	 */
	public void addRobot(RobotView player) {
		robots.add(player);
		container.addActor(player);
	}
	
	/**
	 * Gives a list of all the robots on the board.
	 * @return A list of all the robots on the board.
	 */
	public List<RobotView> getRobots() {
		return this.robots;
	}
	
	/**
	 * Gives the robot with the specified robot ID-number.
	 * @param robotID The ID-number to look for.
	 * @return The robot with the specified ID.
	 */
	public RobotView getRobot(int robotID) {
		for(RobotView p : robots) {
			if(p.getRobotID() == robotID) {
				return p;
			}
		}
		return null;
	}
}
