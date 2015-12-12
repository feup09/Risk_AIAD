package logic;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Player extends Agent{

    private int playerId;
    private boolean playing;
    public int numTrades;
    public int armies;
    public enum PLAYERTYPE {HUMAN, BOT, MEGABOT}
    public enum PLAYERCOLOR {GREEN,BLUE,RED,YELLOW,GREY,BLACK}
    PLAYERTYPE playerType;
    PLAYERCOLOR playerColor;
    GameState gs;

    //personality
    private boolean optimist;
    private boolean impulsive;
    private boolean vingative;

    public ArrayList<Territory> playerTerritories;
    public ArrayList<Card> playerCards;
    public ArrayList<Player> allies;

    private boolean isHuman;

    public boolean getIsHuman() {
        return isHuman;
    }

    public void setIsHuman(boolean is) {
        isHuman = is;
    }

    public Player(int id, PLAYERTYPE type, PLAYERCOLOR color , GameState gs){
        super();    
        String zero=null;//super(id + "", this.ISLOCALNAME)
            
        this.gs = gs;
            playerId = id;
            playing = true;
            this.playerType = type;
            this.playerColor = color;
            this.setPersonality();

            playerTerritories = new ArrayList<Territory>();
            playerCards = new ArrayList<Card>();
            allies = new ArrayList<Player>();

    }

    public int getNumCards(){
        return playerCards.size();
    }

    public int[] getDeploy(GameState gs){
            int move[]= {0}, newScore;
            int bestScore= -100;
            Territory aux;
            for(int i=0; i<this.playerTerritories.size(); i++) { //Corro o vetor de territórios que em pertencem
                aux = this.playerTerritories.get(i);
                newScore = this.scoreDeploy(gs, aux.id); //Pontuo a colocação de exércitos em cada território
                if(bestScore < newScore) {  // Atualizo a informação da melhor jogada
                    bestScore = newScore;
                    move[0] = aux.id;
                }
            }
            if (bestScore > -100)
                return move;
            else return null;
    }
    
    public int[] getAttack(GameState gs){
        int move[]= new int[2];
        double bestScore= -100;
        double newScore;
        Territory from = null, to= null;
        for(int i=0; i<this.playerTerritories.size(); i++) { //Corro o vetor de territórios que em pertencem
            from = this.playerTerritories.get(i);
            for(int j=0; j<from.neighbours.size(); j++) { //Corro o vetor de territórios adjacente a esse território
                to = from.neighbours.get(j);
                newScore = this.scoreAtack(gs, from.id, to.id); //Pontuo o ataque do territorio "from"  para "to"
                if(bestScore < newScore) {  // Atualizo a informação da melhor jogada
                    bestScore = newScore;
                    move[0] = from.id;
                    move[1] = to.id;
                }
            }
        }
        if ((bestScore > -100) && this.impulsive || (bestScore>0 && !this.impulsive) )
            return move;
        else return null;
    }

    public int[] getFortify(GameState gs){
        int move[]= new int[2];
        double bestScore= -100;
        double newScore;
        Territory from = null, to= null;
        for(int i=0; i<this.playerTerritories.size(); i++) { //Corro o vetor de territórios que em pertencem
            from = this.playerTerritories.get(i);
            for(int j=0; j<from.neighbours.size(); j++) { //Corro o vetor de territórios adjacente a esse território
                to = from.neighbours.get(j);
                newScore = this.scoreFortify(gs, from.id, to.id); //Pontuo o ataque do territorio "from"  para "to"
                if(bestScore < newScore) {  // Atualizo a informação da melhor jogada
                    bestScore = newScore;
                    move[0] = from.id;
                    move[1] = to.id;
                }
            }
        }
        if (bestScore > -100 )
            return move;
        else return null;
    }

    public void setPersonality() {
        // Um boolean por default já é false
        this.optimist = false;
        this.impulsive = false;
        this.vingative = false;
        boolean result[] = new boolean[3];
        Random gerador = new Random();

        int personality = gerador.nextInt(8)+1; //1 a 8

        if( (personality % 2) == 0)
            this.optimist = true;
        if( personality<5 )
            this.impulsive = true;
        if( (personality > 3) && (personality < 7))
            this.vingative = true;
        }

    public int[] throwDice(int number)  {
        Random gerador = new Random();
        int dice[] = new int[number];
        for (int i=0; i< number; i++)
            dice[i] = (gerador.nextInt(6) +1); //1 a 6
        return dice;
    }   

    public ArrayList<Card> getTrade(){
        ArrayList<ArrayList<Card>> tradeDeck= new ArrayList<ArrayList<Card>>();

        // Verifica as combinações possiveis
        for (int i=0; i<playerCards.size(); i++){
                for (int j=0; j<playerCards.size(); j++){
                        for (int k=0; k<playerCards.size(); k++){
                                if(i != j && i != k && j != k){
                                        Card.FIGURE card1 = playerCards.get(i).figure;
                                        Card.FIGURE card2 = playerCards.get(j).figure;
                                        Card.FIGURE card3 = playerCards.get(k).figure;

                                        if(card1==card2
                                        && card1==card3
                                        && card2==card3 ||
                                        card1!=card2
                                        && card1!=card3
                                        && card2!=card3 ){
                                                ArrayList<Card> auxDeck= new ArrayList<Card>();
                                                auxDeck.add(playerCards.get(i));
                                                auxDeck.add(playerCards.get(j));
                                                auxDeck.add(playerCards.get(k));
                                                tradeDeck.add(auxDeck);
                                        }
                                }
                        }
                }
        }

        // Dá uma pontuação extra aos decks possiveis baseado nos territórios
        Hashtable<Integer,Integer> bestDeck = new Hashtable<Integer,Integer>();

        for(int i=0; i<tradeDeck.size();i++){
                ArrayList<Card> auxDeck=tradeDeck.get(i);
                int valueDeck=0;
                for(int j=0; j<auxDeck.size(); j++){
                        if(playerTerritories.contains(auxDeck.get(j).territory))
                                valueDeck++;
                }
                bestDeck.put(i,valueDeck);
        }

//Escolhe o melhor deck para fazer trade
        double max = Double.NEGATIVE_INFINITY;
        int maxkey=0;
        Set<Integer> keys = bestDeck.keySet();
        for (Integer key : keys) {
                if (bestDeck.get(key) > max) {
                max = bestDeck.get(key);
                maxkey=key;
                }
        }

        return tradeDeck.get(maxkey);
}

    public int scoreDeploy(GameState gs, int terr){
        int allyArmy = 0;
        int enemyArmy = 0;

        //Contar exércitos
        Territory aux = gs.territories.get(terr);
        if(aux.owner == this){                            //Estou num território deste player
          for (int i=0; i< aux.neighbours.size(); i++) {  //Corro todos os vizinhos
              if(aux.neighbours.get(i).owner == this)     //Vizinho é o player
                  allyArmy = allyArmy + aux.army;         //Aumento aliados
              if(aux.neighbours.get(i).owner != this)     //Vizinho não é o player
                  enemyArmy = enemyArmy + aux.army;       //Aumento inimigos
          }
        }
        int score = - allyArmy + enemyArmy;
        return score;
    }

    public double scoreAtack(GameState gs, int fromTerr, int toTerr) {
    int allyArmy=0;
    int enemyArmy=0;
    double probVictory=0;

    Territory aux = gs.territories.get(toTerr);

    if(aux.owner == this){                              //Entre os vizinhos do territorio atacado
        for (int i=0; i< aux.neighbours.size(); i++) {  //Corro todos os vizinhos
            if(aux.neighbours.get(i).owner == this)     //Vizinho é o player
                allyArmy = allyArmy + aux.army-1;       //Conto tropas que podem atacar
            if(aux.neighbours.get(i).owner != this)     //Vizinho não é o player
                enemyArmy = enemyArmy + aux.army;       //Aumento inimigos
        }
    }

    int nAtk = gs.territories.get(fromTerr).army - 1;
    if (nAtk>3) nAtk = 3;
    //Se o jogador tiver pelo menos 2 tropas vai defender com 2 unidades
    if(aux.army > 1)
    probVictory = gs.getWin(nAtk, 2);
    else
    probVictory = gs.getWin(nAtk, 1);

    double probRetaliation = 0;
    if(aux.owner.vingative == true)
    probRetaliation = 1;

    double score = probVictory - probRetaliation*enemyArmy;
    return score;
    }

    public int scoreFortify(GameState gs, int fromTerr, int toTerr) {
        int enemyArmy = 0;
        int movesToFrontier = 100; // Se a fronteira ficar a mais de 1 movimento ignoro a jogada

        //Contar exércitos
        Territory aux = gs.territories.get(toTerr);
        if(aux.owner == this){                              //Estou num território deste player
            for (int i=0; i< aux.neighbours.size(); i++) {  //Corro todos os vizinhos
                if(aux.neighbours.get(i).owner != this)  {   //Vizinho não é o player
                    enemyArmy = enemyArmy + aux.army;       //Aumento inimigos
                    movesToFrontier =movesToFrontier*0;
                  }
            }
        }

        int displacedArmy = gs.territories.get(toTerr).army-1;      

        int score = displacedArmy + enemyArmy - movesToFrontier;
        return score;
    }

    public int scorePlayer(int id, GameState gs){
        int alliedArmy = 0;
        Territory mine;
        Territory adja;
        Player evalPlayer = gs.players.get(id);
        int numTerritories = evalPlayer.playerTerritories.size();   //Quantos exercitos tem player?

        for(int i=0; i< this.playerTerritories.size(); i++) {       //Corro territórios deste player
            mine = this.playerTerritories.get(i);                   
              for (int j=0; j< mine.neighbours.size(); j++) {       //Corro todos os vizinhos desse territorio
                  adja = mine.neighbours.get(j);
                  if(mine.owner == evalPlayer)                      //Adjacente pertende a player avaliado?
                      alliedArmy = alliedArmy + adja.army;          //Aumento aliados
              }
        }

        int score = alliedArmy + numTerritories;
        return score;
    }

    
    
    
    
    // classe do comunicacao players
    class playerBehaviour extends SimpleBehaviour {
        private int n = 0;

        // construtor do behaviour
        public playerBehaviour(Agent a) {
            super(a); 
        }

        // metodo action
        @Override
        public void action() {
            ACLMessage msg = blockingReceive();
            if (msg.getPerformative() == ACLMessage.INFORM){ //O player envia jogadas
                
                
                /*GameState state = null;
                try {
                    state = (GameState) msg.getContentObject();
                } catch (UnreadableException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                AID Sender  = msg.getSender();
                msg = new ACLMessage(ACLMessage.INFORM);

                
                System.err.println(Sender.getName());
                msg.addReceiver(Sender);
                msg.setPerformative(ACLMessage.INFORM);
                send(msg);

                System.out.println(playerId + " got inform");


                /*int lam[] = {4167, 5787};
                message response = new message(playerId, lam, lam, lam, lam,GameState.Phase.TRADE, gs);

                msg.setContentObject( response);
                send(msg);
                } catch(FIPAException e) {} catch (IOException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }  */
            }
        }
        
        @Override
        public boolean done() { return false;}  }

    // classe de comunicacao master
    class gameMasterBehaviour extends SimpleBehaviour {
        private int n=0;
        
        // construtor do behaviour
        public gameMasterBehaviour(Agent a) {
            super(a); 
        }

        // metodo action
        @Override
        public void action() {
            ACLMessage msg = blockingReceive();
            ACLMessage reply = msg.createReply();
            
            if (msg.getPerformative() == ACLMessage.INFORM){/* try {
                //O player recebe jogadas
                message play = (message)msg.getContentObject();
                System.out.println(++n + " Master recebeu " + Arrays.toString(play.atack));
                } catch (UnreadableException | java.lang.ClassCastException ex ) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                System.err.println("lean on");
            }
            
            if((msg.getPerformative() == ACLMessage.NOT_UNDERSTOOD)) { //O game master envia pedido de jogada
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd1 = new ServiceDescription();
                sd1.setType("Player ");
                template.addServices(sd1);
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    reply = new ACLMessage(ACLMessage.INFORM);
                    for(int i=0; i<result.length; ++i)
                        reply.addReceiver(result[i].getName());
                    reply.setContentObject(gs); 
                    send(reply);
                } catch(FIPAException |IOException e) {} } 
            }

        // metodo done
        public boolean done() {  return false; } } 

    
    // metodo setup
    protected void setup() {
        // cria behaviour
        playerBehaviour player = new playerBehaviour(this);
        gameMasterBehaviour gamemaster = new gameMasterBehaviour(this);
        
        // regista agente no DF
        DFAgentDescription dfd = new DFAgentDescription();
        
        AID id = new AID(playerId+"", AID.ISLOCALNAME );
        dfd.setName(getAID());
        System.out.println("Info: "+getAID().getName());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getName());
        if(this.playerType == PLAYERTYPE.MEGABOT) {
            sd.setType("Master");
            addBehaviour(gamemaster);}
        else{
            sd.setType("Player ");
            addBehaviour(player);}
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
    }// fim da classe PingPong

 }
