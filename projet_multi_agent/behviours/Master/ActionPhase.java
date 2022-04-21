package behviours.Master;

import agents.MasterAgent;
import agents.PlayerAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


public class ActionPhase extends OneShotBehaviour {
	MasterAgent agent;
	int retour;

	public ActionPhase(MasterAgent a){
		this.agent = a;
        retour = 0;
	}
	
	
	public void action() {
        String actions = agent.possible_action();

        System.out.println("action request");

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(actions);
        msg.addReceiver(PlayerAgent.IDENTIFIANT);
        agent.send(msg);


        agent.doWait();
        ACLMessage message = agent.receive();
        
        System.out.println("calcul des récompense");

        retour = agent.determine_results(message.getContent());

	}

    public int onEnd() {
		return retour;
	}
}
