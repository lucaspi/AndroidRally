package se.chalmers.dryleafsoftware.androidrally.game;

import java.util.Random;

public class GameSettings {

	public final static int DEFAULT_HUMAN_PLAYERS = 1;
	public final static int DEFAULT_BOTS = 7;
	public final static int DEFAULT_HOURS_ROUND = 24;
	public final static int DEFAULT_CARD_SECOND = 45;

	private int nbrOfHumanPlayers = DEFAULT_HUMAN_PLAYERS;
	private int nbrOfBots = DEFAULT_BOTS;
	private int hoursEachRound = DEFAULT_HOURS_ROUND;
	private int cardTimerSeconds = DEFAULT_CARD_SECOND;

	public static final String DEFAULT_MAP = "yxxxxxxxxx36:37xxxx06xxxyxxxxxxx133xx113xxxxx78x16xyxxx12xxxx133x5x113xxx32xxx58:16xyx5x103x103x103xxx133xx113xxxxxx38:16xyxxxxx06:07xxxxxxxx06xx16xyxxxxx06x5x123x123x123x123x123x4xxx18:16xyxxxx27x06xxxxxxxxxx28:16xyx123x123x123x123x06:07xxxxxxxx06xx16xyxxxxxx5x203x203x203x203x203x203xxx48:16xyxxx42xxx14x123x123x123x123x123x14xxx68:16xyxx5xxxx22x1xxxxxxx88xxyxxxxxxxxxxxx5x46xxx";
	private String map = DEFAULT_MAP;

	/**
	 * Constructs a settings object with default parameters
	 */
	public GameSettings() {
		this(DEFAULT_HUMAN_PLAYERS, DEFAULT_BOTS);
	}

	/**
	 * Constructs a settings object with the user defined settings and default
	 * timers and map
	 * 
	 * @param humanPlayers
	 *            The amount of human players
	 * @param bots
	 *            The amount of bots
	 */
	public GameSettings(int humanPlayers, int bots) {
		this(humanPlayers, bots, DEFAULT_HOURS_ROUND, DEFAULT_CARD_SECOND);

	}

	/**
	 * Constructs a settings object with the user defined settings and a default
	 * map
	 * 
	 * @param humanPlayer
	 *            The amount of human players
	 * @param bots
	 *            The amount of bots
	 * @param hoursEachRound
	 *            How long a round is allowed to be, in hours
	 * @param cardTimeSeconds
	 *            How long the card timer should be, in seconds
	 */
	public GameSettings(int humanPlayer, int bots, int hoursEachRound,
			int cardTimeSeconds) {
		this(humanPlayer, bots, hoursEachRound, cardTimeSeconds, DEFAULT_MAP);

	}

	/**
	 * Constructs a settings object with all settings user defined
	 * 
	 * @param humanPlayer
	 *            The amount of human players
	 * @param bots
	 *            The amount of bots
	 * @param hoursEachRound
	 *            How long a round is allowed to be, in hours
	 * @param cardTimeSeconds
	 *            How long the card timer should be, in seconds
	 * @param map
	 */
	public GameSettings(int humanPlayer, int bots, int hoursEachRound,
			int cardTimeSeconds, String map) {
		this.nbrOfHumanPlayers = humanPlayer;
		this.nbrOfBots = bots;
		this.hoursEachRound = hoursEachRound;
		this.cardTimerSeconds = cardTimeSeconds;
		this.map = map;
	}

	public int getNbrOfHumanPlayers() {
		return this.nbrOfHumanPlayers;
	}

	public int getNbrOfBots() {
		return this.nbrOfBots;
	}

	public int getHoursEachRound() {
		return this.hoursEachRound;
	}

	public int getCardTimerSeconds() {
		return this.cardTimerSeconds;
	}

	public String getMap() {
		return this.map;
	}
	
	/**
	 * Gives a random map.
	 * @return a random map
	 */
	public String getRandomMap() {
		Random random = new Random();
		int i = random.nextInt(2);
		
		switch (i) {
		case 0:
			map = DEFAULT_MAP;
			break;
		case 1:
			map = "yxxxxxxx213xxxx16xxx16xxyxx12xxxx16x213xxxx37x26xx78x16xyxxxxxxx213xxx32xxxxx58:16xyxxxx26xxx213xxxx16x26xxx38xyxx1x1xx06xx14x223x223x223x223x223xxx16xyxx26xx26xxxxxxxxxxx18:16xyxxxx26xxxxxxxxxxx28:16xyxx26xx26xxx5x103x103x103x103x103xxxxyxx26xx26x07xx113xxxx36x26xxx48:16xyxxxxxx16x113xxx22xxxxx68:16xyxx5xxxxx113xxxx16:17x26xx88:16xxyxxxxxxx113xxxxxxxxx";
			break;
		}
		return map;
	}

}
