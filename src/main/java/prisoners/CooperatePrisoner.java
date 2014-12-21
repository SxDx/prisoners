package prisoners;

import jade.lang.acl.ACLMessage;

public class CooperatePrisoner extends Prisoner {

    @Override
    public void handleSituation(ACLMessage msg) {
        this.sendCooperate();
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
