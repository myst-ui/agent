
package behviours.Player;

import agents.PlayerAgent;
import jade.core.behaviours.OneShotBehaviour;

public class EndPhase extends OneShotBehaviour {
	PlayerAgent agent;
	
	public EndPhase(PlayerAgent a){
		this.agent = a;
	}
	
	

	public void action() {
		System.out.println("End");
		agent.doDelete();
	}

}
