package gui;
import logic.*;
import logic.GameState.Phase;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Rectangle;


public class MapPanel extends JPanel {
	
	ArrayList<Territory> pTerritories;
	GameState gs;
	
	
	
	private static final long serialVersionUID = 1L;
	private static final Font Font_pequena = new Font("Arial", Font.BOLD, 15);
	private static final Font Font_media = new Font("Arial", Font.BOLD, 30);
	private static final Font Font_grande = new Font("Arial", Font.BOLD, 40);
	private static final Font button1 = new Font("Arial", Font.BOLD, 25);
	private static final Font button2 = new Font("Arial", Font.PLAIN, 15);
	private static final Font title = new Font("Arial", Font.BOLD, 15);
	private static final Font normal = new Font("Arial", Font.PLAIN, 15);
	private static final Font defaultFont = new Font("Arial", Font.PLAIN, 10);
	
	private BufferedImage background;
	
	final static Color NAMERICA =  new Color(236,151,64,255);
	final static Color SAMERICA =  new Color(241,193,88,255);
	final static Color EUROPE =  new Color(118,112,148,255);
	final static Color AFRICA =  new Color(201,152,111,255);
	final static Color ASIA =  new Color(170,178,64,255);
	final static Color OCEANIA =  new Color(209,91,163,255);
	
	final static Color RED =  new Color(218,21,21,255);
	final static Color SRED =  new Color(232,49,49,200);
	final static Color GREEN =  new Color(88,175,63,255);
	final static Color SGREEN =  new Color(88,175,63,150);
	final static Color BLUE =  new Color(7,108,229,255);
	final static Color SBLUE =  new Color(7,108,229,100);
	final static Color YELLOW =  new Color(242,194,58,255);
	final static Color SYELLOW = new Color(242,194,58,100);
	final static Color PINK =  new Color(216,104,174,255);
	final static Color SPINK =  new Color(216,104,174,100);
	final static Color TORQ =  new Color(80,201,178,255);
	final static Color STORQ =  new Color(80,201,178,10);
	final static Color GREY =  Color.GRAY;
	
	final static Color BLACK =  Color.BLACK;
	
	public Rectangle buttonPlus;
	public Rectangle buttonMinus;
	public Rectangle buttonTop; 
	public Rectangle buttonBottom; 
	private int buttonClicked;

	
	 public MapPanel(GameState gs){
		  super();
		  this.gs = gs;
		  this.pTerritories = gs.getTerritories();
		  
		  this.buttonPlus = new Rectangle(1180, 370, 40, 40);
		  this.buttonMinus = new Rectangle(1250, 370, 40, 40);
		  this.buttonTop = new Rectangle(1185, 435, 110, 40);
		  this.buttonBottom = new Rectangle(1185, 495, 110, 40);
		  this.buttonClicked = 1;
		  
		  
		  /* try {                
		      background = ImageIO.read(new File("src/images/background.png"));            
		      
		 } catch (IOException ex) {
		     System.out.println(ex.toString());
		 }*/
		  
	  }
	 
	 public void initGameComponents(){
		  try {                
	          background = ImageIO.read(new File("src/images/background.png"));            
	          
	     } catch (IOException ex) {
	         System.out.println(ex.toString());
	     }
		  
		  this.gs = new GameState();
		  pTerritories = gs.getTerritories();
		  
	  }
	 
	 
	  public void paint(Graphics g) {
		  super.paintComponent(g);
		  
		  //set background
		  //g.drawImage(background, 0, 0, 1100,750,null);
		  
		  //draw lines
		  drawLines(g);
		  
		  //fill countries
		  fillCountries(g);
		  
		  //draw borders
		  drawBorders(g);
		  
		  //draw names
		  drawNames(g);
		  
		  //draw troops
		  drawTroops(g);
		  
		  
		  //draw adicional stuff;
		  //drawMessage(g);
		  drawSide(g);
		  drawPlayers(g);
		  drawContinentChart(g);
		  drawPhase(g);
		  drawButtons(g);
		  
		  /*
		  g.setColor(RED);
		  drawLineEnds(g);
		  g.setColor(BLACK);
		  */
		  
	  }
	  
