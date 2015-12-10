package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import jade.BootProfileImpl;
import jade.core.Profile;
import jade.core.Runtime;
import jade.util.leap.HashMap;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class GameState {

	public int maxPlayers;
	public int numPlayers;

	public ArrayList<Player> players= new ArrayList<Player>();
	public ArrayList<Territory> territories = new ArrayList<Territory>();

	public ArrayList<Card> cardDeck = new ArrayList<Card>();
        HashMap rating;

    public void rondaInicial() {
        int occupiedTerritories =0;
        int dado[] = new int[numPlayers]; //Guarda lancar de dados para definir primeiro a jogador
        int bestThrow=0;
        int bestPlayer=0;
        
        for(int i=0; i<numPlayers; i++) {
            dado[i] = players.get(i).throwDice(1)[0];
            if(bestThrow < dado[i]) {
                bestPlayer = i;
                bestThrow = dado[i];
            }
        }
        
        int j=bestPlayer;
        while(occupiedTerritories < 42) { //Ocupamos todos territorios 
            players.get(j).getOcuppy(this);
            j++;
            if(j==(numPlayers -1)) j=0;
        }
    }
	
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
            rating.put("Australia", 3.250);
            rating.put("South America", 2.250);
            rating.put("North America", 1.222);
            rating.put("Africa", 1.000);
            rating.put("Europe", 0.5571);
            rating.put("Asia", 0.167);
            
            this.numPlayers=numPlayers;
            Runtime runtime = Runtime.instance();
            String[] arguments = new String[1];
            arguments[0] = "-gui";
            Profile profile = new BootProfileImpl(arguments);
            ContainerController mainContainer = runtime.createMainContainer(profile);

            //Game Master
            Player master= new Player(0,Player.PLAYERTYPE.MEGABOT,Player.PLAYERCOLOR.BLUE,this);
            master.gs=this;
            AgentController agc = mainContainer.acceptNewAgent("Master", master);
            agc.start();
            players.add(master);

            Thread.sleep(1000);

            //Player 1
            Player nolasco= new Player(1, Player.PLAYERTYPE.BOT, Player.PLAYERCOLOR.BLACK, this);
            AgentController agc1 = mainContainer.acceptNewAgent("nolasco", nolasco);
            agc1.start();
            players.add(nolasco);
            
            Thread.sleep(1000);

            //Player 2
            Player bruno= new Player(2,Player.PLAYERTYPE.BOT, Player.PLAYERCOLOR.BLUE, this);
            AgentController agc2 = mainContainer.acceptNewAgent("bruno", bruno);
            agc2.start();
            players.add(bruno);
            
            Thread.sleep(1000);

            //Player 3
            Player ricardo= new Player(3, Player.PLAYERTYPE.BOT, Player.PLAYERCOLOR.GREEN, this);
            AgentController agc3 = mainContainer.acceptNewAgent("ricardo", ricardo);
            agc3.start();
            players.add(ricardo);
            
            
            
            int numArmies=0;
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
            for(int i=0;i<this.numPlayers;i++){
                players.get(i).armies = numArmies;
            }
            System.out.println("Num de exercitos por jogador" + numArmies );

            newDeck();
            rondaInicial();
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
	
	public void giveCards(Player player){
            player.playerCards.add(cardDeck.get(0));
            cardDeck.remove(0);
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
