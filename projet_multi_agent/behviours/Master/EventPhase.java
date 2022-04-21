package behviours.Master;

import agents.MasterAgent;
import jade.core.behaviours.OneShotBehaviour;


public class EventPhase extends OneShotBehaviour {
	MasterAgent agent;
	
	public EventPhase(MasterAgent a){
		this.agent = a;
	}
	
	
	public void action() {

        agent.spawn_event();
	}

}
