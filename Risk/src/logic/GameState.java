package logic;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import logic.Card.TERRITORY;

public class GameState {

	public int maxPlayers;
	public int numPlayers;

	public ArrayList<Player> players;
	public ArrayList<Territory> territories;

	public ArrayList<Card> cardDeck = new ArrayList<Card>();
	public ArrayList<Card> usedDeck;
	
	public enum HORSETERRITORY{
		//NORTH AMERICA
    ALASKA,
    ALBERTA,
    CENTRAL_AMERICA,
    EASTERN_UNITED_STATES,
    GREENLAND,
    NORTHWEST_TERRITORY,
    ONTARIO,
    QUEBEC,
    WESTERN_UNITED_STATES,
		//SOUTH AMERICA
	ARGENTINA,
    BRAZIL,
    PERU,
    VENEZUELA
	}
	
	public enum SOLDIERTERRITORY{
		//NORTH AMERICA

		//EUROPE
    GREAT_BRITAIN,
    ICELAND,
    NORTHERN_EUROPE,
    SCANDINAVIA,
    SOUTHERN_EUROPE,
    UKRAINE,
    WESTERN_EUROPE,
		//AFRICA
		CONGO,
    EAST_AFRICA,
    EGYPT,
    MADAGASCAR,
    NORTH_AFRICA,
    SOUTH_AFRICA
	}
	
	public enum CANNONTERRITORY{

		//ASIA
	AFGHANISTAN,
    CHINA,
    INDIA,
    IRKUTSK,
    JAPAN,
    KAMCHATKA,
    MIDDLE_EAST,
    MONGOLIA,
    SIAM,
    SIBERIA,
    URAL,
    YAKUTSK,
		//AUSTRALIA
		EASTERN_AUSTRALIA,
    INDONESIA,
    NEW_GUINEA,
    WESTERN_AUSTRALIA
	}
	
	

        ////http://www.datagenetics.com/blog/november22011/
  double[][] win = {{0.4167, 0.5787, 0.6597}, {0.2546, 0.2276, 0.3717}};
	double[][] tie = {{0, 0, 0}, {0, 0.3241, 0.3358}};

	public GameState (int numPlayers){
		int numArmies=0;
		this.numPlayers=numPlayers;
		switch(this.numPlayers){
			case 2:numArmies=40;
			break;
			case 3:numArmies=35;
			break;
			case 4:numArmies=30;
			break;
			case 5:numArmies=25;
			break;
			case 6:numArmies=20;
			break;
			default:System.out.println("Invalid number of players");
		}
		System.out.println("Num de exércitos por jogador" + numArmies );
		
		newDeck();
	}
	
	public void newDeck(){
		Card tempCard;
		long seed = System.nanoTime();
		for (HORSETERRITORY horseterritory : HORSETERRITORY.values()) {
			tempCard= new Card(Card.FIGURE.HORSE,Card.TERRITORY.valueOf(horseterritory.toString()));
			this.cardDeck.add(tempCard);
		}
		for (SOLDIERTERRITORY soldierterritory : SOLDIERTERRITORY.values()) {
			tempCard= new Card(Card.FIGURE.SOLDIER,Card.TERRITORY.valueOf(soldierterritory.toString()));
			this.cardDeck.add(tempCard);
		}
		for (CANNONTERRITORY cannonterritory : CANNONTERRITORY.values()) {
			tempCard= new Card(Card.FIGURE.CANNON,Card.TERRITORY.valueOf(cannonterritory.toString()));
			this.cardDeck.add(tempCard);
		}
		
		for(int i=0; i<2; i++){
			tempCard= new Card(Card.FIGURE.ALL,Card.TERRITORY.JOKER);
			this.cardDeck.add(tempCard);
		}
		
		

		Collections.shuffle(this.cardDeck, new Random(seed));
		
		
	}
	
	public void giveCards(Player player,int numTerritories){
		for(int i=0; i<numTerritories; i++){
			player.playerCards.add(cardDeck.get(i));
			cardDeck.remove(i);
		}
	}
	
	public void doTrade(Player player, ArrayList<Card> cards){
		switch(player.numTrades){
			case 0:
				player.armies+=4;
				player.numTrades++;
				break;
			case 1:
				player.armies+=6;
				player.numTrades++;
				break;
			case 2:
				player.armies+=8;
				player.numTrades++;
				break;
			case 3:
				player.armies+=10;
				player.numTrades++;
				break;
			case 4:
				player.armies+=12;
				player.numTrades++;
				break;
			case 5:
				player.armies+=15;
				player.numTrades++;
				break;
			default:
				player.armies+=((player.numTrades-2)*5);
				player.numTrades++;
				break;
		}
		
		for(int i=0; i<cards.size();i++){
				if(player.playerTerritories.contains(cards.get(i).territory))
				{
					player.armies+=2;
					break;
				}
			}
		
		cardDeck.add(cards.get(0));
		cardDeck.add(cards.get(1));
		cardDeck.add(cards.get(2));
		
		
			
	}

	public double getWin(int atk, int def) {
		return win[atk+1][def+1];
	}

	public double getTie(int atk, int def) {
		return tie[atk+1][def+1];
	}
}
