package agent;

import java.awt.Component;

import logic.*;
import gui.MapPanel;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import gui.*;
public class Human extends Player  {
	
	MapPanel map;
	boolean activeM;
	
	public Human(int id, String PlayerName,int type, int color, double optimist, double impulsive, double vingative){
		super(id,PlayerName,type,color, 1, 1, 1);
		
		this.activeM = false;
		MouseListener listeners = new MouseListener();   
		
	}


	@Override
	public boolean isHuman() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void setMap(MapPanel map){
		this.map = map;
	}
	
	private class MouseListener extends MouseAdapter {

	    @Override
	    public void mousePressed(MouseEvent e) {
	        //This method is being used, working fine
	    }

	    @Override
	    public void mouseDragged(MouseEvent e) {
	        //This method is being used, working fine
	    }
	}
	
	public void processMapClick(Point p){
		
	}
	
	public int getFirstDeploy(GameState gs){
		
		return 0;
	}
	
	public ArrayList<Card> getTrade(GameState gs){
		ArrayList<Card> temp = new ArrayList<Card>();
		
		return temp;
	}
	
	public ArrayList<Integer> getAttack(GameState gs){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		return temp;
	}
	
	public ArrayList<Integer> getDeploy(GameState gs){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		return temp;
	}
	
	public ArrayList<Integer> getFortify(GameState gs){
		ArrayList<Integer> temp = new ArrayList<Integer>();
	
		return temp;
	}
	
	
}
