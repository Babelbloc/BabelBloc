package net.lemarechal.corentin.babelbloc;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class Personnel implements Parcelable,Serializable {
    private static final long serialVersionUID = 12;
    // fields

    public String PersonnelNom;
    public String PersonnelPrenom;
    public String PersonnelMdp;
    public String PersonnelIdentifiant;
    // constructors
    public Personnel() {}

    public Personnel( String Nom,String Prenom,String Identifiant,String Mdp) {
        this.PersonnelNom = Nom;
        this.PersonnelPrenom= Prenom;
        this.PersonnelMdp = Mdp;
        this.PersonnelIdentifiant = Identifiant;

    }

    protected Personnel(Parcel in) {
        PersonnelNom = in.readString();
        PersonnelPrenom = in.readString();
        PersonnelMdp = in.readString();
        PersonnelIdentifiant = in.readString();
    }

    public static final Creator<Personnel> CREATOR = new Creator<Personnel>() {
        @Override
        public Personnel createFromParcel(Parcel in) {
            return new Personnel(in);
        }

        @Override
        public Personnel[] newArray(int size) {
            return new Personnel[size];
        }
    };

    @Override
    public String  toString()
    {
        return ("Nom : "+this.PersonnelNom+ "\t Prenom : "+this.PersonnelPrenom+ "\t Mot de passe : "+this.PersonnelMdp+ "\n Identifiant : "+this.PersonnelIdentifiant);
    }
    // properties
    public  String getPersonnelPrenom()
    {
        return  this.PersonnelPrenom;
    }
    public String getPersonnelName() {
        return this.PersonnelNom;
    }
    public String getPersonnelMdp()
    {
        return this.PersonnelMdp;
    }
    public  String getPersonnelIdentifiant()
    {
        return  this.PersonnelIdentifiant;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(PersonnelNom);
        dest.writeString(PersonnelPrenom);
        dest.writeString(PersonnelMdp);
        dest.writeString(PersonnelIdentifiant);


    }
}