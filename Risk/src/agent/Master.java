package agent;
import logic.*;
import logic.GameState.Phase;
import gui.*;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Hashtable;
import java.util.Set;

import agent.Player.PlayerType;

public class Master extends Agent {

	private GameState gs;
	private MapPanel map;
	private int ready;
	Message m;
	Message r;
	
	public Master(GameState gs, MapPanel map){
		super();
		this.map = map;
		this.gs = gs;
		ready = 0;
		m = new Message();
		r = new Message();
		
		
	}
	
	public void start(){
		//msg para todos;
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		m.reset();
		r.reset();
    	for(int j = 0; j < gs.getPlayers().size();++j){
        	msg.addReceiver(new AID(gs.getPlayers().get(j).PlayerName, AID.ISLOCALNAME ));
        	
    	}
    	try {
    		m.setSubject("ready");
			msg.setContentObject(m);
			send(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
	}
	

	class gameMasterBehaviour extends SimpleBehaviour {
		
		private static final long serialVersionUID = 1L;
        
        // construtor do behaviour
        public gameMasterBehaviour(Agent a) {
            super(a); 
        }
        
       

        // metodo action
        @Override
        public void action() {
            ACLMessage msg = blockingReceive();
            ACLMessage outgoing = new ACLMessage(ACLMessage.INFORM);
            r.reset();
            map.repaint();
            
            try {
                    Thread.sleep(50);
            } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
            }
            
            try {
            	
            	Message m = (Message) msg.getContentObject();
            	
            	System.out.println("teste");
                System.out.println(gs.getPhase().toString());
                System.out.println(m.getSubject());
                System.out.println(msg.getSender().getName());
                
            	
                
	            if (msg.getPerformative() == ACLMessage.INFORM){
	            		            	
	            	if(gs.getPhase()== Phase.REINFORCE){
	            		//deal with it
	            		int from = m.getFortify().get(0);
                                int to = m.getFortify().get(1);
                                int troops = m.getFortify().get(2);
                                gs.getTerritories().get(to).addArmy(troops);
                                gs.getTerritories().get(from).removeArmy(troops);
	            		
	            		
	            		//next
            			r.setState(gs);
            			r.setSubject("Attack");
	            		if(m.isDone()){
	            			gs.nextPhase();
	            		}
	            		m.reset();
	            		outgoing.setContentObject(r);
	            		outgoing.addReceiver(new AID(gs.getPlayers().get(gs.getPlayerTurn()).getPlayerName(),AID.ISLOCALNAME));
	            		System.out.println("sent Reinforce");
	            	}
	            	else if(gs.getPhase()== Phase.ATTACK){
	            		//deal with it;
	            		int from = m.getAttack().get(0);
                                int to = m.getAttack().get(1);
                                int troops = m.getAttack().get(2);
                         if(from!=-1 && to!=-1 && troops!=-1)
                             gs.doAttack(from, to, troops);
	            		
            			r.setState(gs);
            			r.setSubject("Reinforce");
	            		if(m.isDone()){
	            			gs.nextPhase();
	            			r.setSubject("Reinforce");
	            		}
	            		m.reset();
	            		outgoing.setContentObject(r);
	            		outgoing.addReceiver(new AID(gs.getPlayers().get(gs.getPlayerTurn()).getPlayerName(),AID.ISLOCALNAME));
	            		
	            		System.out.println("sent Attack");
	            	}
	            	else if(gs.getPhase()== Phase.DEPLOY){
	            		//deal with it
                                System.err.println("temp: "+gs.getPlayers().get(gs.getPlayerTurn()).getTempArmy());
                                if(gs.getPlayers().get(gs.getPlayerTurn()).getTempArmy()<1)
                                    m.done();
                                
                                else {    
                                    int terri = m.getDeploys().get(0);
                                    gs.getTerritories().get(terri).addArmy(1);
                                    gs.getPlayers().get(gs.getPlayerTurn()).removeArmy(1);
                                }
                                    //next
                                    r.setState(gs);
                                    r.setSubject("Attack");
                                    outgoing.addReceiver(new AID(gs.getPlayers().get(gs.getPlayerTurn()).getPlayerName(),AID.ISLOCALNAME));

                                    if(m.isDone()){
                                            gs.nextPhase();
                                    }
                                

                                    System.out.println("sent Deploy");
                                    m.reset();
                                    outgoing.setContentObject(r); 
	            	}
	            	else if(gs.getPhase()== Phase.TRADE){
	            		//deal with it
	            		
	            		
	            		//next
            			r.setState(gs);
            			r.setSubject("Trade");
            			outgoing.addReceiver(new AID(gs.getPlayers().get(gs.getPlayerTurn()).getPlayerName(),AID.ISLOCALNAME));
	            			            		
	            		if(m.isDone() || gs.getPlayers().get(gs.getPlayerTurn()).numCardsInHand() <3){
	            			gs.nextPhase();
	            			r.setSubject("Deploy");
	            		}
	            		
	            		System.out.println("sent Deploy");
	            		m.reset();
	            		outgoing.setContentObject(r);
                                
	            	}
	            	else if(m.getSubject().equals("FirstDeploy")){
	            		//deal with it
	            		System.out.println("fd");
	            		
	            		int terri = m.getFDTerritory();
	            		gs.getTerritories().get(terri).addArmy(1);
	            		gs.getPlayers().get(gs.getPlayerTurn()).removeArmy(1);
	            		
	            		gs.nextPhase();
	            		r.setSubject("FirstDeploy");
            			r.setState(gs);
                                System.out.print("n players: "+gs.getNPlayers());
            			outgoing.addReceiver(new AID(gs.getPlayers().get(gs.getPlayerTurn()).getPlayerName(),AID.ISLOCALNAME));
            			if(gs.getPhase() == Phase.TRADE){
            				r.setSubject("Trade");
            			}
            			System.out.println("sent First Deploy");
            			m.reset();
            			outgoing.setContentObject(r);
                                System.out.print("n players5: "+r.getState().getNPlayers());
	            		
	            	}
	            	/*else if()){
	            		if(m.isDone()){
	            			gs.nextPhase();
	            			r.setState(gs);
	            			outgoing.setContentObject(r);
	            		}
	            	}*/
	            	else if(gs.getPhase() == Phase.START){
	            		
	            		if(m.isDone()){
	            			++ready;
	            			
	            		}
	            		if(ready == gs.getNPlayers()){
	            			System.out.println("Master: All players Ready. Game Starting");
	            			gs.nextPhase();
	            			
	            			m.reset();
	            			r.setSubject("FirstDeploy");
	            			r.setState(gs);
	            			outgoing.addReceiver(new AID(gs.getPlayers().get(gs.getPlayerTurn()).getPlayerName(),AID.ISLOCALNAME));
	            			outgoing.setContentObject(r);
	            		}
	            	}
	            	
	            	send(outgoing);
	            }
	            else{
	            	System.out.println("deal with non inform struff");
	            }
	            	
	            
	            
            } catch (UnreadableException | NullPointerException e) {
				//e.printStackTrace();
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
        gameMasterBehaviour gamemaster = new gameMasterBehaviour(this);
        
        // regista agente no DF
        DFAgentDescription dfd = new DFAgentDescription();
        
        AID id = new AID("Master", AID.ISLOCALNAME );
        dfd.setName(getAID());
        System.out.println("Info: "+getAID().getName());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(id.getName());
        
        //set type
        sd.setType("Master");
        addBehaviour(gamemaster);
        
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
    
    
}
