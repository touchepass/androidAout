package be.tony.troisiememain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.tony.troisiememain.R;

import be.tony.troisiememain.Classe.Article;

public class InfoArticleActivity extends AppCompatActivity {

    private TextView nom, description, prix, etat, ville, reception;
    private Button retour;
    private Article a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_article);

        nom = (TextView) findViewById(R.id.nom_info);
        description = (TextView) findViewById(R.id.description_info);
        prix = (TextView) findViewById(R.id.prix_info);
        etat = (TextView) findViewById(R.id.etat_info);
        ville = (TextView) findViewById(R.id.ville_info);
        reception = (TextView) findViewById(R.id.reception_info);

        retour = (Button) findViewById(R.id.retour_info);
        retour.setOnClickListener(RetourRecherche);

        Intent intent = getIntent();
        a = intent.getParcelableExtra("article");

        nom.setText(a.getNom());
        description.setText(a.getDescription());
        prix.setText(String.valueOf(a.getPrix()));
        if(a.getEtat() == 1){
            etat.setText("Neuf");
        }
        else{
            etat.setText("Utilisé");
        }
        ville.setText(a.getVille());
        if(a.getRecuperation()==1){
            reception.setText("Poste");
        }
        else{
            reception.setText("Déplacement");
        }

    }

    View.OnClickListener RetourRecherche = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
