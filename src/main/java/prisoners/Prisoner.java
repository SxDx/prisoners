package prisoners;

import game.MessageType;
import game.Player;
import interfaces.PrisonerInterface;
import jade.core.AID;
import jade.lang.acl.ACLMessage;


public abstract class Prisoner extends Player implements PrisonerInterface {

    @Override
    protected void setup() {
        super.setup();
        System.out.println("Prisoner::Setup::" + this.getName());
    }

    @Override
    public void sendCooperate() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent(MessageType.Cooperate.toString());
        this.sendMessage(msg);
    }

    @Override
    public void sendDefect() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent(MessageType.Defect.toString());
        this.sendMessage(msg);
    }

    @Override
    public void sendMessage(ACLMessage msg) {
        msg.addReceiver(new AID("judge", AID.ISLOCALNAME));
        super.sendMessage(msg);
    }
}
