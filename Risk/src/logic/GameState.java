package logic;

import java.util.ArrayList;


public class GameState {
	
	public enum playerType {HUMAN,BOT1,BOT2,BOT3};
	public enum playerColor {GREEN,BLUE,RED,YELLOW,GREY,BLACK};
	
	private int maxPlayers;
	private int numPlayers;
	
	private ArrayList<Player> players;
	private ArrayList<Territory> territories;
	
	private ArrayList<Card> cardDeck;
	private ArrayList<Card> usedDeck;
	
}
