package game;

import jade.core.AID;
import jade.core.ProfileImpl;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.Runtime;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Judge extends Player {

    private int rounds = 10;
    private ContainerController container = Runtime.instance().createAgentContainer(new ProfileImpl());
    private HashMap<String, HashMap<Integer, ACLMessage>> history = new HashMap<String, HashMap<Integer, ACLMessage>>();

    @Override
    protected void setup() {
        // Wait for container to start
        this.doWait(500);

        System.out.println("Judge::Setup::" + this.getName());
        super.setup();

        history.put("cooperate", new HashMap<Integer, ACLMessage>());
        history.put("defect", new HashMap<Integer, ACLMessage>());

        try {
            container.createNewAgent("cooperate", "prisoners.CooperatePrisoner", null).start();
            container.createNewAgent("defect", "prisoners.DefectPrisoner", null).start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
        // Wait for agents to start
        this.doWait(1000);
        this.addBehaviour(new JudgeBehaviour(this));

    }

    public int getRounds() {
        return rounds;
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

    private void handleDefect(ACLMessage msg, String prisoner, int round) {
        HashMap<Integer, ACLMessage> prisonerHistory = history.get(prisoner);
        prisonerHistory.put(round, msg);
        history.put(prisoner, prisonerHistory);
    }

    private void handleCooperate(ACLMessage msg, String prisoner, int round) {
        HashMap<Integer, ACLMessage> prisonerHistory = history.get(prisoner);
        prisonerHistory.put(round, msg);
        history.put(prisoner, prisonerHistory);
    }

    private void sendSituation() {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(MessageType.Situation.toString());
        msg.addReceiver(new AID("cooperate", AID.ISLOCALNAME));
        msg.addReceiver(new AID("defect", AID.ISLOCALNAME));
        this.sendMessage(msg);
    }

    private void calculateResults() {
        int firstPrisonerYears = 0;
        int secondPrisonerYears = 0;
        HashMap<Integer, ACLMessage> firstPrisonerHistory = this.history.get("cooperate");
        HashMap<Integer, ACLMessage> secondPrisonerHistory = this.history.get("defect");

        // Calculate years in prisons after n rounds
        for (int i = 0; i < this.getRounds(); i += 1) {
            //System.out.println(firstPrisonerHistory.get(i) + " vs. " + secondPrisonerHistory.get(i));
            ACLMessage firstMsg = firstPrisonerHistory.get(i);
            ACLMessage secondMsg = secondPrisonerHistory.get(i);

            if (firstMsg.equals(secondMsg)) {
                if (MessageType.valueOf(firstMsg.getContent()) == MessageType.Cooperate) {
                    // Both prisoners get 1 year
                    firstPrisonerYears += 1;
                    secondPrisonerYears += 1;
                }
                else {
                    // Both prisoners get 2 years
                    firstPrisonerYears += 2;
                    secondPrisonerYears += 2;
                }
            }

            else {
                if (MessageType.valueOf(firstMsg.getContent()) == MessageType.Cooperate && MessageType.valueOf(secondMsg.getContent()) == MessageType.Defect) {
                    // Cooperate Prisoner gets 3 years
                    firstPrisonerYears += 3;
                }
                else {
                    // Cooperate Prisoner gets 3 years
                    secondPrisonerYears += 3;
                }
            }
        }

        System.out.println("Cooperate Prisoner Years in Prison: " + firstPrisonerYears);
        System.out.println("Defect Prisoner Years in Prison: " + secondPrisonerYears);

    }
}
