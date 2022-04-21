
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
        String content = message.getContent();

        if (! content.equals("e")){
            String direction = agent.choose_direction(message.getContent());

            agent.doWait(500);

            System.out.println("movement send");

            ACLMessage info = new ACLMessage(ACLMessage.INFORM);
            info.setContent(direction);
            info.addReceiver(MasterAgent.IDENTIFIANT);
            agent.send(info);
    
                
            agent.doWait();
            ACLMessage room = agent.receive();
            retour = Integer.parseInt(room.getContent());

        }
	}

    public int onEnd() {
		return retour;
	}

}