	  public void drawPhase(Graphics g){
		  String s = " ";
		  if(gs.getPhase()== Phase.TRADE){
			  s = "Trade";
		  }
		  else if(gs.getPhase()== Phase.DEPLOY || gs.getPhase()== Phase.FIRSTDEPLOY){
			  s = "Deploy";
		  }
		  else if(gs.getPhase()== Phase.ATTACK ||gs.getPhase()== Phase.ATTACK2){
			  s = "Attack";
		  }
		  else if(gs.getPhase()== Phase.REINFORCE || gs.getPhase()== Phase.REINFORCE2){
			  s = "Reinforce";
		  }
		  
		  g.drawString(s, 1200, 20);
	  }
	  
	  public void drawSide(Graphics g){
		  
		  if(gs.getPhase() == Phase.ATTACK2){
			  
			  g.setFont(Font_grande);
			  g.setColor(this.getPlayerColor(gs.getPlayerTurn()));
			  g.drawString(gs.getAttackingTroops()+"",1200,300);
			  g.setColor(BLACK);
			  g.setFont(normal);
		  }
		  else if(gs.getPhase() == Phase.REINFORCE2){
			  
			  g.setFont(Font_grande);
			  g.setColor(this.getPlayerColor(gs.getPlayerTurn()));
			  g.drawString(gs.getTerritories().get(gs.getAttackingTerritory()).getTempArmy()+"",1200,300);
			  g.setColor(BLACK);
			  g.setFont(normal);
		  }
		  else if(gs.getPhase() == Phase.DEPLOY){
			  
			  g.setFont(Font_grande);
			  g.setColor(this.getPlayerColor(gs.getPlayerTurn()));
			  g.drawString(gs.getPlayers().get(gs.getPlayerTurn()).getTempArmy()+"",1200,300);
			  g.setColor(BLACK);
			  g.setFont(normal);
		  }
		  else if(gs.getPhase() == Phase.FIRSTDEPLOY){
			  
			  g.setFont(Font_grande);
			  g.setColor(this.getPlayerColor(gs.getPlayerTurn()));
			  g.drawString(gs.getPlayers().get(gs.getPlayerTurn()).getArmy()+"",1200,300);
			  g.setColor(BLACK);
			  g.setFont(normal);
		  }
		  
	  }
	  
	  public void drawMessage(Graphics g){
		  if(gs.getPhase()== Phase.START){
			  System.out.println("LETS START RISK");
		  }
		  else if(gs.getPhase()== Phase.ATTACK3){
			  g.setColor(Color.WHITE);
			  g.fillRect(150, 75, 800, 600);
			  g.setColor(BLACK);
			  g.drawRect(150, 75, 800, 600);
			  String aP, dP;
			  g.setFont(Font_grande);
			  Color c1 = this.getPlayerColor(this.pTerritories.get(gs.getAttackingTerritory()).getOwner());
			  g.setColor(c1);
			  aP = gs.getPlayers().get(this.pTerritories.get(gs.getAttackingTerritory()).getOwner()).getName() + "";
			  g.drawString(aP, 230, 120);
			  g.setColor(BLACK);
			  g.drawString("Attacks", 450, 120);
			  Color c2 = this.getPlayerColor(this.pTerritories.get(gs.getDefendingTerritory()).getOwner());
			  g.setColor(c2);
			  dP = gs.getPlayers().get(this.pTerritories.get(gs.getAttackingTerritory()).getOwner()).getName() + "";
			  g.drawString(dP, 700, 120);
			  
			  //draw dices
			  g.setColor(BLACK);
			  g.setFont(normal);
			  
			  int x = 350;
			  int y = 180;
			  int x2 = 600;
			  
			  for(int i = 0; i < gs.attackerDices.size();++i){
				  int dice = gs.attackerDices.get(i);
				  this.drawDice(dice, x, y +50 *i +5 *i, g);
			  }
			  
			  for(int i = 0; i < gs.defenderDices.size();++i){
				  int dice = gs.defenderDices.get(i);
				  this.drawDice(dice, x2, y +50 *i +5 *i, g);
			  }
			  g.setFont(Font_media);
			  g.setColor(c1);
			  g.drawString(aP + " Loses " + gs.attackResult.get(0)+ " troops", 300, 550);
			  g.setColor(BLACK);
			  g.setColor(c2);
			  g.drawString(dP + " Loses " + gs.attackResult.get(1)+ " troops", 300, 600);
			  
		  }
	  }
	  
