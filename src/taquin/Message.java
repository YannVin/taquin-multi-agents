package taquin;
public class Message {
    private final Agent destinataire;
    private final Agent expediteur;
    private final Position positionVider; 

    public Message(Agent e, Agent d, Position p){
        expediteur = e;
        destinataire = d;
        positionVider = p;
    }

    public Agent getDestinataire() {
        return destinataire;
    }

    public Agent getExpediteur() {
        return expediteur;
    }

    public Position getPositionVider() {
        return positionVider;
    }
}
