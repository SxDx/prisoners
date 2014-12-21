package game;

import behaviours.JudgeBehaviour;
import jade.core.AID;
import jade.core.ProfileImpl;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.Runtime;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Judge extends Player {

    private int rounds = 10;
    private ContainerController container = Runtime.instance().createAgentContainer(new ProfileImpl());
    private HashMap<String, HashMap<Integer, ACLMessage>> history = new HashMap<String, HashMap<Integer, ACLMessage>>();
    private HashMap<String, String> prisoners = new HashMap<String, String>();
    private Properties properties = new Properties();

    @Override
    protected void setup() {
        // Wait for container to start
        this.doWait(500);

        System.out.println("Judge::Setup::" + this.getName());
        super.setup();

        try {
            this.properties.load(new FileInputStream("src/main/resources/prisoners.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        history.put(this.getFirstPrisonerName(), new HashMap<Integer, ACLMessage>());
        history.put(this.getSecondPrisonerName(), new HashMap<Integer, ACLMessage>());

        try {
            container.createNewAgent(this.getFirstPrisonerName(), this.getFirstPrisonerClass(), null).start();
            container.createNewAgent(this.getSecondPrisonerName(), this.getSecondPrisonerClass(), null).start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
        // Wait for agents to start
        this.doWait(1000);
        this.addBehaviour(new JudgeBehaviour(this));

    }

    private String getFirstPrisonerName() {
        return this.properties.get("firstPrisonerName").toString();
    }

    private String getFirstPrisonerClass() {
        return this.properties.get("firstPrisonerClass").toString();
    }

    private String getSecondPrisonerName() {
        return this.properties.get("secondPrisonerName").toString();
    }

    private String getSecondPrisonerClass() {
        return this.properties.get("secondPrisonerClass").toString();
    }

    public int getRounds() {
        return rounds;
    }
    
    public void handleDefect(ACLMessage msg, String prisoner, int round) {
        HashMap<Integer, ACLMessage> prisonerHistory = history.get(prisoner);
        prisonerHistory.put(round, msg);
        history.put(prisoner, prisonerHistory);
    }

    public void handleCooperate(ACLMessage msg, String prisoner, int round) {
        HashMap<Integer, ACLMessage> prisonerHistory = history.get(prisoner);
        prisonerHistory.put(round, msg);
        history.put(prisoner, prisonerHistory);
    }

    public void sendSituation() {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(MessageType.Situation.toString());
        msg.addReceiver(new AID(this.getFirstPrisonerName(), AID.ISLOCALNAME));
        msg.addReceiver(new AID(this.getSecondPrisonerName(), AID.ISLOCALNAME));
        this.sendMessage(msg);
    }

    public void calculateResults() {
        int firstPrisonerYears = 0;
        int secondPrisonerYears = 0;
        HashMap<Integer, ACLMessage> firstPrisonerHistory = this.history.get(this.getFirstPrisonerName());
        HashMap<Integer, ACLMessage> secondPrisonerHistory = this.history.get(this.getSecondPrisonerName());

        // Calculate years in prisons after n rounds
        for (int i = 0; i < this.getRounds(); i += 1) {
            //System.out.println(firstPrisonerHistory.get(i) + " vs. " + secondPrisonerHistory.get(i));
            ACLMessage firstMsg = firstPrisonerHistory.get(i);
            ACLMessage secondMsg = secondPrisonerHistory.get(i);

            // Both Prisoners do the same
            if (firstMsg.equals(secondMsg)) {
                if (MessageType.valueOf(firstMsg.getContent()) == MessageType.Cooperate) {
                    // Both prisoners get 1 year
                    firstPrisonerYears += 1;
                    secondPrisonerYears += 1;
                } else {
                    // Both prisoners get 2 years
                    firstPrisonerYears += 2;
                    secondPrisonerYears += 2;
                }
            }

            // Prisoners differ
            else {
                if (MessageType.valueOf(firstMsg.getContent()) == MessageType.Cooperate && MessageType.valueOf(secondMsg.getContent()) == MessageType.Defect) {
                    // Cooperate Prisoner gets 3 years
                    firstPrisonerYears += 3;
                } else {
                    // Cooperate Prisoner gets 3 years
                    secondPrisonerYears += 3;
                }
            }
        }

        System.out.println(this.getFirstPrisonerName() + " Prisoner Years in Prison: " + firstPrisonerYears);
        System.out.println(this.getSecondPrisonerName() + " Prisoner Years in Prison: " + secondPrisonerYears);
    }
}