	  public void drawNames(Graphics g){
		  for (int i = 0; i < pTerritories.size(); i++) {
			  Territory t = pTerritories.get(i);
			  g.drawString(t.getName(),t.getXName(),t.getYName());
	      }
	  }
	  
	  public void drawTroops(Graphics g){
		  
		  if(gs.getPhase() == Phase.DEPLOY || gs.getPhase() == Phase.FIRSTDEPLOY){
			  for (int i = 0; i < pTerritories.size(); i++) {
				  Territory t = pTerritories.get(i);
				  String troops = t.getArmy()+ t.getTempArmy()+"";
				  g.drawString(troops,t.getXTroops(),t.getYTroops());
		      }
		  }
		  else{
		  
			  for (int i = 0; i < pTerritories.size(); i++) {
				  Territory t = pTerritories.get(i);
				  String troops = t.getArmy()+ "";
				  g.drawString(troops,t.getXTroops(),t.getYTroops());
		      }
		  }
	  }
	  
	  public void drawLines(Graphics g){
		  Graphics2D g2 = (Graphics2D) g;
		  g2.setStroke(new BasicStroke(2));
		  
		  
		  g.drawLine(0,80,27,80);
		  g.drawLine(271,86,323,82);
		  g.drawLine(223,150,323,82);
		  g.drawLine(294,123,323,82);
		  g.drawLine(390,106,424,121);
		  g.drawLine(463,156,496,175);
		  g.drawLine(449,221,463,156);
		  g.drawLine(449,221,496,175);
		  g.drawLine(502,219,496,175);
		  g.drawLine(502,219,449,221);
		  g.drawLine(442,260,443,277);
		  g.drawLine(515,362,511,367);
		  g.drawLine(563,367,571,392);
		  g.drawLine(396,466,419,468);
		  g.drawLine(636,458,651,454);
		  g.drawLine(669,543,672,620);
		  g.drawLine(620,663,664,629);
		  g.drawLine(956,214,986,243);
		  g.drawLine(939,258,986,243);
		  g.drawLine(909,485,900,460);
		  g.drawLine(916,495,948,500);
		  g.drawLine(970,555,948,500);
		  g.drawLine(945,569,948,500);
		  g.drawLine(945,569,932,558);
		  g.drawLine(1046,80,1100,80);
		  
		  g2.setStroke(new BasicStroke(1));
		  
	  }
	  
	  public void fillCountries(Graphics g){
		  g.setColor(BLACK);
		  
		  
		  for (int i = 0; i < pTerritories.size(); i++) {
					  
			  if(pTerritories.get(i).isClicked()){
				  Color c = getTerritoryColor(pTerritories.get(i));
				  g.setColor(c);  
			  }
			  else if(pTerritories.get(i).isTarget()){
				  g.setColor(GREY);
			  }
			  else{
				  Color c = getTerritoryColor(pTerritories.get(i));
				  g.setColor(c);
			  }
			  g.fillPolygon(pTerritories.get(i));
			  g.setColor(BLACK);
	      }
		  g.setColor(BLACK);
	  }
	  
	  public void drawBorders(Graphics g){
		  g.setColor(BLACK);
		  for (int i = 0; i < pTerritories.size(); i++) {
			  if(pTerritories.get(i).isTarget()){
				  g.setColor(YELLOW);
			  }
			  g.drawPolygon(pTerritories.get(i));
			  g.setColor(BLACK);
	      }
	  }
	  
	  public void drawLineEnds(Graphics g){
		  g.fillOval(266,81,10,10);
	  }
	  
