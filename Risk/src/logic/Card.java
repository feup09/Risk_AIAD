package logic;

import logic.Card.CardFigure ;

public class Card implements java.io.Serializable {
	private CardFigure figure;
	private int territory;
	public enum CardFigure {HORSE,SOLDIER,CANNON};
	

	public Card(CardFigure figure,int territory){

		this.figure=figure;
		this.territory=territory;

	}
	
	public CardFigure getFigure(){
		return this.figure;
	}
	
	public int getTerritory(){
		return this.territory;
	}
	
}
