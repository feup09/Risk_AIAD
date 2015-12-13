package agent;


import java.util.ArrayList;

import gui.MapPanel;
import java.util.Hashtable;

import javax.lang.model.element.NestingKind;

import logic.Card;
import logic.GameState;
import logic.Territory;

public class IntermediateBot extends Bot {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public IntermediateBot(int id, String PlayerName, int type, int color, double optimist, double impulsive, double vingative) {
		super(id, PlayerName, type, color,optimist, impulsive, vingative);
                
		
		
	}
	
	
	public  void setMap(MapPanel map){
		
	}
        
        public int getFirstDeploy(GameState gs){
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
                return move[0];
            else return 0;
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
        
        
        for(int i=0;i<this.playerTerritories.size();i++)
        {
        	System.out.println("Meu "+ playerTerritories.get(i).getName());
        }
    	
        Hashtable<Integer,Hashtable<Integer,Integer>> compare = new Hashtable<Integer,Hashtable<Integer,Integer>>();
        System.out.println();
    	for(int i=0; i<playerTerritories.size();i++)
    	{   
    		System.out.println("---"+playerTerritories.get(i).getName()+" tem " + playerTerritories.get(i).getArmy());
    		if(playerTerritories.get(i).getArmy()>1)
    		{
    			if(this.optimist>=0 && this.optimist<=0.50)
            	{
            		compare.put(i,SearchBest(playerTerritories.get(i),3));
            	}
        		else if(this.optimist>0.50 && this.optimist<=0.75)
        		{
        			compare.put(i,SearchBest(playerTerritories.get(i),2));
        		}
        		else if(this.optimist>0.75 && this.optimist<=1)
        		{
        			compare.put(i,SearchBest(playerTerritories.get(i),1));
        		}
    		}
    		
    		
    	}
    	ArrayList<Integer> valuetoreturn=new ArrayList<Integer>();
    	int bestvalue=1000;
    	int bestindex=0;
    	int bestterritory=0;
    	if(compare.size()==0)
    	{
    		valuetoreturn.add(-1);
    		valuetoreturn.add(-1);
    		valuetoreturn.add(-1);
    		return valuetoreturn;
    	}
    	for(int index : compare.keySet())
    	{
    		
    		for(int key : compare.get(index).keySet())
    		{
    			System.out.println("paises a comparar "+ gs.getTerritories().get(key).getName());
    			if(compare.get(index).get(key)<bestvalue)
    			{	
    				
    				bestvalue=compare.get(index).get(key);
    				bestterritory=key;
    				bestindex=index;
    			}
    		}
    			
    		
    	}
    	
    	
    	int bestte=playerTerritories.get(bestindex).getId();
    	valuetoreturn.add(bestte);
    	valuetoreturn.add(bestterritory);
        valuetoreturn.add(gs.getTerritories().get(bestte).getArmy()-1);
         System.err.println("o pais "+gs.getTerritories().get(bestte).getName()+" ataca "+gs.getTerritories().get(bestterritory).getName());
    	return valuetoreturn;
    	
    }
    
    public Hashtable<Integer,Integer> SearchBest(Territory rootTerritory,int depth)
    {
    	
    	Hashtable<Integer, Integer> values = new Hashtable<Integer, Integer>();
    	for(int i=0;i<rootTerritory.getNeighbours().size();i++)
    	{   
    		
    		ArrayList<Integer> visited= new ArrayList<>();
    		Territory neighbour=rootTerritory.getNeighbours().get(i);
    		if(neighbour.getOwner()==this.getId())
    			continue;
    		System.out.println("Vizinho" + neighbour.getName());
    		values.put(neighbour.getId(),neighbour.getArmy() );
    		visited.add(neighbour.getId());
    		for(int j=0;j<neighbour.getNeighbours().size();j++)
        	{
    			if (depth==1)
    				break;
    			if (visited.contains(neighbour.getNeighbours().get(j).getName()) || neighbour.getNeighbours().get(j).getName() == rootTerritory.getName() || neighbour.getNeighbours().get(j).getOwner()==this.getId())
    				continue;
        		//System.out.println(neighbour.getName()+"----"+ values.get(neighbour.getName()));
        		values.put(neighbour.getId(),values.get(neighbour.getId())+neighbour.getNeighbours().get(j).getArmy());
        		visited.add(neighbour.getNeighbours().get(j).getId());
        		for(int k=0;k<neighbour.getNeighbours().get(j).getNeighbours().size();k++)
            	{
        			if (depth==2)
        				break;
        			if (visited.contains(neighbour.getNeighbours().get(j).getNeighbours().get(k).getId())|| neighbour.getNeighbours().get(j).getNeighbours().get(k).getName() == rootTerritory.getName() ||
        					 neighbour.getNeighbours().get(j).getNeighbours().get(k).getOwner()==this.getId())
        				continue;
            		values.put(neighbour.getId(),values.get(neighbour.getId())+neighbour.getNeighbours().get(j).getNeighbours().get(k).getArmy());
            		//System.out.println("2"+neighbour.getName()+"----"+ values.get(neighbour.getName()));

            	}
        	}
    	}
    	
    	int bestvalue=1000;
    	int bestterritory=0;
    	for(Integer key : values.keySet())
    	{
    		
    		if (values.get(key)<bestvalue)
    		{
    			bestvalue=values.get(key);
    			bestterritory=key;
    		}
    	}
    	
    	Hashtable<Integer, Integer>valuetoreturn=new Hashtable<Integer, Integer>();
    	valuetoreturn.put(bestterritory, bestvalue);
    	return valuetoreturn;
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
