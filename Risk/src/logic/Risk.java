package logic;

import jade.wrapper.StaleProxyException;

public class Risk {
    public static GameState gs;
    public static void main(String[] args) throws StaleProxyException, InterruptedException {
        
        gs = new GameState(4);
    }
    
}