	  public void drawPlusMinus(Graphics g){
		  if(this.buttonClicked ==1){
			g.setColor(GREEN);
		  }
		  g.drawRect(1180, 370, 40, 40);
		  g.setColor(BLACK);
		  
		  if(this.buttonClicked ==2){
			g.setColor(GREEN);
		  }
		  g.drawRect(1250, 370, 40, 40);
		  g.setColor(BLACK);
		  
		  g.setFont(button1);
		  g.drawString("+", 1192, 400);
		  g.drawString("-", 1265, 398);
		  g.setFont(normal);
	  }
	  
	  public void drawTop(Graphics g, String s){
		  
		  g.setColor(BLACK);
		  g.drawRect(1185, 435, 110, 40);
		  
		  g.setFont(button2);
		  g.drawString(s, 1215, 460);
		  g.setFont(normal);
		  
	  }
	  
	  
	  public void drawBottom(Graphics g, String s){
		  
		  g.setColor(BLACK);
		  g.drawRect(1185, 495, 110, 40);
		  
		  g.setFont(button2);
		  g.drawString(s, 1215, 520);
		  g.setFont(normal);
	  }
	  
	  public void drawButtons(Graphics g){
		  
		  if(gs.getPhase()==Phase.START){
			  drawTop(g,"Start");
		  }
		  else if(gs.getPhase()==Phase.TRADE){
			  drawTop(g,"Trade");
			  drawBottom(g,"Done");
		  }
		  else if(gs.getPhase()==Phase.DEPLOY || gs.getPhase() == Phase.FIRSTDEPLOY){
			  drawTop(g,"Reset");
			  drawBottom(g,"Done");
			  drawPlusMinus(g);
		  }
		  else if(gs.getPhase()==Phase.ATTACK){
			  drawBottom(g,"Done");
		  }
		  else if(gs.getPhase()==Phase.ATTACK2){
			  drawTop(g,"Attack");
			  drawBottom(g,"Done");
			  drawPlusMinus(g);
		  }
		  else if(gs.getPhase()==Phase.ATTACK3){
			  drawBottom(g,"OK");
		  }
		  else if(gs.getPhase()==Phase.REINFORCE){
			  drawBottom(g,"Done");
		  }
	  }
	  
	  public void drawButtons2(Graphics g){
		
		  if(this.buttonClicked ==1){
			g.setColor(GREEN);
		  }
		  g.drawRect(1180, 370, 40, 40);
		  g.setColor(BLACK);
		  
		  if(this.buttonClicked ==2){
			g.setColor(GREEN);
		  }
		  g.drawRect(1250, 370, 40, 40);
		  g.setColor(BLACK);
			
		  g.drawRect(1185, 495, 110, 40);
		  g.drawRect(1185, 435, 110, 40);
		  g.setFont(button1);
		  g.drawString("+", 1192, 400);
		  g.drawString("-", 1265, 398);
		  g.setFont(button2);
		  g.drawString("DONE", 1215, 460);
		  g.setFont(button2);
		  g.drawString("RESET", 1215, 520);
		  g.setFont(normal);
	
		 
	  }
		
		public void drawPlayers(Graphics g){
			g.setFont(title);
			g.drawString("Players", 1200,100 );
		  
			int startX = 1170;
			int startY = 130;
			for(int i = 0; i < this.gs.getNPlayers();++i){
				if(this.gs.getPlayers().get(i).isPlaying()){
					
					if(gs.getPlayers().get(i).getId() == gs.getPlayerTurn()){
						g.setColor(this.getPlayerColor(i));
						g.fillRect(startX-20, startY-10, 10, 10);
						g.setColor(BLACK);
					}
					
					g.setFont(normal);
					g.setColor(this.getPlayerColor(i));
					g.drawString("P"+ (this.gs.getPlayers().get(i).getId()+1) + "- " + this.gs.getPlayers().get(i).getName() +" - " + this.gs.getPlayers().get(i).getTerritories().size() + " Territories", startX, startY);
					startY += 20;
					g.setColor(BLACK);
					g.setFont(normal);
				}
				else{
					g.setFont(normal);
					g.drawString("P"+ (this.gs.getPlayers().get(i).getId()+1) + "- " + this.gs.getPlayers().get(i).getName() +" GAME OVER", startX, startY);
					startY += 20;
					g.setFont(normal);
					
				}
			}
		}
		
