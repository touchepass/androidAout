package be.tony.troisiememain.Classe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ArticleList extends ArrayList<Article> implements Parcelable
{
    public ArticleList ()
    {

    }

    public ArticleList (Parcel in)
    {
        this.getFromParcel(in);
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public ArticleList createFromParcel(Parcel in)
        {
            return new ArticleList(in);
        }

        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        //Taille de la liste
        int size = this.size();
        dest.writeInt(size);
        for(int i=0; i < size; i++)
        {
            Article art = this.get(i); //On vient lire chaque objet personne
            dest.writeString(art.getNom());
            dest.writeString(art.getDescription());
            dest.writeDouble(art.getPrix());
            dest.writeInt(art.getEtat());
            dest.writeString(art.getVille());
            dest.writeInt(art.getRecuperation());
        }
    }

    public void getFromParcel(Parcel in)
    {
        // On vide la liste avant tout remplissage
        this.clear();

        //Récupération du nombre d'objet
        int size = in.readInt();

        //On repeuple la liste avec de nouveau objet
        for(int i = 0; i < size; i++)
        {
            Article art = new Article();
            art.setNom(in.readString());
            art.setDescription(in.readString());
            art.setPrix(in.readDouble());
            art.setEtat(in.readInt());
            art.setVille(in.readString());
            art.setRecuperation(in.readInt());
            this.add(art);
        }

    }
}
