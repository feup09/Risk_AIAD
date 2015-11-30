package logic;

import java.util.ArrayList;
import java.util.Random;

public abstract class Player {



	private int playerId;
	private boolean playing;
	public enum PLAYERTYPE {HUMAN,BOT1,BOT2,BOT3}
	public enum PLAYERCOLOR {GREEN,BLUE,RED,YELLOW,GREY,BLACK}
	PLAYERTYPE playertype;
	PLAYERCOLOR playercolor;
	int armies;

	//personality
	private boolean optimist;
	private boolean impulsive;
	private boolean vingative;

	private ArrayList<Territory> playerTerritories;
	private ArrayList<Card> playerCards;

	public abstract boolean isHuman();

	public Player(int id, PLAYERTYPE type, PLAYERCOLOR color ){

		playerId = id;
		playing = true;
		this.playerType = type;
		this.playerColor = color;
    this.setPersonality();

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

  public void setPersonality()
  {
			// Um boolean por default já é false
      //this.optimist = false;
      //this.impulsive = false;
      //this.vingative = false;
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

  public int[] throwDice(int number)
  {
      Random gerador = new Random();
      int dice[] = new int[number];
      for (int i=0; i< number; i++)
          dice[i] = (gerador.nextInt(6) +1); //1 a 6
      return dice;
  }

  public int scoreTrade() {
      int cards = 1;
      int score = 0;
      for (int i=0; i< this.playerCards.size(); i++)
          cards = cards * this.playerCards.get(i).value;

      if( (cards % 8) == 0)  score = 8;
      if( (cards % 27) == 0)  score = 27;
      if( (cards % 125) == 0)  score = 125;
      if( (cards % 30) == 0)  score = 30;

      return score;
  }

	public int scoreDeploy(GameState gs, int terr){
      int allyArmy = 0;
      int enemyArmy = 0;

      //Contar exércitos
      Territory aux = gs.territories.get(terr);
      if(aux.owner == this){                              //Estou num território deste player
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

	  public double scoreAtack(GameState gs, int fromTerr, int toTerr, int atk) {
	    //atk deve estar entre 1 e 3
	    int allyArmy=0;
	    int enemyArmy=0;
	    double probVictory;

	    Territory aux = gs.territories.get(toTerr);

	    if(aux.owner == this){                              //Entre os vizinhos do territorio atacado
	        for (int i=0; i< aux.neighbours.size(); i++) {  //Corro todos os vizinhos
	            if(aux.neighbours.get(i).owner == this)     //Vizinho é o player
	                allyArmy = allyArmy + aux.army-1;       //Conto tropas que podem atacar
	            if(aux.neighbours.get(i).owner != this)     //Vizinho não é o player
	                enemyArmy = enemyArmy + aux.army;       //Aumento inimigos
	        }
	  	 }

      //Se o jogador tiver pelo menos 2 tropas vai defender com 2 unidades
      if(aux.army > 1)
          probVictory = gs.getWin(atk, aux.army);
      else
          probVictory = gs.getWin(atk, 1);

      double probRetaliation = 0;
      if(aux.owner.vingative == true)
          probRetaliation = 1;

      int territoryRating = aux.territoryRating;

      double score = probVictory*territoryRating - probRetaliation*enemyArmy;
      return score;
	  }

	  public int scoreFortify(GameState gs, int fromTerr, int toTerr) {
      int enemyArmy = 0;

      //Contar exércitos
      Territory aux = gs.territories.get(toTerr);
      if(aux.owner == this){                              //Estou num território deste player
          for (int i=0; i< aux.neighbours.size(); i++) {  //Corro todos os vizinhos
              if(aux.neighbours.get(i).owner != this)     //Vizinho não é o player
                  enemyArmy = enemyArmy + aux.army;       //Aumento inimigos
          }
      }

      int displacedArmy = gs.territories.get(toTerr).army-1;
      //falta adicionar movesToFrontier;

      int score = displacedArmy + enemyArmy; //-movesToFrontier
      return score;
   }
 }