		public void drawContinentChart(Graphics g){
		
			g.setFont(title);
			
			int y = 590;
			  g.drawString("Continent", 1180, y);
			  g.drawString("Bonus", 1285, y);
			  g.setFont(normal);
			  g.drawString("Asia", 1200, y+20);
			  g.drawString("7", 1300, y+20);
			  g.drawString("North America", 1170, y+40);
			  g.drawString("5", 1300, y+40);
			  g.drawString("Europe", 1190, y+60);
			  g.drawString("5", 1300, y+60);
			  g.drawString("Africa", 1195, y+80);
			  g.drawString("3", 1300, y+80);
			  g.drawString("South America", 1170, y+100);
			  g.drawString("2", 1300, y+100);
			  g.drawString("Oceania", 1190, y+120);
			  g.drawString("2", 1300, y+120);
			  
			  g.drawLine(1165, y-20, 1165, y+130);
			  g.drawLine(1270, y-20, 1270, y+130);
			  g.drawLine(1340, y-20, 1340, y+130);
			  g.drawLine(1165, y-20, 1340, y-20);
			  g.drawLine(1165, y+130, 1340, y+130);
		}
	  
	  
	  
	  public Color getTerritoryColor(Territory t){
		  Territory.TColor c = t.color;
		  if( c == Territory.TColor.NAMERICA){
			  return NAMERICA;
		  }
		  else if( c == Territory.TColor.SAMERICA){
			  return SAMERICA;
		  }
		  else if( c == Territory.TColor.EUROPE){
			  return EUROPE;
		  }
		  else if( c == Territory.TColor.ASIA){
			  return ASIA;
		  }
		  else if( c == Territory.TColor.OCEANIA){
			  return OCEANIA;
		  }
		  else if( c == Territory.TColor.AFRICA){
			  return AFRICA;
		  }
		  else if( c == Territory.TColor.BLUE){
			  if(t.isClicked())
				  return SBLUE;
			  else
				  return BLUE;
		  }
		  else if( c == Territory.TColor.GREEN){
			  if(t.isClicked())
				  return SGREEN;
			  else
				  return GREEN;
		  }
		  else if( c == Territory.TColor.RED){
			  if(t.isClicked())
				  return SRED;
			  else
				  return RED;
		  }
		  else if( c == Territory.TColor.PINK){
			  if(t.isClicked())
				  return SPINK;
			  else
				  return PINK;
		  }
		  else if( c == Territory.TColor.YELLOW){
			  if(t.isClicked())
				  return SYELLOW;
			  else
				  return YELLOW;
		  }
		  else if( c == Territory.TColor.TORQ){
			  if(t.isClicked())
				  return STORQ;
			  else
				  return TORQ;
		  }
		  else{
			  return BLACK;
		  }
	  }
	  
	  
	  
	  public Color getPlayerColor(int p){
		  Territory.TColor c = gs.getPlayers().get(p).getPlayerColor();
		  if( c == Territory.TColor.BLUE){
			  return BLUE;
		  }
		  else if( c == Territory.TColor.GREEN){
			  return GREEN;
		  }
		  else if( c == Territory.TColor.RED){
			  return RED;
		  }
		  else if( c == Territory.TColor.YELLOW){
			  return YELLOW;
		  }
		  else if( c == Territory.TColor.PINK){
			  return PINK;
		  }
		  else if( c == Territory.TColor.TORQ){
			  return TORQ;
		  }
		  else{
			  return BLACK;
		  }
	}
	  
	  
	public void setButtonClicked(int b){
		this.buttonClicked = b;
	}
	
	public int getButtonClicked(){
		return this.buttonClicked;
	}
	
	public void drawDice(int num, int x, int y, Graphics g){
		
		g.setColor(BLACK);
		g.drawRect(x, y, 50, 50);
		g.setFont(Font_grande);
		g.drawString(num+"", x+15, y+40);
		
	}
	  
	  
  
  
}
    