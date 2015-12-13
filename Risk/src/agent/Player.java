package agent;
import logic.*;
import gui.MapPanel;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Hashtable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import agent.Master.gameMasterBehaviour;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public abstract class Player extends Agent implements java.io.Serializable{



	private int playerId;
	public String PlayerName;
	private boolean playing;
	public enum PlayerType {HUMAN,BOT}
	PlayerType playerType;
	Territory.TColor playerColor;
	
	int armies;
	int tempArmies;
	
	Message m;
	Message r;

	//personality
	public double optimist;
	public double impulsive;
	public double vingative;

	protected ArrayList<Territory> playerTerritories;
	private ArrayList<Card> playerCards;

	public abstract boolean isHuman();
	public abstract void setMap(MapPanel map);
	public abstract int getFirstDeploy(GameState gs);
	public abstract ArrayList<Card> getTrade(GameState gs);
	public abstract ArrayList<Integer> getAttack(GameState gs);
	public abstract ArrayList<Integer> getDeploy(GameState gs);
	public abstract ArrayList<Integer> getFortify(GameState gs);
	
	public Player(int id, String PlayerName, int type, int color, double optimist, double impulsive, double vingative){
		super();
		playerId = id;
		this.PlayerName = PlayerName;
		this.armies = 0;
		this.tempArmies = 0;
		
		playing = true;
		
		this.playing =true;
		
		this.optimist = optimist;
		this.impulsive = impulsive;
		this.vingative = vingative;

		setPlayerColor(color);
		setPlayerType(type);
		playerTerritories = new ArrayList<Territory>();
		playerCards = new ArrayList<Card>();
		
		m = new Message();
		r = new Message();

	}
	
	
	
	class playerBehaviour extends SimpleBehaviour {
		
		private static final long serialVersionUID = 1L;
        
        // construtor do behaviour
        public playerBehaviour(Agent a) {
            super(a); 
        }

        // metodo action
        @Override
        public void action() {
            
            
            ACLMessage msg = blockingReceive();
            ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
            m.reset();
            r.reset();
            try {
				Message m = (Message) msg.getContentObject();
				
	            if (msg.getPerformative() == ACLMessage.INFORM){ //O player envia jogadas
	            	
	            	
	            	if(m.getSubject().equals("ready")){
	            		
	            		System.out.println("ready"+getId());
	            		
	            		r.setSubject("ready");
	            		r.done();
	            		
	            		reply.setContentObject(r);
	            		
	            	}
	            	else if(m.getSubject().equals("FirstDeploy")){
	            		
	            		int territory = getFirstDeploy(m.getState());
	            		
	            		r.setSubject("FirstDeploy");
	            		r.done();
	            		r.setFDTerritory(territory);
	            		reply.setContentObject(r);
	            		
	            	}
	            	else if(m.getSubject().equals("Trade")){
	            		
	            		ArrayList<Card> toTrade= new ArrayList<Card>();
	            		toTrade = getTrade(m.getState());
	            		if(toTrade.isEmpty()){
	            			r.done();
	            		}
	            		
	            		r.setSubject("Trade");
	            		r.setCards(toTrade);
	            		
	            		reply.setContentObject(r);
	            		
	            	}
	            	else if(m.getSubject().equals("Attack")){
	            		
	            		ArrayList<Integer> attackMove= new ArrayList<Integer>();
	            		attackMove = getAttack(m.getState());
	            		
	            		if(attackMove.get(0)== -1){
	            			r.done();
	            		}
	            		System.out.println("atack gs_np: "+ m.getState().getNPlayers());
	            		r.done();
	            		
	            		r.setSubject("Attack");
	            		r.setAttack(attackMove);
	            		
	            		reply.setContentObject(r);
	            		
	            	}
	            	else if(m.getSubject().equals("Reinforce")){
	            		
	            		ArrayList<Integer> reinforce= new ArrayList<Integer>();
	            		reinforce = getFortify(m.getState());
	            		
	            		r.setSubject("Reinforce");
	            		r.setFortify(reinforce);
	            		r.done();
	            		
	            		reply.setContentObject(r);
	            		
	            	}
	            	else if(m.getSubject().equals("Deploy")){
	            		ArrayList<Integer> reinforce= new ArrayList<Integer>();
                                reinforce = getDeploy(m.getState());
	            		
	            		
	            		r.setSubject("Deploy");
	            		r.setDeploys(reinforce);
	            		r.done();
	            		
	            		reply.setContentObject(r);
	            		
	            	}
	            	
	            	reply.addReceiver(new AID("Master", AID.ISLOCALNAME ));
            		send(reply);
	            	
	            }
            } catch (UnreadableException| NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

           }

        // metodo done
        public boolean done() {  
        	return false; 
        	}
	}
	
	
	
	 // metodo setup
    protected void setup() {
        // cria behaviour
        playerBehaviour gameplayer = new playerBehaviour(this);
        
        // regista agente no DF
   
        
        AID id = new AID(this.PlayerName, AID.ISLOCALNAME );
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        System.out.println("Info: "+getAID().getName());
        
        
        ServiceDescription sd = new ServiceDescription();
        sd.setName(id.getName());
        
        //set type
        sd.setType(this.playerType.toString());
        addBehaviour(gameplayer);
        
        dfd.addServices(sd);
        
        try {
           DFService.register(this, dfd);
        } catch(FIPAException e) {
           e.printStackTrace();
        }        
   }   // fim do metodo setup

    // metodo takeDown
    protected void takeDown() {
        // retira registo no DF
        try {
            DFService.deregister(this);  
        } catch(FIPAException e) {
            e.printStackTrace();
        }
    }
	
	public void setPlayerColor(int color){
		if(color == 1){
			this.playerColor = Territory.TColor.BLUE;
		}
		else if(color == 2){
			this.playerColor = Territory.TColor.RED;
		}
		else if(color == 3){
			this.playerColor = Territory.TColor.GREEN;
		}
		else if(color == 4){
			this.playerColor = Territory.TColor.PINK;
		}
		else if(color == 5){
			this.playerColor = Territory.TColor.YELLOW;
		}
		else if(color == 6){
			this.playerColor = Territory.TColor.TORQ;
		}
	}
	
	public void setPlayerType(int type){
		if(type == 1){
			this.playerType = Player.PlayerType.HUMAN;
		}
		
		else if(type == 2){
			this.playerType = PlayerType.BOT;
		}
		/*
		else if(type == 3){
			this.playerColor = Territory.TColor.GREEN;
		}
		else if(type == 4){
			this.playerColor = Territory.TColor.PINK;
		}*/
	}
	
	public String getPlayerName(){
		return this.PlayerName;
	}
	
	public PlayerType getPlayerType(){
		return this.playerType;
	}
	
	public void addTerritory(Territory t){
		playerTerritories.add(t);
		t.setOwner(this.playerId);
		t.setOcupied();
		t.setColor(this.playerColor);
	}
	
	public int getId(){
		return this.playerId;
	}
	
	public ArrayList<Territory> getTerritories(){
		return this.playerTerritories;
	}
	
	public Territory.TColor getPlayerColor(){
		return this.playerColor;
	}
	
	public boolean isPlaying(){
		return this.playing;
	}
	
	public void addArmy(int n){
		this.armies += n;
	}
	
	public void addTempArmy(int n){
		this.tempArmies += n;
	}
	
	public void setTempArmy(int n){
		this.tempArmies = n;
	}
	
	public void removeArmy(int n){
		this.armies -= n;
	}
	
	public void removeTempArmy(int n){
		this.tempArmies -= n;
	}
	
	public void resetArmies(){
		this.tempArmies = this.armies;
	}
	
	public void setArmyFromTemp(){
		this.armies = this.tempArmies;
		this.tempArmies = 0;
	}
	
	public int getTempArmy(){
		return this.tempArmies;
	}
	
	public int getArmy(){
		return this.armies;
	}
	
	public void removeTerritory(int n){
			
		for (Iterator<Territory> it = playerTerritories.iterator(); it.hasNext();) {
			if (it.next().getId() == n) {
			    it.remove();
			}
		}
	}
	
	public int numCardsInHand(){
		return this.playerCards.size();
	}
	
}
