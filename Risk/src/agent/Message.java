package agent;
import logic.*;

import java.util.ArrayList;

public class Message implements java.io.Serializable{
	
	private String subject;
	private boolean done = false;
	private int fdTerritory;
	private ArrayList<Card> cards;
	private ArrayList<Integer> deploys;
	private ArrayList<Integer> attack; 
	private ArrayList<Integer> fortify;
	private GameState gs;
	private int[] alliance;
	
	public Message(){
		this.cards = new ArrayList<Card>();
		this.deploys = new ArrayList<Integer>();
		attack = new ArrayList<Integer>();
		fortify = new ArrayList<Integer>();
		alliance = new int[2];
	}
	
	
	
	public void reset(){
		this.cards.clear();
		this.deploys.clear();
		done = false;
	}
	
	public void done(){
		done = true;
	}
	
	public boolean isDone(){
		return done;
	}
	
	public String getSubject(){
		return subject;
	}
	public void setSubject(String subject){
		this.subject = subject;
	}
	
	public void setFDTerritory(int n){
		this.fdTerritory = n;
	}
	
	public int getFDTerritory(){
		return this.fdTerritory;
	}
	
	public void setCards(ArrayList<Card> cards){
		this.cards = cards;
	}
	
	public ArrayList<Card> getCards(){
		return cards;
	}
	
	public void setDeploys(ArrayList<Integer> territories){
		this.deploys = territories;
	}
	
	public ArrayList<Integer> getDeploys(){
		return deploys;
	}
	
	public void setAttack(ArrayList<Integer> territories){
		this.attack = territories;
		
	}
	
	public ArrayList<Integer> getAttack(){
		return attack;
	}
	
	public void setFortify(ArrayList<Integer> territories){
		fortify = territories;
	}
	
	public ArrayList<Integer> getFortify(){
		return fortify;
	}
	
	public void setState(GameState gs){
		this.gs = gs;
	}
	
	public GameState getState(){
		return this.gs;
	}
	
	public int[] getAlliance(){
		
		return this.alliance;
	}
	
	public void setAlliance(int playerA, int playerB){
		
		alliance[0] = playerA;
		alliance[1] = playerB;
	}
	
	
}
