package prisoners;

import jade.lang.acl.ACLMessage;

public class SpitePrisoner extends Prisoner {

    private boolean defected = false;

    @Override
    public void handleSituation(ACLMessage msg) {
        if(!this.opponentHasDefected()) {
            this.sendCooperate();
            return;
        }

        this.sendDefect();
    }

    @Override
    public void handleCooperate(ACLMessage msg) {
        // Do nothing
    }

    @Override
    public void handleDefect(ACLMessage msg) {
        this.defected = true;
    }

    public boolean opponentHasDefected() {
        return this.defected;
    }


}
