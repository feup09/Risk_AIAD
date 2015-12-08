package logic;

import jade.BootProfileImpl;
import jade.core.MainContainer;
import jade.core.Profile;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import logic.PingPong;

public class Risk {
    public GameState gs;
    public void main(String[] args) throws StaleProxyException, InterruptedException {
        gs = new GameState(3);
    }
    
}
