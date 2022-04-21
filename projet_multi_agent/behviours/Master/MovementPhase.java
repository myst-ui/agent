package behviours.Master;

import agents.MasterAgent;
import agents.PlayerAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


public class MovementPhase extends OneShotBehaviour {
	MasterAgent agent;
    int retour;
	
	public MovementPhase(MasterAgent a){
		this.agent = a;
        retour = 4;
	}
	
	
	public void action() {

		String directions = agent.available_direction();
        System.out.println("movement request");
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(directions);
        msg.addReceiver(PlayerAgent.IDENTIFIANT);
        agent.send(msg);

        agent.doWait();
        ACLMessage message = agent.receive();
        String direction = message.getContent();

        agent.move_player(direction);
        retour = agent.process_room();

        ACLMessage info = new ACLMessage(ACLMessage.INFORM);
        info.setContent(Integer.toString(retour));
        info.addReceiver(PlayerAgent.IDENTIFIANT);
        agent.send(info);
        agent.doWait(3000);
        
	}
    public int onEnd() {
		return retour;
	}

}
