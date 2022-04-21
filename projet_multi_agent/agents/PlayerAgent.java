package agents;

import java.util.Random;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.AID;
import behviours.Player.FindGame;
import behviours.Player.ActionPhase;
import behviours.Player.MovementPhase;
import behviours.Player.EndPhase;





public class PlayerAgent extends Agent{


    private static final String START = "start";
	private static final String MOVE = "move";
	private static final String ACTION = "action";
    private static final String END = "end";
    
    public static AID IDENTIFIANT = new AID("Playeragent", AID.ISLOCALNAME);


    public void setup(){

        FSMBehaviour behaviour = new FSMBehaviour(this);

        behaviour.registerFirstState(new FindGame(this), START);
        behaviour.registerState(new MovementPhase(this), MOVE);
        behaviour.registerState(new ActionPhase(this), ACTION);
        behaviour.registerLastState(new EndPhase(this), END);

        behaviour.registerDefaultTransition(ACTION, MOVE);
        behaviour.registerDefaultTransition(START, MOVE);
        behaviour.registerTransition(MOVE,ACTION , 1);
        behaviour.registerTransition(MOVE,MOVE , 2);
        behaviour.registerTransition(MOVE,MOVE , 3);
        behaviour.registerTransition(MOVE,END, 4);
        
        addBehaviour(behaviour);

    }

    Random rand = new Random();

    public String choose_direction(String directions){
        String[] dir = directions.split(",");
        String newd;
        int val = rand.nextInt(dir.length);
        newd = dir[val];
        return newd;
    }


    public String choose_action(String actions){
        int force = MasterAgent.characterSheet[1];
        int intel = MasterAgent.characterSheet[2];
        int agi = MasterAgent.characterSheet[3];
        String action = null;

        if (actions.equals("fight")){
            if (force*3 > agi*2){
                if (force*3 > intel){
                    action = "attack";
                }
                else action = "spell";
            }
            else if (agi*2 > intel) action = "dodge";
            else action = "spell";
        }
        if (actions.equals("trap")){
            if (force*2 > agi){
                if (force*2 > intel*3){
                    action = "force";
                }
                else action = "solve";
            }
            else if (agi > intel*3) action = "cross";
            else action = "solve";
        }
        if (actions.equals("encounter")){
            if (force > agi*3){
                if (force > intel*2){
                    action = "intimidate";
                }
                else action = "charm";
            }
            else if (agi*3 > intel*2) action = "talk";
            else action = "charm";
        }

        return action;
    }
}
