package prisoners;

import behaviours.PrisonerBehaviour;
import game.MessageType;
import game.Player;
import interfaces.PrisonerInterface;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public abstract class Prisoner extends Player implements PrisonerInterface {

    private Properties properties = new Properties();

    @Override
    protected void setup() {
        super.setup();
        System.out.println("Prisoner::Setup::" + this.getName());

        try {
            this.properties.load(new FileInputStream("src/main/resources/prisoners.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addBehaviour(new PrisonerBehaviour(this));
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
        // Prisoners always send their message to the judge and their opponent
        msg.addReceiver(new AID("judge", AID.ISLOCALNAME));
        msg.addReceiver(new AID(this.getOpponentName(), AID.ISLOCALNAME));
        super.sendMessage(msg);
    }

    /**
     * Get the local name of the prisoner, needed for sending messages
     * @return the local name of the opponent
     */
    public String getOpponentName() {
        String firstName = this.properties.get("firstPrisonerName").toString();
        String secondName = this.properties.get("secondPrisonerName").toString();

        if (this.getLocalName().equals(firstName)) {
            return secondName;
        }

        return firstName;
    }
}
