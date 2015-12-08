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

    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime = Runtime.instance();
        String[] arguments = new String[1];
        arguments[0] = "-gui";
        Profile profile = new BootProfileImpl(arguments);
        ContainerController mainContainer = runtime.createMainContainer(profile);
        
        
        arguments[0] = "ping";
        AgentController agc = mainContainer.createNewAgent("pi1", "logic.PingPong", arguments);
        agc.start();
        
        arguments[0] = "pong";
        AgentController agc2 = mainContainer.createNewAgent("po1", "logic.PingPong", arguments);
        agc2.start();
        
    }
    
}
