package agent;


import gui.MapPanel;

public abstract class Bot extends Player implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public abstract void setMap(MapPanel map);
	public abstract boolean isHuman();
	
	public Bot(int id, String PlayerName, int type, int color, double optimist, double impulsive, double vingative) {
		super(id, PlayerName, type, color, optimist, impulsive, vingative);
		
		
	}
	
	
}
