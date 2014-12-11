package game;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.core.behaviours.*;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.Iterator;

public class Judge extends Player {

    private ArrayList<AgentController> prisoners = new ArrayList<AgentController>();

    @Override
    protected void setup() {
        super.setup();
        System.out.println("Setup: Loading Judge");
        AgentContainer ac = this.getContainerController();
        Object[] args = new Object[1];
        try {
            args[0] = "1";
            AgentController hans = ac.createNewAgent("Hans", "CooperatePrisoner", args);

            args[0] = "2";
            AgentController klaus = ac.createNewAgent("Klaus", "DefectPrisoner", args);
            prisoners.add(hans);
            prisoners.add(klaus);

            klaus.start();
            hans.start();
        } catch (Exception e) {
            System.out.println("Error creating Player");
            e.printStackTrace();

        }

        this.addBehaviour(new JudgeBehaviour(this));
    }

    private class JudgeBehaviour extends OneShotBehaviour {

        private Judge judge;

        public JudgeBehaviour(Judge judge) {
            super(judge);
            this.judge = judge;
        }

        @Override
        public void action() {
            System.out.println("Judge.action");
            Iterator<AgentController> it = this.judge.prisoners.iterator();
            while (it.hasNext()) {
                AgentController prisoner = it.next();
                AID receiver = null;
                try {
                    receiver = new AID(prisoner.getName(), AID.ISLOCALNAME);
                } catch (Exception e) {
                    System.out.println("Error getting Receiver for Situation");
                }
                this.judge.sendSituation(receiver);
            }
        }
    }

    private void sendSituation(AID receiver) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(receiver);
        this.sendMessage(msg);
    }
}
