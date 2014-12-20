package prisoners;

import game.Player;
import interfaces.PrisonerInterface;
import jade.lang.acl.ACLMessage;


public abstract class Prisoner extends Player implements PrisonerInterface {

    @Override
    protected void setup() {
        super.setup();
        System.out.println("Prisoner::Setup::"+  this.getName());
    }

    @Override
    public void sendCooperate() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent("Coorperate");
        this.sendMessage(msg);
    }

    @Override
    public void sendDefect() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent("Defect");
        this.sendMessage(msg);
    }
}
