package be.tony.troisiememain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.tony.troisiememain.R;

public class AccueilActivity extends AppCompatActivity {

    // var
    private Button ajouter_article, rechercher_par_prix, rechercher_par_ville, quitter;
    private String title;
    private TextView retour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        title = "Accueil";
        setTitle(title);

        retour = (TextView) findViewById(R.id.information_accueil);

        ajouter_article = (Button) findViewById(R.id.ajouter_article);
        rechercher_par_prix = (Button) findViewById(R.id.recherche_par_prix);
        rechercher_par_ville = (Button) findViewById(R.id.rechercher_par_ville);
        quitter = (Button) findViewById(R.id.quitter);

        ajouter_article.setOnClickListener(ajouterArticleActivite);
        rechercher_par_prix.setOnClickListener(rechercherParPrixActivite);
        rechercher_par_ville.setOnClickListener(rechercherParVilleActivite);
        quitter.setOnClickListener(Quitter);

        Intent recup_ajouter_article = getIntent();
        String message = recup_ajouter_article.getStringExtra("ajou_article");
        if(message != null){
            retour.setText((String)message);
        }

    }

    View.OnClickListener Quitter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            System.exit(0);
        }
    };

    View.OnClickListener ajouterArticleActivite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AccueilActivity.this, AjouterArticleActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener rechercherParPrixActivite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AccueilActivity.this, RecherchePrixActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener rechercherParVilleActivite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AccueilActivity.this, RechercherVilleActivity.class);
            startActivity(intent);
        }
    };
}
