package behviours.Master;

import agents.MasterAgent;
import jade.core.behaviours.OneShotBehaviour;


public class CreateGame extends OneShotBehaviour {
	MasterAgent agent;
	
	public CreateGame(MasterAgent a){
		this.agent = a;
	}
	
	
	public void action() {
		agent.doWait();
        agent.receive();

        System.out.println("initialisation de la partie");

		agent.create_dungeon();
        agent.generat_charactersheet();

		
	}

}
