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


        String action = agent.choose_action(message.getContent());

		agent.doWait(500);
		System.out.println("action send");


        ACLMessage info = new ACLMessage(ACLMessage.INFORM);
		info.setContent(action);
		info.addReceiver(MasterAgent.IDENTIFIANT);
		agent.send(info);
        
	}

}
