package se.chalmers.dryleafsoftware.androidrally.game;

public class GameSettings {

	public final static int DEFAULT_HUMAN_PLAYERS = 1;
	public final static int DEFAULT_BOTS = 7;
	public final static int DEFAULT_HOURS_ROUND = 24;
	public final static int DEFAULT_CARD_SECOND = 45;

	private int nbr_of_human_players = DEFAULT_HUMAN_PLAYERS;
	private int nbr_of_bots = DEFAULT_BOTS;
	private int hours_each_round = DEFAULT_HOURS_ROUND;
	private int card_timer_seconds = DEFAULT_CARD_SECOND;

	public static final String DEFAULT_MAP = null;
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
		this.nbr_of_human_players = humanPlayer;
		this.nbr_of_bots = bots;
		this.hours_each_round = hoursEachRound;
		this.card_timer_seconds = cardTimeSeconds;
		this.map = map;
	}

	public int getNbrOfHumanPlayers() {
		return this.nbr_of_human_players;
	}

	public int getNbrOfBots() {
		return this.nbr_of_bots;
	}

	public int getHoursEachRound() {
		return this.hours_each_round;
	}

	public int getCardTimerSeconds() {
		return this.card_timer_seconds;
	}

	public String getMap() {
		return this.map;
	}

}
