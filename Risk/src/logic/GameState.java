package logic;

import java.util.ArrayList;

public class GameState {

	public int maxPlayers;
	public int numPlayers;

	public ArrayList<Player> players;
	public ArrayList<Territory> territories;

	public ArrayList<Card> cardDeck;
	public ArrayList<Card> usedDeck;

        ////http://www.datagenetics.com/blog/november22011/
  double[][] win = {{0.4167, 0.5787, 0.6597}, {0.2546, 0.2276, 0.3717}};
	double[][] tie = {{0, 0, 0}, {0, 0.3241, 0.3358}};

	public GameState (int numPlayers){
		int numArmies=0;
		this.numPlayers=numPlayers;
		switch(this.numPlayers){
			case 2:numArmies=40;
			case 3:numArmies=35;
			case 4:numArmies=30;
			case 5:numArmies=25;
			case 6:numArmies=20;
			default:System.out.println("Invalid number of players");
		}
	}

  public double getWin(int atk, int def) {
  	return win[atk+1][def+1];
  }

  public double getTie(int atk, int def) {
    return tie[atk+1][def+1];
  }
}
