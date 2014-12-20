package prisoners;

import jade.lang.acl.ACLMessage;

public class CooperatePrisoner extends Prisoner {

    @Override
    protected void setup() {
        super.setup();
        this.addBehaviour(new PrisonerBehaviour(this));
    }

    @Override
    public void handleSituation(ACLMessage msg) {
        System.out.println("CooperatePrisoner::handleSituation");
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
