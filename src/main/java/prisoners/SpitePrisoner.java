package prisoners;

import jade.lang.acl.ACLMessage;

public class SpitePrisoner extends Prisoner {

    private boolean defected = false;

    @Override
    public void handleSituation(ACLMessage msg) {
        this.sendCooperate();
    }

    @Override
    public void handleCooperate(ACLMessage msg) {
        if(!this.opponentHasDefected()) {
            this.sendCooperate();
        }

        this.sendDefect();
    }

    @Override
    public void handleDefect(ACLMessage msg) {
        this.defected = true;
    }

    public boolean opponentHasDefected() {
        return this.defected;
    }


}
