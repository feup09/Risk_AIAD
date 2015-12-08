package logic;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

// classe do agente
public class PingPong extends Agent {
   
    public int armyPi =0;
    public int armyPo =0;
     /**/
   // classe do behaviour
   class PingPongBehaviour extends SimpleBehaviour {
      private int n = 0;

        // construtor do behaviour
        public PingPongBehaviour(Agent a) {
          super(a); 
          }

        // metodo action
        public void action() {
           ACLMessage msg = blockingReceive();
          if(msg.getPerformative() == ACLMessage.PROPOSE) {
              System.out.println(++n + " " + getLocalName() + ": recebi " + msg.getContent());
              // cria resposta
              ACLMessage reply = msg.createReply();
              // preenche conteedo da mensagem
              String response = msg.getContent();
              //armyPo = Integer.parseInt(response.substring(6));
              System.err.println(msg.getSender().getName());
              System.err.println("armypo: " + armyPo);
              System.err.println("armyPi: " + armyPi);
              if(response.contains("Ally? ") && (armyPi >= armyPo)) {
                  reply.setContent("Sure."); 
              }
              else reply.setContent("Nope"); 
              // envia mensagem
              send(reply);
           }
        }

        // metodo done
        public boolean done() {
           return n==1;
        }

   }   // fim da classe PingPongBehaviour


   // metodo setup
   protected void setup() {
      String tipo = "";
      // obtem argumentos
      Object[] args = getArguments();
      if(args != null && args.length > 1) {
         tipo = (String) args[0];
      } else {
         System.out.println("Nao especificou o tipo");
      }
      
      // regista agente no DF
      DFAgentDescription dfd = new DFAgentDescription();
      dfd.setName(getAID());
      ServiceDescription sd = new ServiceDescription();
      sd.setName(getName());
      sd.setType("Agente " + tipo);
      dfd.addServices(sd);
      try {
         DFService.register(this, dfd);
      } catch(FIPAException e) {
         e.printStackTrace();
      }

      // cria behaviour
      PingPongBehaviour b = new PingPongBehaviour(this);
      addBehaviour(b);
      /**/if(tipo.equals("ping")) {
          armyPi = Integer.parseInt((String) args[1]);
      }
      // toma a iniciativa se for agente "pong"
      if(tipo.equals("pong")) {
         //armyPo = Integer.parseInt((String) args[1]);
         // pesquisa DF por agentes "ping"
         DFAgentDescription template = new DFAgentDescription();
         ServiceDescription sd1 = new ServiceDescription();
         sd1.setType("Agente ping");
         template.addServices(sd1);
         try {
            DFAgentDescription[] result = DFService.search(this, template);
            // envia mensagem "pong" inicial a todos os agentes "ping"
            ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
            for(int i=0; i<result.length; ++i) {
               msg.addReceiver(result[i].getName());
            }
            msg.setContent("Ally? " + (String) args[1]);
            send(msg);
         } catch(FIPAException e) { e.printStackTrace(); }
      }

   }   // fim do metodo setup

   // metodo takeDown
   protected void takeDown() {
      // retira registo no DF
      try {
         DFService.deregister(this);  
      } catch(FIPAException e) {
         e.printStackTrace();
      }
   }

}   // fim da classe PingPong

