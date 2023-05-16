package taquin;

import static taquin.Main.*;
import java.util.List;

public class Agent extends Thread{
    
    private Position positionActu;
    private Position positionPrecedente;
    private Position positionFinale;
    private String number;

    private boolean fini = false;

    public static int nbrAgents;

    public Agent(Position positionDepart, Position positionFinale, String num){
        this.positionActu = positionDepart;
        this.positionPrecedente = positionDepart;
        this.positionFinale = positionFinale;
        this.number = num;
    }

    public Position getPosActu() {
        return positionActu;
    }

    public Position getPosPrecedente() {
        return positionPrecedente;
    }

    public Position getPosFinale() {
        return positionFinale;
    }

    public String getNumber() {
        return number;
    }

    public void setPosActu(Position p) {
        positionActu = p;
    }

    public void setPosPrecedente(Position p) {
        positionPrecedente = p;
    }
    //Renvoi la meilleur position pour un agent selon nos criteres de priorité
    public void bestPosition() throws InterruptedException {
        Position position = null;
        synchronized (plateau) {
            position = plateau.getVoisinVide(this); //renvoi la case vide la plus proche de l'arrivé ou aleatoirement dans les case vide possible si pas precedent
            if(getPriority()>1){
                Thread.currentThread().setPriority(getPriority()-1);
            }
            if(position == getPosActu()) {
                if(getPriority()<10){
                    Thread.currentThread().setPriority(getPriority()+1);
                }
                position = plateau.agentVoisin(this).getPosActu(); // renvoi le voisin le plus proche de l'arrivé ou alors aleatoirement si pas precedent
                Message msg = new Message(this, plateau.getAgentPos(position), position); //creer un message
                HashMapMessages.get(msg.getDestinataire()).add(msg); //envoi le message a l'agent qui bloque (rentre le msg dans la hashmap)
            } else {
                plateau.deplacer(this, position); //deplace les cases
            }
        }
    }

    //Renvoi la meilleur position pour un agent selon nos criteres de priorité mais en autorisant le retour arriere pour les choix de case vide
    public void bestPositionAutorisePredecesseur() throws InterruptedException { 
        Position position = null;
        synchronized (plateau) {
            position = plateau.getVoisinVidePrec(this); //renvoi une case vide aleatoirement
            if(getPriority()>1){
                Thread.currentThread().setPriority(getPriority()-1);
            }
            if(position == getPosActu()) {
                if(getPriority()<10){
                    Thread.currentThread().setPriority(getPriority()+1);
                }
                position = plateau.agentVoisin(this).getPosActu();
                Message msg = new Message(this, plateau.getAgentPos(position), position);
                HashMapMessages.get(msg.getDestinataire()).add(msg);
            } else {
                try {
                    plateau.deplacer(this, position);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //vrai si agent est sur case final
    public boolean bienPlace(){
        return getPosActu().equals(getPosFinale());
    }
    //procedure qui effectué lance les bonne procedure sur un agent selon la situation
    public void run() {

        while(plateau.nonFini()) { //tant que le plateau n'est pas fini

            List<Message> messagesAgent = HashMapMessages.get(this);
            Message premierMessage;
            if(messagesAgent.isEmpty()) {
                premierMessage = null;
            } else {
                premierMessage = messagesAgent.remove(0);
            }

            if(premierMessage != null && getPosActu().equals(premierMessage.getPositionVider())) { //si un agent recoi un message et doit se decaler
                try {
                    bestPositionAutorisePredecesseur();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else if(!bienPlace()) { //si un agent doit de deplacer pour aller a sa case final
                try {
                    bestPosition();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            if(!fini){ // affichage de la fin du jeu
                fini = true;
                System.out.print("Nombre de deplacement final : " + Plateau.nbrDeplacement + "\n");
                sleep(5000);
                System.exit(0);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }






























}
