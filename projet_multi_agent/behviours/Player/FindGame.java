package behviours.Player;

import agents.MasterAgent;
import agents.PlayerAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class FindGame extends OneShotBehaviour {
	PlayerAgent agent;
	int retour;
	
	public FindGame(PlayerAgent a){
		this.agent = a;
		retour = 0;
	}
	
	

	public void action() {
		agent.doWait(15000);
		
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.setContent("START GAME");
		message.addReceiver(MasterAgent.IDENTIFIANT);
		agent.send(message);
        System.out.println("demande de jeu");

		ACLMessage answer = agent.receive();
		if (answer.getContent().equals("Accept")) retour = 1;


	}
	
	public int onEnd() {
		return retour;
	}


}
