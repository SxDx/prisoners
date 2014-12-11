package prisoners;

import game.MessageType;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class PrisonerBehaviour extends CyclicBehaviour {

    private Prisoner prisoner;

    public PrisonerBehaviour(Prisoner p) {
        super(p);
        this.prisoner = p;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.blockingReceive();

        switch (MessageType.valueOf(msg.getContent())) {
            case Situation:
                prisoner.handleSituation(msg);
                break;
            case Defect:
                prisoner.handleDefect(msg);
                break;
            case Cooperate:
                prisoner.handleCooperate(msg);
                break;
        }
    }
}
