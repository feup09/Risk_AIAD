package logic;

import java.util.ArrayList;

public abstract class Player {
	
	
	
	private int playerId;
	private boolean playing;
	GameState.playerType playerType;
	GameState.playerColor playerColor;
	
	//personality
	private boolean optimist;
	private boolean impulsive;
	private boolean vingative;
	
	private ArrayList<Territory> playerTerritories;
	private ArrayList<Card> playerCards;
	
	public abstract boolean isHuman();
	
	public Player(int id, GameState.playerType type, GameState.playerColor color, boolean opt, boolean imp, boolean ving ){
		
		playerId = id;
		playing = true;
		playerType = type;
		playerColor = color;
		optimist = opt;
		impulsive = imp;
		vingative = ving;
		
		playerTerritories = new ArrayList<Territory>();
		playerCards = new ArrayList<Card>();
		
	}
	
	public MoveTrade getTrade(){
		MoveTrade  move= null;
		
		
		return move;
	}
	
	public MoveDeploy getDeploy(){
		MoveDeploy  move= null;
		
		
		return move;
	}
	
	
	public MoveAttack getAttack(){
		MoveAttack  move= null;
		
		
		return move;
	}
	
	public MoveFortify getFortify(){
		MoveFortify  move= null;
		
		
		return move;
	}
	
	
	

}
