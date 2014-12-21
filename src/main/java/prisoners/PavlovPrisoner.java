package prisoners;


import game.MessageType;
import jade.lang.acl.ACLMessage;

public class PavlovPrisoner extends Prisoner {

    private MessageType nextAction = MessageType.Cooperate;
    private MessageType lastAction = MessageType.Cooperate;

    @Override
    public void handleSituation(ACLMessage msg) {
        if(this.getNextAction().equals(MessageType.Cooperate))
        {
            this.sendCooperate();
            this.setLastAction(MessageType.Cooperate);
            return;
        }

        this.sendDefect();
        this.setLastAction(MessageType.Defect);
    }

    @Override
    public void handleCooperate(ACLMessage msg) {
        if (this.getLastAction().equals(MessageType.Cooperate)) {
            this.setNextAction(MessageType.Cooperate);
            return;
        }

        this.setNextAction(MessageType.Defect);
    }

    @Override
    public void handleDefect(ACLMessage msg) {
        if (this.getLastAction().equals(MessageType.Defect)) {
            this.setNextAction(MessageType.Cooperate);
            return;
        }

        this.setNextAction(MessageType.Defect);
    }


    public MessageType getNextAction() {
        return nextAction;
    }

    public void setNextAction(MessageType nextAction) {
        this.nextAction = nextAction;
    }

    public MessageType getLastAction() {
        return lastAction;
    }

    public void setLastAction(MessageType lastAction) {
        this.lastAction = lastAction;
    }
}
