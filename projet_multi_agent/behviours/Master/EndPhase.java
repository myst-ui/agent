package behviours.Master;

import agents.MasterAgent;
import jade.core.behaviours.OneShotBehaviour;


public class EndPhase extends OneShotBehaviour {
	MasterAgent agent;
	
	public EndPhase(MasterAgent a){
		this.agent = a;
	}
	
	
	public void action() {
        System.out.println("End");
		agent.doDelete();
	}

}