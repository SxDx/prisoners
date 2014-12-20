package game;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Judge extends Player {
    private ArrayList<AID> prisoners = new ArrayList<AID>();
    private int rounds = 10;

    @Override
    protected void setup() {
        super.setup();

        System.out.println("Judge::Setup::" + this.getName());
        Object[] args = this.getArguments();

        if (args != null && args.length == 2) {
            System.out.println("Judge::Setup::Prisoners");
            prisoners.add(new AID("cooperate", AID.ISLOCALNAME));
        } else {
            System.out.println("Judge::Setup::Parameters::Error");
        }

        this.addBehaviour(new JudgeBehaviour(this));

        System.out.println("Judge::Setup::Finished");
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public ArrayList getPrisoners() {
        return prisoners;
    }

    public void setPrisoners(ArrayList prisoners) {
        this.prisoners = prisoners;
    }

    private class JudgeBehaviour extends OneShotBehaviour {

        private Judge judge;

        public JudgeBehaviour(Judge judge) {
            super(judge);
            this.judge = judge;
        }

        @Override
        public void action() {
            System.out.println("Judge::Action");

            for (int i = 0; i <= this.judge.getRounds(); i += 1) {
                System.out.println("Judge::Action::Round::" + i + "::Start");
                Iterator<AID> iterator = this.judge.getPrisoners().iterator();
                while (iterator.hasNext())
                {
                    AID prisoner = iterator.next();
                    this.judge.sendSituation(prisoner);
                }
            }
        }
    }

    private void sendSituation(AID receiver) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(receiver);
        msg.setContent("Situation");
        this.sendMessage(msg);
    }
}
