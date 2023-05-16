package taquin;

import static java.lang.Thread.MIN_PRIORITY;
import static java.lang.Thread.sleep;
import static taquin.Main.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plateau {
    private static final int size = 5;
    private final String[][] plateau;
    public static int nbrDeplacement = 0;

    public static int getSize() {
        return size;
    }

    public Plateau(){
        plateau = new String[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                plateau[i][j] = "  ";
            }
        }
        for (Agent agent : tabAgents) {
            plateau[agent.getPosActu().getX()][agent.getPosActu().getY()] = agent.getNumber();
        }
    }

    public String getCase(int i,int j) {
        return plateau[i][j];
    }

    public Agent getAgentPos(Position p){
        for(Agent agent:tabAgents){
            if(agent.getPosActu().equals(p)){
                return agent;
            }
        }
        return null;
    }

    public Agent agentVoisin(Agent a) { // renvoi le voisin le plus proche de l'arrivé ou alors aleatoirement si pas precedent

        Position positionActu = a.getPosActu();
        Position positionFinale = a.getPosFinale();

        double distanceH = distance(new Position(positionActu.getX()-1,positionActu.getY()),positionFinale); //distance 2D
        double distanceB = distance(new Position(positionActu.getX()+1,positionActu.getY()),positionFinale);
        double distanceD = distance(new Position(positionActu.getX(),positionActu.getY()+1),positionFinale);
        double distanceG = distance(new Position(positionActu.getX(),positionActu.getY()-1),positionFinale);

        Agent posVoisin = null;
        

        double min = Math.min(Math.min(Math.min(distanceH,distanceB),distanceD),distanceG); 
        //on recupere le voisin le plus proche de l'arrivé
        if((positionActu.getX() -1 >= 0) && distanceH == min){
            posVoisin = getAgentPos(new Position(positionActu.getX()-1,positionActu.getY()));
        }
        if((positionActu.getX() + 1 < size) && distanceB == min){
            posVoisin = getAgentPos(new Position(positionActu.getX()+ 1,positionActu.getY() ));
        }
        if((positionActu.getY() + 1 < size) && distanceD == min){
            posVoisin = getAgentPos(new Position(positionActu.getX() ,positionActu.getY()+ 1));
        }
        if((positionActu.getY() - 1 >= 0) && distanceG == min){
            posVoisin = getAgentPos(new Position(positionActu.getX() ,positionActu.getY()- 1));
        }   
        //Sinon on recpere un voisin a aleatoirement
        if(posVoisin == null) {
            if((positionActu.getX() -1 >= 0) && !(plateau[positionActu.getX() - 1][positionActu.getY()].equals("  ") )){
                posVoisin = getAgentPos(new Position(positionActu.getX()-1,positionActu.getY()));
            }
            if((positionActu.getX() + 1 < size)  && !(plateau[positionActu.getX() + 1][positionActu.getY()].equals("  "))){
                posVoisin = getAgentPos(new Position(positionActu.getX()+ 1,positionActu.getY() ));
            }
            if((positionActu.getY() + 1 < size) && !(plateau[positionActu.getX() ][positionActu.getY()+1].equals("  "))){
                posVoisin = getAgentPos(new Position(positionActu.getX() ,positionActu.getY()+ 1));
            }
            if((positionActu.getY() - 1 >= 0) && !(plateau[positionActu.getX() ][positionActu.getY()-1].equals("  "))){
                posVoisin = getAgentPos(new Position(positionActu.getX() ,positionActu.getY()- 1));
            }   
        }

        return posVoisin;
    }

    public boolean nonFini(){
        for(Agent agent : tabAgents){
            if(!(agent.getPosActu().equals(agent.getPosFinale()))){
                return true;
            }
        }
        return false;
    }
    //change les coordonnée des agents
    public void deplacer(Agent a, Position p) throws InterruptedException {
        plateau[p.getX()][p.getY()] = a.getNumber();
        plateau[a.getPosActu().getX()][a.getPosActu().getY()] = "  ";

        sleep(100);
        nbrDeplacement++;
        affichePlateauTerminal();
        aff.AffichageGraphique();
        a.setPosPrecedente(a.getPosActu());
        a.setPosActu(p);
    }
    
    public void affichePlateauTerminal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(plateau[i][j] + " | ");
            }
            System.out.print('\n');
            for(int k = 0; k < size; k++)
                System.out.print("___  ");
            System.out.print('\n');
        }
        System.out.print('\n');
        System.out.println("Deplacement numéro : " + nbrDeplacement + "\n");
    }
    //renvoi la case vide la plus proche de l'arrivé ou aleatoirement dans les case vide possible si pas precedent
    public Position getVoisinVide(Agent a){

        Position positionActu = a.getPosActu();
        Position positionFinale = a.getPosFinale();
        Position positionPrec = a.getPosPrecedente();
        Position tempPos = positionActu;

        Position tempPosH = positionActu;
        Position tempPosB = positionActu;
        Position tempPosD = positionActu;
        Position tempPosG = positionActu;

        Position nouvellePos = positionActu;

        double distanceH = distance(new Position(positionActu.getX()-1,positionActu.getY()),positionFinale);
        double distanceB = distance(new Position(positionActu.getX()+1,positionActu.getY()),positionFinale);
        double distanceD = distance(new Position(positionActu.getX(),positionActu.getY()+1),positionFinale);
        double distanceG = distance(new Position(positionActu.getX(),positionActu.getY()-1),positionFinale);

        List<Position> ListPosition = new ArrayList<>();

        
        double min = Math.min(Math.min(Math.min(distanceH,distanceB),distanceD),distanceG);
        //renvoi case vide la plus proche de l'arrivé qui n'est pas precedent
        if((positionActu.getX() -1 >= 0) && distanceH == min && (plateau[positionActu.getX() - 1][positionActu.getY()].equals("  "))){
            tempPos = new Position(positionActu.getX() - 1,positionActu.getY());
        }
        if((positionActu.getX() + 1 < size) && distanceB == min && (plateau[positionActu.getX() + 1][positionActu.getY()].equals("  "))){
            tempPos = new Position(positionActu.getX() + 1,positionActu.getY());
        }
        if((positionActu.getY() + 1 < size) && distanceD == min && (plateau[positionActu.getX()][positionActu.getY() + 1].equals("  "))){
            tempPos = new Position(positionActu.getX(),positionActu.getY() + 1);
        }
        if((positionActu.getY() - 1 >= 0) && distanceG == min && (plateau[positionActu.getX()][positionActu.getY() - 1].equals("  "))){
            tempPos = new Position(positionActu.getX() ,positionActu.getY()- 1);
        }
        if(!tempPos.equals(positionActu) && !tempPos.equals(positionPrec))
        {
            nouvellePos = tempPos;
        }

        //sinon renvoi aleatoirement case vide pas precedent
        if( nouvellePos == positionActu) {
            if((positionActu.getX() -1 >= 0) && (plateau[positionActu.getX() - 1][positionActu.getY()].equals("  "))){
                tempPosH = new Position(positionActu.getX() - 1,positionActu.getY());
                if(!tempPosH.equals(positionPrec)) {
                    ListPosition.add(tempPosH);
                }
            }
            if((positionActu.getX() + 1 < size) && (plateau[positionActu.getX() + 1][positionActu.getY()].equals("  "))){
                tempPosB = new Position(positionActu.getX() + 1,positionActu.getY());
                if(!tempPosB.equals(positionPrec)) {
                    ListPosition.add(tempPosB);
                }
            }
            if((positionActu.getY() + 1 < size) && (plateau[positionActu.getX()][positionActu.getY() + 1].equals("  "))){
                tempPosD = new Position(positionActu.getX(),positionActu.getY() + 1);
                if(!tempPosD.equals(positionPrec)) {
                    ListPosition.add(tempPosD);
                }
            }
            if((positionActu.getY() - 1 >= 0) && (plateau[positionActu.getX()][positionActu.getY() - 1].equals("  "))){
                tempPosG = new Position(positionActu.getX() ,positionActu.getY()- 1);
                if(!tempPosG.equals(positionPrec)){
                    ListPosition.add(tempPosG);
                }
            }
        }
        int maxrandom = ListPosition.size();
        int nombreAleatoire = 0;
        Random random = new Random();
        if(maxrandom != 0){
            nombreAleatoire = random.nextInt(maxrandom);
        }
        if(!ListPosition.isEmpty() && ListPosition.size()==1) {
            nouvellePos = ListPosition.remove(0);
        }
        else if(!ListPosition.isEmpty()){
            nouvellePos = ListPosition.remove(nombreAleatoire);
        }

        return nouvellePos;
    }
    //lance la resolution sur tout les thread
    public void resoudre(){
        for(Agent a:tabAgents){
            a.start();
            if(a.getPosActu() == a.getPosFinale()){
                a.setPriority(MIN_PRIORITY);
            }
        }
    }
    //renvoi une case vide aleatoirement
    public Position getVoisinVidePrec(Agent a){

        Position positionActu = a.getPosActu();

        Position tempPosH = positionActu;
        Position tempPosB = positionActu;
        Position tempPosD = positionActu;
        Position tempPosG = positionActu;

        Position nouvellePos = positionActu;

        List<Position> ListPosition = new ArrayList<>();

        if((positionActu.getX() -1 >= 0) && (plateau[positionActu.getX() - 1][positionActu.getY()].equals("  "))){
            tempPosH = new Position(positionActu.getX() - 1,positionActu.getY());
            ListPosition.add(tempPosH);
        }
        if((positionActu.getX() + 1 < size) && (plateau[positionActu.getX() + 1][positionActu.getY()].equals("  "))){
            tempPosB = new Position(positionActu.getX() + 1,positionActu.getY());
            ListPosition.add(tempPosB);
        }
        if((positionActu.getY() + 1 < size) && (plateau[positionActu.getX()][positionActu.getY() + 1].equals("  "))){
            tempPosD = new Position(positionActu.getX(),positionActu.getY() + 1);
            ListPosition.add(tempPosD);
        }
        if((positionActu.getY() - 1 >= 0) && (plateau[positionActu.getX()][positionActu.getY() - 1].equals("  "))){
            tempPosG = new Position(positionActu.getX() ,positionActu.getY()- 1);
            ListPosition.add(tempPosG);
        }
        int maxrandom = ListPosition.size();
        int nombreAleatoire = 0;
        Random random = new Random();
        if(maxrandom != 0){
            nombreAleatoire = random.nextInt(maxrandom);
        }
        if(!ListPosition.isEmpty() && ListPosition.size()==1) {
            nouvellePos = ListPosition.remove(0);
        }
        else if(!ListPosition.isEmpty()){
            nouvellePos = ListPosition.remove(nombreAleatoire);
        }

        return nouvellePos;
    }

    //renvoi un distance 2D
    public double distance(Position p1, Position p2) {
        double res = (p2.getY()-p1.getY())*(p2.getY()-p1.getY()) + (p2.getX()-p1.getX())*(p2.getX()-p1.getX());
        return Math.sqrt(res);
    }

}