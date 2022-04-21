package behviours.Player;

import agents.MasterAgent;
import agents.PlayerAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class ActionPhase extends OneShotBehaviour {
	PlayerAgent agent;
	
	public ActionPhase(PlayerAgent a){
		this.agent = a;
	}
	
	

	public void action() {

        agent.doWait();
        ACLMessage message = agent.receive();

		System.out.println("action reçue");

        String action = agent.choose_action(message.getContent());

		System.out.println("action choisie");


        ACLMessage info = new ACLMessage(ACLMessage.INFORM);
		info.setContent(action);
		info.addReceiver(MasterAgent.IDENTIFIANT);
		agent.send(info);
        System.out.println("action envoyé");
        
	}

}
