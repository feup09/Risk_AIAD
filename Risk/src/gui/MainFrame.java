package gui;
import logic.*;
import agent.*;
import jade.wrapper.*;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import jade.core.Profile;
import jade.core.Runtime;
import jade.BootProfileImpl;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import logic.GameState.Phase;

public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private boolean mouseIsActive;
	
	private MapPanel map;
	private BottomPanel bottom;
	private GameState gs;
	private Master master;

	
	public MainFrame() throws StaleProxyException, InterruptedException{
		
		generateGameComponents();
		createAndShowGui();
		
		master = new Master(gs,map);
		setHumanInterface();
		registerPlayers();
		
		gs.deliverTerritories();
		gs.deliverArmies();
		
		map.repaint();
		master.start();
		
		
		
	}
	
	
	public void setHumanInterface(){
		for(int i = 0; i < gs.getPlayers().size();++i){
			if(gs.getPlayers().get(i).getPlayerType() == Player.PlayerType.HUMAN){
				gs.getPlayers().get(i).setMap(map);
			}
			
		}
	}
	
	
	public void registerPlayers() throws StaleProxyException, InterruptedException{
		Runtime runtime = Runtime.instance();
		String arguments[] = new String [1];
		arguments[0] = "-gui";
		Profile profile = new BootProfileImpl(arguments);
		ContainerController mainContainer = runtime.createMainContainer(profile);
		
		//Game Master
        AgentController agc = mainContainer.acceptNewAgent("Master", master);
        agc.start();

        Thread.sleep(10);
        /*
        AgentController man = mainContainer.acceptNewAgent(gs.getPlayers().get(0).PlayerName,gs.getPlayers().get(0));
        man.start();
        */
        Thread.sleep(10);
       for(int i = 0; i < gs.getPlayers().size();++i){
    	   mainContainer.acceptNewAgent(gs.getPlayers().get(i).PlayerName,gs.getPlayers().get(i)).start();
        	System.out.println("created agent");
        	Thread.sleep(10);
        }
 
		
	}
	
	
	public void generateGameComponents(){
		this.gs = new GameState();
	}
	
	
	public void createAndShowGui(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        this.map = new MapPanel(gs);
        map.setPreferredSize(new Dimension(1400,750));
		this.add(map, BorderLayout.CENTER);
		
		
		
		
		this. bottom = new BottomPanel(gs);
        bottom.setPreferredSize(new Dimension(200, 200));
        this.add(bottom, BorderLayout.SOUTH);
        
        this.pack();
        
        this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	
	
	
	//game rules stuff
	public void distributeCountries(){
		
		
	}
	
	public void deploy(){
		
	}
	
	public void attack(){
		
		
	}
	
	
	public boolean playerGameOver(){
		
		return false;
	}
	
	public boolean globalGameOver(){
		
		return false;
	}
	
	
	
	//main
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

}
