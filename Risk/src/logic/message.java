package logic;

import logic.GameState.Phase;

public class message implements java.io.Serializable{
    GameState gs = null;
    GameState.Phase pha= GameState.Phase.ATTACK;
    
    int idSender;
    int trade[];
    int deploy[];
    int atack[];
    int fortify[];

    public message(int idSender, int[] trade, int[] deploy, int[] atack, int[] fortify, Phase phase, GameState gs) {
        this.idSender = idSender;
        this.trade = trade;
        this.deploy = deploy;
        this.atack = atack;
        this.fortify = fortify;
        
        this.gs = gs;
        this.pha = phase;
    }
 

}
