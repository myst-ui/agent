
package behviours.Player;

import agents.MasterAgent;
import agents.PlayerAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class MovementPhase extends OneShotBehaviour {
	PlayerAgent agent;
    int retour;
	
	public MovementPhase(PlayerAgent a){
		this.agent = a;
        retour = 4;
	}
	
	

	public void action() {
        agent.doWait();
        ACLMessage message = agent.receive();

        if (message.getContent().length() >3){
            String direction = agent.choose_direction(message.getContent());

            ACLMessage info = new ACLMessage(ACLMessage.INFORM);
            info.setContent(direction);
            info.addReceiver(MasterAgent.IDENTIFIANT);
            agent.send(info);
    
            System.out.println("movement envoyé");
                
            agent.doWait();
            ACLMessage room = agent.receive();
            retour = Integer.parseInt(room.getContent());

        }
	}

    public int onEnd() {
		return retour;
	}

}
