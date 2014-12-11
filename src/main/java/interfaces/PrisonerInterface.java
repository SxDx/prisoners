package interfaces;

import jade.lang.acl.ACLMessage;

public interface PrisonerInterface {
    /**
     * Handle a Situation
     *
     * @param msg - The received message
     */
    public void handleSituation(ACLMessage msg);

    /**
     * Send a cooparate message
     */
    public void sendCooperate();

    /**
     * Handle a cooperate message
     *
     * @param msg - The received message
     */
    public void handleCooperate(ACLMessage msg);

    /**
     * send a defect message
     */
    public void sendDefect();

    /**
     * Handle a defect message
     *
     * @param msg - The received message
     */
    public void handleDefect(ACLMessage msg);
}
