package agents;

import java.util.Random;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.AID;
import behviours.Master.CreateGame;
import behviours.Master.ActionPhase;
import behviours.Master.MovementPhase;
import behviours.Master.EventPhase;
import behviours.Master.ResultPhase;
import behviours.Master.EndPhase;




public class MasterAgent extends Agent{

    private static final String START = "start";
	private static final String MOVE = "move";
	private static final String ACTION = "action";
	private static final String RESULT = "result";
    private static final String EVENT = "event";
    private static final String END = "end";



    public static AID IDENTIFIANT = new AID("Masteragent", AID.ISLOCALNAME);
    public static int [] playerPos;
    public static int [][] map;
    public static int [] characterSheet;
    Random rand = new Random();






    public void setup(){

        FSMBehaviour behaviour = new FSMBehaviour(this);


        behaviour.registerFirstState(new CreateGame(this), START);
        behaviour.registerState(new MovementPhase(this), MOVE );
        behaviour.registerState(new ActionPhase(this), ACTION);
        behaviour.registerState(new ResultPhase(this), RESULT);
        behaviour.registerState(new EventPhase(this), EVENT);
        behaviour.registerLastState(new EndPhase(this), END);

        behaviour.registerDefaultTransition(START, MOVE);
        behaviour.registerDefaultTransition(RESULT, EVENT);
        behaviour.registerDefaultTransition(EVENT, MOVE);
		behaviour.registerTransition(MOVE,ACTION , 1);
        behaviour.registerTransition(MOVE,RESULT , 2);
        behaviour.registerTransition(MOVE,EVENT , 3);
        behaviour.registerTransition(MOVE,END, 4);
        behaviour.registerTransition(ACTION,EVENT , 1);
        behaviour.registerTransition(ACTION,END , 2);


        addBehaviour(behaviour);
    }



    public void create_dungeon() {
        map = new int [6][6];
        double rd;
        for (int i =0;i<6;i++){
            for (int j =0;j<6;j++){
                rd = Math.random();
                if (rd<=0.7) map[i][j] = 1;
                else if (rd<=0.9) map[i][j] = 2;
                else map[i][j] = 3;
            }
        }
        int x = rand.nextInt(6);
        int y = rand.nextInt(6);
        map[x][y] = 4;

        int xj;
        int yj;
        do{
            xj = rand.nextInt(6);
            yj = rand.nextInt(6);
        }while((xj != x) && (yj !=y));
        playerPos = new int [2];
        playerPos[0] = xj;
        playerPos[1] = yj;

    }

    public void generat_charactersheet() {
        // PV : force : intel : agi : Gold
        characterSheet = new int [5];
        characterSheet [0] = rand.nextInt(5)+4;
        characterSheet [1] = rand.nextInt(10)+1;
        characterSheet [2] = rand.nextInt(10)+1;
        characterSheet [3] = rand.nextInt(10)+1;
        characterSheet [4] = 0;
    }


    public String available_direction(){
        String directions = "";
        if (playerPos[0]-1 >=0){
            directions += "haut,";
        }
        if (playerPos[1]-1 >=0){
            directions += "gauche,";
        }
        if (playerPos[0]+1 <6){
            directions += "bas,";
        }
        if (playerPos[1]+1 <6){
            directions += "droite,";
        }
        return directions;
    }
    public String possible_action(){
        String actions = "";
        int rd = rand.nextInt(3);
        if (rd ==0){
            actions+= "fight";
        }
        else if(rd ==1){
            actions+= "trap";
        }
        else{
            actions+= "encounter";
        }
        return actions;
    }
    public void move_player(String direction){
        map[playerPos[0]][playerPos[1]] = 3 ;

        if (direction.equals("haut")){
            playerPos[0] -=  1;
        }
        if (direction.equals("bas")){
            playerPos[0] += 1;
        }
        if (direction.equals("gauche")){
            playerPos[1] -= 1;
        }
        if (direction.equals("droite")){
            playerPos[1] += 1;
        }
        System.out.println("le jouer c'est déplacé vers le/la "+direction);
        System.out.println("Posistion du joueur : "+playerPos[0] +" "+playerPos[1]);
    }
    public int process_room(){
        String out = "";
        switch (map[playerPos[0]][playerPos[1]]){
            case 1:
                out+="une action vas se déroulé dans cette salle";
                break;
            case 2:
                out+="une salle de trésor est pillé";
                break;
            case 3:
                out+="la salle est vide";
                break;
            case 4:
                out+="le joueur c'est échapé par la sortie du Donjon avec : "+Integer.toString(characterSheet[4])+" golds";
                break;
        }
        System.out.println(out);
        return map[playerPos[0]][playerPos[1]];
    }

    public void pv_loss(int nb){
        characterSheet[0]-=nb;
        if (nb == 0) System.out.println("nothing append");
        else System.out.println("Player lose "+nb+" HP");
    }
    public void gold_earns(int nb){
        characterSheet [4] +=nb;
        if (nb == 0) System.out.println("nothing append");
        else System.out.println("Player earns "+nb+" golds");
    }

    public int determine_results(String action){
        int end = 1;
        int loot = rand.nextInt(10)+1;
        int roll = rand.nextInt(10)+1;
        switch(action){
            case "treasure" :
                gold_earns(loot*3); ;
                break;
            case "attack" :
                if (characterSheet[1] >= roll) gold_earns(loot*2) ;
                else pv_loss(2);
                break;
            case "spell" :
                if (characterSheet[2] >= roll) gold_earns(0) ;
                else pv_loss(2);
                break;
            case "dodge" :
                if (characterSheet[3] >= roll) gold_earns(loot*1) ;
                else pv_loss(2);
                break;
            case "force" :
                if (characterSheet[1] >= roll) gold_earns(loot*1) ;
                else pv_loss(1);
                break;
            case "solve" :
                if (characterSheet[2] >= roll) gold_earns(loot*2) ;
                else pv_loss(1);
                break;
            case "cross" :
                if (characterSheet[3] >= roll) gold_earns(0) ;
                else pv_loss(1);
                break;
            case "intimidate" :
                if (characterSheet[1] >= roll) gold_earns(0) ;
                else pv_loss(0);
                break;
            case "charm" :
                if (characterSheet[2] >= roll) gold_earns(loot*1) ;
                else pv_loss(0);
                break;
            case "talk" :
                if (characterSheet[3] >= roll) gold_earns(loot*2) ;
                else pv_loss(0);
                break;
            default:
                break;
        }
        
        if (characterSheet[0]<0) {
            end = 2;
            System.out.println("le joueur est mort dans le donjon");
        }
        return end;
    }
    public void spawn_event(){
        int x = rand.nextInt(6);
        int y = rand.nextInt(6);
        if ((playerPos[0] != x) || (playerPos[1] !=y)){
            if (map[x][y] !=4){
                int rd = rand.nextInt(3);
                if (rd != 0){
                    map[x][y] = rd;
                    System.out.println("an event as spawn");
                }
            }
        }
    }
}



