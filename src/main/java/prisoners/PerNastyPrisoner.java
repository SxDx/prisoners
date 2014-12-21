package prisoners;


import game.MessageType;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Arrays;

public class PerNastyPrisoner extends Prisoner {

    private ArrayList<MessageType> messages = new ArrayList<MessageType>(Arrays.asList(MessageType.Defect, MessageType.Defect, MessageType.Cooperate));
    private int index = 0;

    @Override
    public void handleSituation(ACLMessage msg) {

        if (messages.get(this.getNextIndex()).equals(MessageType.Cooperate)) {
            this.sendCooperate();
            return;
        }

        this.sendDefect();
    }

    @Override
    public void handleCooperate(ACLMessage msg) {
        this.index += 1;
    }

    @Override
    public void handleDefect(ACLMessage msg) {
        this.index += 1;
    }

    public int getNextIndex() {
        return this.index % 3;
    }
}
