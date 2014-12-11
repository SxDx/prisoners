package prisoners;



import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class CooperatePrisoner extends Prisoner {

    @Override
    protected void setup() {
        this.setStrategy("Always cooprating");
        super.setup();
        this.addBehaviour(new PrisonerBehaviour(this));
    }

    @Override
    public void handleSituation(ACLMessage msg) {
        System.out.println("Handling of Cooperate player");
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
