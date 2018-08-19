package be.tony.troisiememain.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import be.tony.troisiememain.R;

import java.util.Collections;

import be.tony.troisiememain.Classe.Article;
import be.tony.troisiememain.Classe.ArticleList;

public class AffichageResultatActivity extends AppCompatActivity {

    private String title;
    private Button prev, next, detail, tris, retour;
    private TableLayout resultat;
    private TextView msg_err;
    private int cpt_lst_articles, tri;
    private ArticleList lst;
    private int orientation, ecran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_resultat);

        title = "Affichage Résultats";
        setTitle(title);

        prev = (Button) findViewById(R.id.prev_res);
        next = (Button) findViewById(R.id.next_res);
        detail = (Button) findViewById(R.id.afficher_article);
        tris = (Button) findViewById(R.id.trier);
        retour = (Button) findViewById(R.id.retour_aff_res);

        resultat = (TableLayout) findViewById(R.id.table_res);

        msg_err = (TextView) findViewById(R.id.err_resultat_aff);

        orientation = getResources().getConfiguration().orientation;
        ecran = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        Intent recup_ajouter_article = getIntent();
        lst = recup_ajouter_article.getParcelableExtra("listeArticle");

        if(lst.size() == 0){
            msg_err.setText("aucun article trouvé");
            msg_err.setTextColor(Color.RED);
        }
        else{
            if(savedInstanceState != null){
                cpt_lst_articles = savedInstanceState.getInt("cpt_lst_articles");
                tri = savedInstanceState.getInt("tri");
            }
            else{
                tri = 0;
                cpt_lst_articles = 0;
            }
            initTableau();
        }

        retour.setOnClickListener(RetourRecherche);
        next.setOnClickListener(nextList);
        prev.setOnClickListener(prevLst);
        tris.setOnClickListener(trier);
    }

    View.OnClickListener RetourRecherche = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    View.OnClickListener trier = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(tri%2==0){
                Collections.sort(lst, (a,b) -> a.compareTo(b) );

            }
            else{
                Collections.sort(lst, (a,b) -> b.compareTo(a) );
            }
            tri++;
            initTableau();
        }
    };


    View.OnClickListener nextList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(lst.size() > 5*(cpt_lst_articles+1)){
                msg_err.setText("");
                cpt_lst_articles += 1;
                initTableau();
            }
            else{
                msg_err.setText("Pas d'autre articles");
                msg_err.setTextColor(Color.RED);
            }
        }

    };

    View.OnClickListener prevLst = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(cpt_lst_articles > 0){
                msg_err.setText("");
                cpt_lst_articles -= 1;
                initTableau();
            }
            else{
                msg_err.setText("Pas d'articles précédent");
                msg_err.setTextColor(Color.RED);
            }
        }
    };

    View.OnClickListener infoArticle = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AffichageResultatActivity.this, InfoArticleActivity.class);
            Article a = (Article)v.getTag();
            intent.putExtra("article",(Parcelable)a);
            startActivity(intent);
        }
    };

    private void initTableau(){

        int max = lst.size() > (5*(cpt_lst_articles+1)) ? 5 : lst.size()-(cpt_lst_articles*5);

        resultat.removeAllViews();

        TableRow ligneTitre = new TableRow(this);
        TextView tab_nom_t = new TextView((this));
        tab_nom_t.setText("Nom");
        tab_nom_t.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tab_nom_t.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        ligneTitre.addView(tab_nom_t);

        TextView tab_prix_t = new TextView((this));
        tab_prix_t.setText("Prix");
        tab_prix_t.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tab_prix_t.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        ligneTitre.addView(tab_prix_t);

        if(ecran == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            TextView tab_ville_t = new TextView((this));
            tab_ville_t.setText("Ville");
            tab_ville_t.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tab_ville_t.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            ligneTitre.addView(tab_ville_t);

            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
                TextView tab_etat_t = new TextView((this));
                tab_etat_t.setText("Etat");
                tab_etat_t.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                tab_etat_t.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                ligneTitre.addView(tab_etat_t);
            }
        }
        else{
            if(orientation == Configuration.ORIENTATION_LANDSCAPE){
                TextView tab_ville_t = new TextView((this));
                tab_ville_t.setText("Ville");
                tab_ville_t.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                tab_ville_t.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                ligneTitre.addView(tab_ville_t);
            }
        }

        resultat.addView(ligneTitre);

        for(int i = 0; i < max; i++){
            Article a = lst.get((cpt_lst_articles*5)+i);

            TableRow ligne = new TableRow(this);
            if(i%2==0){
                ligne.setBackgroundColor(Color.LTGRAY);
            }
            ligne.setClickable(true);
            ligne.setOnClickListener(infoArticle);
            ligne.setTag(a);

            TextView tab_nom = new TextView((this));
            tab_nom.setText(a.getNom());
            tab_nom.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tab_nom.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            ligne.addView(tab_nom);

            TextView tab_prix = new TextView((this));
            tab_prix.setText(String.valueOf(a.getPrix()));
            tab_prix.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tab_prix.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            ligne.addView(tab_prix);

            if(ecran == Configuration.SCREENLAYOUT_SIZE_XLARGE){
                TextView tab_ville = new TextView((this));
                tab_ville.setText(a.getVille());
                tab_ville.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                tab_ville.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                ligne.addView(tab_ville);

                if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    TextView tab_etat = new TextView((this));
                    if(a.getEtat() == 1){
                        tab_etat.setText("Neuf");
                    }
                    else{
                        tab_etat.setText("Utilisé");
                    }
                    tab_etat.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    tab_etat.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                    ligne.addView(tab_etat);
                }
            }
            else{
                if(orientation == Configuration.ORIENTATION_LANDSCAPE){
                    TextView tab_ville = new TextView((this));
                    tab_ville.setText(a.getVille());
                    tab_ville.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    tab_ville.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                    ligne.addView(tab_ville);
                }
            }

            resultat.addView(ligne);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);

        saveInstanceState.putInt("cpt_lst_articles",cpt_lst_articles);
        saveInstanceState.putInt("tri",tri);
    }
}
