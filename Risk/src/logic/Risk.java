package logic;

import jade.wrapper.StaleProxyException;

public class Risk {
    public static GameState gs;
    public static void main(String[] args) throws StaleProxyException, InterruptedException {
        //System.err.println("main");
        gs = new GameState(4);
    }
    
}
