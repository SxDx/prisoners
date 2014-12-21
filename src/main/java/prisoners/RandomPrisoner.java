package prisoners;

import jade.lang.acl.ACLMessage;

public class RandomPrisoner extends Prisoner {

    @Override
    public void handleSituation(ACLMessage msg) {

        Double random = Math.random();

        if (random >= 0.5) {
            this.sendCooperate();
            return;
        }

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
