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

    public static void main(String[] args) throws StaleProxyException, InterruptedException {
        Runtime runtime = Runtime.instance();
        String[] arguments = new String[1];
        arguments[0] = "-gui";
        Profile profile = new BootProfileImpl(arguments);
        ContainerController mainContainer = runtime.createMainContainer(profile);
        
        String[] type = new String[2];
        type[0] = "ping";
        type[1] = "12";
        AgentController agc = mainContainer.createNewAgent("pi", "logic.PingPong", arguments);
        agc.start();
        
        Thread.sleep(1000);
        
        type[0] = "pong";
        type[1] = "15";
        AgentController agc2 = mainContainer.createNewAgent("po", "logic.PingPong", arguments);
        agc2.start();
        
    }
    
}
