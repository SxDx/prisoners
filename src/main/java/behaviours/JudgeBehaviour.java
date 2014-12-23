package behaviours;

import game.Judge;
import game.MessageType;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Iterator;

public class JudgeBehaviour extends OneShotBehaviour {

    private Judge judge;

    public JudgeBehaviour(Judge judge) {
        super(judge);
        this.judge = judge;
    }

    @Override
    public void action() {
        //System.out.println("Judge::Action");

        for (int i = 0; i <= this.judge.getRounds(); i += 1) {
            //System.out.println("Judge::Action::Round::" + i + "::Start");
            this.judge.sendSituation();

            // Save the messages sent by the the two prisoners
            ArrayList<ACLMessage> messages = new ArrayList<ACLMessage>();
            messages.add(this.myAgent.blockingReceive());
            messages.add(this.myAgent.blockingReceive());

            Iterator<ACLMessage> iterator = messages.iterator();

            while (iterator.hasNext()) {
                ACLMessage message = iterator.next();

                switch (MessageType.valueOf(message.getContent())) {
                    case Situation:
                        break;
                    case Defect:
                        this.judge.handleDefect(message, message.getSender().getLocalName(), i);
                        break;
                    case Cooperate:
                        this.judge.handleCooperate(message, message.getSender().getLocalName(), i);
                        break;
                }
            }
        }

        this.judge.calculateResults();
    }
}