package game;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class Player extends Agent {

    public void sendMessage(ACLMessage msg) {
        msg.setLanguage("English");
        msg.setOntology("Prisoner Ontology");
        this.send(msg);
    }
}
