package prisoners;

import behaviours.PrisonerBehaviour;
import game.MessageType;
import jade.lang.acl.ACLMessage;


public class TitForTatPrisoner extends Prisoner {

    private MessageType nextAction = MessageType.Cooperate;

    @Override
    protected void setup() {
        super.setup();
        this.addBehaviour(new PrisonerBehaviour(this));
    }

    @Override
    public void handleSituation(ACLMessage msg) {

        if (this.getNextAction().equals(MessageType.Cooperate)) {
            this.sendCooperate();
            return;
        }
        this.sendDefect();
    }

    @Override
    public void handleCooperate(ACLMessage msg) {
        this.setNextAction(MessageType.Cooperate);
    }

    @Override
    public void handleDefect(ACLMessage msg) {
        this.setNextAction(MessageType.Defect);
    }

    public MessageType getNextAction() {
        return nextAction;
    }

    public void setNextAction(MessageType nextAction) {
        this.nextAction = nextAction;
    }
}
