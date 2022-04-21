package behviours.Master;

import agents.MasterAgent;
import jade.core.behaviours.OneShotBehaviour;


public class ResultPhase extends OneShotBehaviour {
	MasterAgent agent;
	
	public ResultPhase(MasterAgent a){
		this.agent = a;
	}
	
	
	public void action() {
        
        System.out.println("Process rewards");

        agent.determine_results("treasure");
		
	}

}
