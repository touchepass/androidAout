package be.tony.troisiememain.Classe;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


public class Article implements Parcelable, Comparable<Article> {

    private String nom;
    private String description;
    private double prix;
    private int etat;
    private String ville;
    private int recuperation;

    public Article(){}

    public Article(String nom, String description, double prix, int etat, String ville, int recuperation){
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.etat = etat;
        this.ville = ville;
        this.recuperation = recuperation;
    }

    public String getNom(){return this.nom;}
    public void setNom(String nom){this.nom = nom;}
    public String getDescription(){return this.description;}
    public void setDescription(String description){this.description = description;}
    public double getPrix(){return this.prix;}
    public void setPrix(double prix){this.prix = prix;}
    public int getEtat(){return this.etat;}
    public void setEtat(int etat){this.etat = etat;}
    public String getVille(){return this.ville;}
    public void setVille(String ville){this.ville = ville;}
    public int getRecuperation(){return this.recuperation;}
    public void setRecuperation(int recuperation){this.recuperation = recuperation;}

    public Article(Parcel in)
    {
        this.getFromParcel(in);
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public Article createFromParcel(Parcel in)
        {
            return new Article(in);
        }

        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };

    @Override
    public int compareTo(@NonNull Article o) {
        return (int)(this.getPrix()*1000) - (int)(o.getPrix()*1000);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //On ecrit dans le parcel les données de notre objet
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.nom);
        dest.writeString(this.description);
        dest.writeDouble(this.prix);
        dest.writeInt(this.etat);
        dest.writeString(this.ville);
        dest.writeInt(this.recuperation);
    }

    //On va ici hydrater notre objet à partir du Parcel
    public void getFromParcel(Parcel in)
    {
        this.setNom(in.readString());
        this.setDescription(in.readString());
        this.setPrix(in.readDouble());
        this.setEtat(in.readInt());
        this.setVille(in.readString());
        this.setRecuperation(in.readInt());
    }



}
