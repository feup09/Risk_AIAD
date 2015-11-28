package logic;

import java.util.ArrayList;

public class GameState {
	
	public enum playerType {HUMAN,BOT1,BOT2,BOT3};
	public enum playerColor {GREEN,BLUE,RED,YELLOW,GREY,BLACK};
	
	public int maxPlayers;
	public int numPlayers;
	
	public ArrayList<Player> players;
	public ArrayList<Territory> territories;
	
	public ArrayList<Card> cardDeck;
	public ArrayList<Card> usedDeck;
        
        ////http://www.datagenetics.com/blog/november22011/
        double[][] win = {{0.4167, 0.5787, 0.6597}, {0.2546, 0.2276, 0.3717}};
	double[][] tie = {{0, 0, 0}, {0, 0.3241, 0.3358}};
        
        public double getWin(int atk, int def) {
            return win[atk+1][def+1];
        }
        
        public double getTie(int atk, int def) {
            return tie[atk+1][def+1];
        }
}
