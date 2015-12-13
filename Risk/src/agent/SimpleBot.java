package agent;

import gui.MapPanel;
import java.util.ArrayList;
import logic.Card;
import logic.GameState;
import logic.Territory;

public class SimpleBot extends Bot {
	private static final long serialVersionUID = 1L;
	
	
	public SimpleBot(int id, String PlayerName, int type, int color, double optimist, double impulsive, double vingative) {
		super(id, PlayerName, type, color,optimist, impulsive, vingative);
                
		
		
	}
	
	
	public  void setMap(MapPanel map){
		
	}
    
    
    
    
	public int getFirstDeploy(GameState gs){
		
		System.out.println("Bot fd");
                
		return playerTerritories.get(0).getId();
	}
	
	public ArrayList<Card> getTrade(GameState gs){
		ArrayList<Card> temp = new ArrayList<Card>();
		
		System.out.println("Bot trade");
		
		return temp;
	}
	

	
	@Override
	public  boolean isHuman(){
		return false;
	}
	
    public int scoreDeploy(GameState gs, int terr){
        int allyArmy = 0;
        int enemyArmy = 0;

        //Contar exércitos
        System.out.print("n players: "+gs.getNPlayers());
        Territory aux = gs.getTerritories().get(terr);
        if(aux.getOwner()== this.getId()){                                  //Estou num território deste player
          for (int i=0; i< aux.getNeighbours().size(); i++) {               //Corro todos os vizinhos
              if(aux.getNeighbours().get(i).getOwner()== this.getId())      //Vizinho é o player
                  allyArmy = allyArmy + aux.getArmy();                      //Aumento aliados
              if(aux.getNeighbours().get(i).getOwner() != this.getId())     //Vizinho não é o player
                  enemyArmy = enemyArmy + aux.getArmy();                    //Aumento inimigos
          }
        }
        int score = - allyArmy + enemyArmy;
        return score;
    }

    public double scoreAtack(GameState gs, int fromTerr, int toTerr) {
        int allyArmy=0;
        int enemyArmy=0;
        double probVictory;

        Territory aux = gs.getTerritories().get(toTerr);

        if(aux.getOwner() == this.getId()){                                   //Entre os vizinhos do territorio atacado
            for (int i=0; i< aux.getNeighbours().size(); i++) {               //Corro todos os vizinhos
                if(aux.getNeighbours().get(i).getOwner() == this.getId())     //Vizinho é o player
                    allyArmy = allyArmy + aux.getArmy()-1;                    //Conto tropas que podem atacar
                else                                                          //Vizinho não é o player
                    enemyArmy = enemyArmy + aux.getArmy();                    //Aumento inimigos
            }
        }

        int nAtk = gs.getTerritories().get(fromTerr).getArmy() - 1;
        if (nAtk>2) nAtk = 2;
        //Se o jogador tiver pelo menos 2 tropas vai defender com 2 unidades
        if(aux.getArmy() > 1)
            probVictory = gs.getWin(nAtk, 1);
        else
            probVictory = gs.getWin(nAtk, 0);

        double probRetaliation = 0;
            probRetaliation = gs.getPlayers().get(aux.getOwner()).vingative;

        double score = probVictory*allyArmy - probRetaliation*enemyArmy;
        return score;
    }

    public int scoreFortify(GameState gs, int fromTerr, int toTerr) {
        int enemyArmy = 0;
        int movesToFrontier = 100; // Se a fronteira ficar a mais de 1 movimento ignoro a jogada

        //Contar exércitos
        Territory aux = gs.getTerritories().get(toTerr);
        if(aux.getOwner() == this.getId()){                                     //Estou num território deste player
            for (int i=0; i< aux.getNeighbours().size(); i++) {                 //Corro todos os vizinhos
                if(aux.getNeighbours().get(i).getOwner() != this.getId())  {   //Vizinho não é o player
                    enemyArmy = enemyArmy + aux.getArmy();                      //Aumento inimigos
                    movesToFrontier =movesToFrontier*0;
                  }
            }
        }

        int displacedArmy = gs.getTerritories().get(toTerr).getArmy()-1;      

        int score = displacedArmy + enemyArmy - movesToFrontier;
        return score;
    }
    
    public ArrayList<Integer> getDeploy(GameState gs){
            int move[]= {0}, newScore;
            ArrayList<Integer> result = new ArrayList<>();
            int bestScore= -100;
            Territory aux;
            for(int i=0; i<this.playerTerritories.size(); i++) { //Corro o vetor de territórios que em pertencem
                aux = this.playerTerritories.get(i);
                System.out.println("aux "+ aux.getId());
                newScore = this.scoreDeploy(gs, aux.getId());   //Pontuo a colocação de exércitos em cada território
                if(bestScore < newScore) {                      // Atualizo a informação da melhor jogada
                    bestScore = newScore;
                    move[0] = aux.getId();
                }
            }
            result.add(move[0]);
            if (bestScore > -100)
                return result;
            else return null;
    }
    
    public ArrayList<Integer> getAttack(GameState gs){
        int move[]= new int[3];
        ArrayList<Integer> result = new ArrayList<>();
        double bestScore= -100;
        double newScore;
        Territory from = null, to= null;
        for(int i=0; i<this.playerTerritories.size(); i++) {                //Corro o vetor de territórios que em pertencem
            from = this.playerTerritories.get(i);
            for(int j=0; j<from.getNeighbours().size(); j++) {              //Corro o vetor de territórios adjacente a esse território
                to = from.getNeighbours().get(j);
                newScore = this.scoreAtack(gs, from.getId(), to.getId());   //Pontuo o ataque do territorio "from"  para "to"
                if(bestScore < newScore) {                                  // Atualizo a informação da melhor jogada
                    bestScore = newScore;
                    move[0] = from.getId();
                    move[1] = to.getId();
                    move[2] = from.getArmy()-1;
                }
            }
        }
        result.add(move[0]);
        result.add(move[1]);
        result.add(move[2]);
        if ((bestScore > -100) && (this.impulsive>0.5) || (bestScore>0 && !(this.impulsive>0.5)) )
            return result;
        else return null;
    }

    public ArrayList<Integer> getFortify(GameState gs){
        int move[] = new int[3];
        ArrayList<Integer> result = new ArrayList<>();
        double bestScore= -100;
        double newScore;
        Territory from = null, to= null;
        for(int i=0; i<this.playerTerritories.size(); i++) { //Corro o vetor de territórios que em pertencem
            from = this.playerTerritories.get(i);
            for(int j=0; j<from.getNeighbours().size(); j++) { //Corro o vetor de territórios adjacente a esse território
                to = from.getNeighbours().get(j);
                newScore = this.scoreFortify(gs, from.getId(), to.getId()); //Pontuo o ataque do territorio "from"  para "to"
                if(bestScore < newScore) {  // Atualizo a informação da melhor jogada
                    bestScore = newScore;
                    move[0] = from.getId();
                    move[1] = to.getId();
                    move[2] = from.getArmy()-1;
                }
            }
        }
        result.add(move[0]);
        result.add(move[1]);
        result.add(move[2]);
        if (bestScore > -100 )
            return result;
        else return null;
    }
}
