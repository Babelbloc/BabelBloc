package net.lemarechal.corentin.babelbloc;

public class Langue {

    private Drapeau drapeau;
    private String nom;

    public Langue(String nom, Drapeau drapeau){
        this.nom = nom;
        this.drapeau = drapeau;
    }

    public Drapeau getDrapeau() {
        return drapeau;
    }

    public String getNom() {
        return nom;
    }
}
