package prisoners;

import behaviours.PrisonerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class RandomPrisoner extends Prisoner {


    private Random random = new Random();

    @Override
    protected void setup() {
        super.setup();
        this.addBehaviour(new PrisonerBehaviour(this));
    }

    @Override
    public void handleSituation(ACLMessage msg) {

        if (this.getRandom().nextBoolean()) {
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

    public Random getRandom() {
        return random;
    }

}
