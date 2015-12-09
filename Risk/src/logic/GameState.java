package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import jade.BootProfileImpl;
import jade.core.Profile;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

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

	public GameState (int numPlayers) throws StaleProxyException, InterruptedException{
            Runtime runtime = Runtime.instance();
            String[] arguments = new String[1];
            arguments[0] = "-gui";
            Profile profile = new BootProfileImpl(arguments);
            ContainerController mainContainer = runtime.createMainContainer(profile);

            //Game Master
            Player master= new Player(0,Player.PLAYERTYPE.MEGABOT,Player.PLAYERCOLOR.BLUE);
            master.gs=this;
            AgentController agc = mainContainer.acceptNewAgent("Master", master);
            agc.start();

            Thread.sleep(1000);

            //Player 1
            Player nolasco= new Player(1, Player.PLAYERTYPE.BOT, Player.PLAYERCOLOR.BLACK);
            AgentController agc1 = mainContainer.acceptNewAgent("nolasco", nolasco);
            agc1.start();

            Thread.sleep(1000);

            //Player 2
            Player bruno= new Player(2,Player.PLAYERTYPE.BOT, Player.PLAYERCOLOR.BLUE);
            AgentController agc2 = mainContainer.acceptNewAgent("bruno", bruno);
            agc2.start();

            Thread.sleep(1000);

            //Player 3
            Player ricardo= new Player(3, Player.PLAYERTYPE.BOT, Player.PLAYERCOLOR.GREEN);
            AgentController agc3 = mainContainer.acceptNewAgent("ricardo", ricardo);
            agc3.start();
            
            
            
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
            System.out.println("Num de exercitos por jogador" + numArmies );

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
		

		Collections.shuffle(this.cardDeck, new Random(seed));
		
		System.out.println(this.cardDeck.get(0).territory);
	}

	public double getWin(int atk, int def) {
		return win[atk+1][def+1];
	}

	public double getTie(int atk, int def) {
		return tie[atk+1][def+1];
	}
}
