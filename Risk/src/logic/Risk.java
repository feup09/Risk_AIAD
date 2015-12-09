package logic;

import jade.wrapper.StaleProxyException;

public class Risk {
    public GameState gs;
    public void main(String[] args) throws StaleProxyException, InterruptedException {
        gs = new GameState(3);
    }
    
}
