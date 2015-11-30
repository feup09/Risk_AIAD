package logic;

import java.util.ArrayList;

public class Territory {
	public int id;
	public int army;
	public Player owner;
	public ArrayList<Territory> neighbours;
	public int territoryRating;
}
