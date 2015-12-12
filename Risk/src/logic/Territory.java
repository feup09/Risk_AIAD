package logic;

import java.awt.Polygon;
import java.util.ArrayList;

public class Territory extends Polygon{
	private int id;
	private int owner;
	private boolean ocupied;
	private String name;
	private int xName,yName,xTroops,yTroops;
	private int army;
	private int tempArmy;
	private boolean clicked;
	private boolean target;
	
	public enum TColor{NAMERICA, SAMERICA, AFRICA,EUROPE,ASIA,OCEANIA,BLUE,GREEN,RED,PINK,YELLOW,TORQ};
	public TColor color;
	
	public enum Continent {NAMERICA,SAMERICA,AFRICA,EUROPE,ASIA,OCEANIA};
	public Continent continent;
	
	private int[] xCoords;
	private int[] yCoords;
	private int points;
	
	
	private ArrayList<Territory> neighbours;
	
	public Territory(int id, String name)
	{
		this.id=id;
		this.name=name;
	}
	
	public Territory(int id,Continent continent,TColor color, String name,int xName, int yName, int xTroops, int yTroops,
			int[] xCoords,int[] yCoords, int points ){
		
		super(xCoords, yCoords, points);
		
		this.id = id;
		this.owner = 0;
		this.ocupied = false;
		this.continent = continent;
		this.color = color;
		this.name=name;
		this.xName = xName;
		this.yName = yName;
		this.xTroops = xTroops;
		this.yTroops = yTroops;
		this.xCoords = xCoords;
		this.yCoords = yCoords;
		this.points = points;
		
		//Defaults
		this.army = 0;
		this.tempArmy = 0;
		this.clicked = false;
		this.target = false;
		this.neighbours = new ArrayList<Territory>();
		
	}
	
	public void setNeighbour(Territory neighbor){
		this.neighbours.add(neighbor);
	}
	
	public ArrayList<Territory> getNeighbours(){
		
		return this.neighbours;
	}
	
	public int[] getAttackNeighbours(){
		
		int [] value;
		
		int total = this.neighbours.size();
		value = new int[total];
		
		for(int i = 0; i< this.neighbours.size(); ++i){
			value [i] = this.neighbours.get(i).id;
		}
		
		
		return value;
	}
	
	public void click(){
		this.clicked = !clicked;
	}
	
	public void setAsTarget(){
		this.target = true;
	}
	
	public boolean isTarget(){
		return this.target;
	}
	
	public boolean isClicked(){
		return this.clicked;
	}
	
	
	public void resetClickTarger(){
		this.clicked = false;
		this.target = false;
	}
	
	public int getId(){
		return this.id;
	}
	
	public int getXName(){
		return this.xName;
	}
	
	public int getYName(){
		return this.yName;
	}
	
	public int getXTroops(){
		return this.xTroops;
	}
	
	public int getYTroops(){
		return this.yTroops;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getArmy(){
		return this.army;
	}
	
	public int getTempArmy(){
		return this.tempArmy;
	}
	
	public void setColor(TColor c){
		this.color = c;
	}
	
	public int getOwner(){
		return this.owner;
	}
	
	public void setOwner(int playerId){
		this.owner = playerId;
	}
	
	public void setOcupied(){
		this.ocupied = true;
	}
	
	public boolean isOcupied(){
		return this.ocupied;
	}
	
	public void addArmy(int n){
		this.army += n;
	}
	
	public void removeArmy(int n){
		this.army -= n;
	}
	
	public void addTempArmy(int n){
		this.tempArmy += n;
	}
	
	public void removeTempArmy(int n){
		this.tempArmy -= n;
	}
	
	public void setTempArmy(int n){
		this.tempArmy = n;
	}
	
	public void deploy(){
		this.army += this.tempArmy;
		this.tempArmy = 0;
	}
}