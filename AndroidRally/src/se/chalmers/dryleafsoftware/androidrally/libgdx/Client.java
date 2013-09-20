package se.chalmers.dryleafsoftware.androidrally.libgdx;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Client {

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
	
	public Client() {
		
	}
	
	public String[][] getMap() {
		return testmap;
	}
	
	public List<PlayerPieceView> getPlayers(Texture texture) {
		List<PlayerPieceView> players = new ArrayList<PlayerPieceView>();
		
		TextureRegion playerTexture1 = new TextureRegion(texture, 0, 64, 64, 64);
		PlayerPieceView player1 = new PlayerPieceView(1, playerTexture1);
		player1.setPosition(80, 800 - 160);
		player1.setOrigin(20, 20);
		players.add(player1);
		
		TextureRegion playerTexture2 = new TextureRegion(texture, 64, 64, 64, 64);
		PlayerPieceView player2 = new PlayerPieceView(2, playerTexture2);
		player2.setPosition(160, 400);
		player2.setOrigin(20, 20);
		players.add(player2);
		
		return players;
	}
}
