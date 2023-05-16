package taquin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
import java.util.Random;
import javafx.util.Pair;


public class Main {
    public static Plateau plateau;
    public static Affichage aff;
    public static Agent[] tabAgents;
    public static HashMap<Agent, ArrayList<Message>> HashMapMessages;
    
    

    public static void main(String[] args) {
        // choix du nombre d'agents
        Scanner InputNbrAgents = new Scanner(System.in);
        System.out.println("Nombre d'agents ? ");
        Agent.nbrAgents = InputNbrAgents.nextInt();
        InputNbrAgents.close();

        tabAgents = new Agent[Agent.nbrAgents];

        // code pour rendre la positions des agents aleatoire
        Position[] tabpos = new Position[25];
        int indicetab = 0;
        int min = 0;
        int max = Plateau.getSize()-1;
        List<Pair<Integer, Integer>> couples = new ArrayList<>();
        for(int i=0;i < 25 ;i++) {

            Random randomX = new Random();
            int nombreAleatoireX = randomX.nextInt(max - min + 1) + min;
    
            Random randomY = new Random();
            int nombreAleatoireY = randomY.nextInt(max - min + 1) + min;

            Pair<Integer, Integer> paire = new Pair<Integer, Integer>(nombreAleatoireX,nombreAleatoireY);
            while(couples.contains(paire)){
                nombreAleatoireX = randomX.nextInt(max - min + 1) + min;
                nombreAleatoireY = randomY.nextInt(max - min + 1) + min;
                paire = new Pair<Integer, Integer>(nombreAleatoireX,nombreAleatoireY);
            }
            couples.add(paire);
        }
        for (Pair<Integer, Integer> p : couples) {
            tabpos[indicetab] = new Position(p.getKey(),p.getValue());
            indicetab=indicetab+1;
        }
        // liste des agents avec position initiale aleatoire et positon final fixe
        // on choisi arbitrairement les agents a trouvé
        Agent[] tabAgents2 = {
            new Agent(tabpos[0], new Position(0,0), "0 "),
            new Agent(tabpos[1], new Position(0,1), "1 "),
            //new Agent(tabpos[2], new Position(0,2), "2 "),
            //new Agent(tabpos[3], new Position(0,3), "3 "), 
            new Agent(tabpos[4], new Position(0,4), "4 "),
            //new Agent(tabpos[5], new Position(1,0), "5 "),
            new Agent(tabpos[6], new Position(1,1), "6 "),
            //new Agent(tabpos[7], new Position(1,2), "7 "),
            new Agent(tabpos[8], new Position(1,3), "8 "),          
            //new Agent(tabpos[9], new Position(1,4), "9 "),
            //new Agent(tabpos[10], new Position(2,0), "10 "),
            new Agent(tabpos[11], new Position(2,1), "11 "),
            new Agent(tabpos[12], new Position(2,2), "12 "), 
            //new Agent(tabpos[13], new Position(2,3), "13 "),
            new Agent(tabpos[14], new Position(2,4), "14 "),
            //new Agent(tabpos[15], new Position(3,0), "15 "),
            new Agent(tabpos[16], new Position(3,1), "16 "), 
            //new Agent(tabpos[17], new Position(3,2), "17 "),
            new Agent(tabpos[18], new Position(3,3), "18 "),
            new Agent(tabpos[19], new Position(3,4), "19 "),
            //new Agent(tabpos[20], new Position(4,0), "20 "),
            new Agent(tabpos[21], new Position(4,1), "21 "),
            //new Agent(tabpos[22], new Position(4,2), "22 "),
            new Agent(tabpos[23], new Position(4,3), "23 "),   
            //new Agent(tabpos[24], new Position(4,4), "24 "),
        };
        for(int i=0; i< Agent.nbrAgents;i++) {
            tabAgents[i] = tabAgents2[i];
        }
        plateau = new Plateau();
        plateau.affichePlateauTerminal();
        HashMapMessages = new HashMap<>();
        for(int i = 0; i < Agent.nbrAgents; i++) {
            HashMapMessages.put(tabAgents[i], new ArrayList<>());
        }
        aff = new Affichage();
        plateau.resoudre();
    }
}