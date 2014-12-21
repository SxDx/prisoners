package prisoners;

import behaviours.PrisonerBehaviour;
import jade.lang.acl.ACLMessage;

public class DefectPrisoner extends Prisoner {

    @Override
    protected void setup() {
        super.setup();
        this.addBehaviour(new PrisonerBehaviour(this));
    }

    @Override
    public void handleSituation(ACLMessage msg) {
        this.sendDefect();
    }

    @Override
    public void handleCooperate(ACLMessage msg) {
        // Do Nothing
    }

    @Override
    public void handleDefect(ACLMessage msg) {
        // Do Nothing
    }

}
